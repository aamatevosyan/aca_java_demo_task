# ACA TASK for Java Teaching Assistant

## Requirements

Create a web application that will provide a REST api for simulating certification process at an educational company.

You should provide a REST api for CRUD operations for Applicants and Courses.

The applicant should have:
name, email, phone number, address, status (**ON_HOLD**, **IN_PROGRESS**, **COMPLETED**) and can be assigned to one course;

The course should have:
name, start date, end date, teacher name, description and should hold the applicants assigned to it.

Applicants should be filterable by name, email, assigned course.
Courses should be searchable by name.

Provide possibility to generate certificates for all applicants who completed the course and download the zip file containing all generated certificates.

The certificate is just a simple pdf file with Applicant and course information on it;

[Certificate Example](docs/example%20-%20certificate.pdf)

Extra points for`
1. Adding swagger
2. Securing the application with basic or JWT auth
3. Deploying to AWS
4. Configure HTTPS
5. Store generated zip files in S3