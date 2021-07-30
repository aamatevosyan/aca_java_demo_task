package com.ama.aca_demo_task.service;

import com.ama.aca_demo_task.exception.EntityNotFoundException;
import com.ama.aca_demo_task.model.Applicant;
import com.ama.aca_demo_task.model.Course;
import com.ama.aca_demo_task.repository.CourseRepository;
import com.ama.aca_demo_task.utils.RandomString;
import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses(String name) {
        List<Course> courses = new ArrayList<>();
        if (name == null) {
            courseRepository.findAll().forEach(courses::add);
        } else {
            courses.addAll(courseRepository.findByNameIgnoreCaseContaining(name));
        }

        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class, "id", id.toString()));
    }

    @Override
    public Course insert(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void updateCourse(Long id, Course course) {
        Course oldCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class, "id", id.toString()));

        oldCourse.setName(course.getName());
        oldCourse.setStartDate(course.getStartDate());
        oldCourse.setEndDate(course.getEndDate());
        oldCourse.setTeacherName(course.getTeacherName());
        oldCourse.setDescription(course.getDescription());

        courseRepository.save(oldCourse);
    }

    @Override
    public void deleteCourse(Long todoId) {
        courseRepository.deleteById(todoId);
    }

    @Override
    public void getCertificates(Long id, HttpServletResponse response) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class, "id", id.toString()));

        List<Applicant> applicants = course.getApplicants();

        String PATH = "temp/";
        String directoryName = PATH + "courses/" + new RandomString().nextString();

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<String> fileNames = applicants.stream().map(applicant -> {
            try {
                String html = this.parseThymeleafTemplate(course, applicant);
                String filename = directoryName + "/" + id.toString() + ".pdf";
                this.generatePdfFromHtml(html, filename);

                return filename;
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        System.out.println("############# file size ###########" + fileNames.size());

        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                FileSystemResource resource = new FileSystemResource(file);

                ZipEntry e = new ZipEntry(resource.getFilename());
                // Configure the zip entry, the properties of the file
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                // etc.
                zippedOut.putNextEntry(e);
                // And the content of the resource:
                StreamUtils.copy(resource.getInputStream(), zippedOut);
                zippedOut.closeEntry();
            }
            zippedOut.finish();
        } catch (Exception e) {
            // Exception handling goes here
        }
    }

    protected String parseThymeleafTemplate(Course course, Applicant applicant) throws IOException {
        String template = Files.readString(Path.of("resources/thymeleaf_template.html"));
        template = template.replace("${name}", course.getName());
        template = template.replace("${description}", course.getDescription());
        template = template.replace("${applicantName}", applicant.getName());
        template = template.replace("${teacherName}", course.getTeacherName());

        Long months = ChronoUnit.MONTHS.between(
                course.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                course.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        template = template.replace("${duration}", months.toString() + " months");

        return htmlToXhtml(template);
    }

    private static String htmlToXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    public void generatePdfFromHtml(String html, String filename) throws DocumentException, IOException {
        OutputStream outputStream = new FileOutputStream(filename);
        System.out.println(html);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
}
