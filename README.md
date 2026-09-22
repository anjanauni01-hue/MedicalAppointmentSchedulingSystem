# Medical Appointment Scheduling System

SE1020 - Object Oriented Programming | Project Assignment (10%)
Student: Anjana | Student ID: IT22195470 | SLIIT

A web-based application (Java, Spring Boot, Thymeleaf, Bootstrap, MySQL) that lets an
admin manage **patients**, **doctors**, and **appointments** for a clinic. It demonstrates
Encapsulation, Inheritance, Polymorphism, Abstraction and Information Hiding, together
with full CRUD operations backed by a MySQL database (via Spring Data JPA).

## 1. How to run this project in IntelliJ IDEA

1. **Install prerequisites** (if not already installed):
   - JDK 17 or newer
   - MySQL Server (8.x) running locally
   - Maven (IntelliJ has one built in, no separate install needed)

2. **Create the database** — open MySQL Workbench / CLI and run:
   ```sql
   CREATE DATABASE mass_db;
   ```
   (You can skip this — the app will also auto-create it because of
   `createDatabaseIfNotExist=true` in the connection URL, as long as the
   MySQL user has permission.)

3. **Set your MySQL credentials** in
   `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

4. **Open the project in IntelliJ IDEA**: `File -> Open...` -> select the
   `MedicalAppointmentSchedulingSystem` folder. IntelliJ will detect the
   `pom.xml` and import it as a Maven project automatically.

5. **Run it**: open
   `src/main/java/com/sliit/mass/MedicalAppointmentSchedulingSystemApplication.java`
   and click the green ▶ Run button (or run `mvn spring-boot:run` from the
   terminal).

6. **Open the app** in your browser at: **http://localhost:8080**

   Hibernate will create all the tables automatically on first run, and
   `data.sql` will insert a few sample patients, doctors and appointments
   so the screens aren't empty for your demo/viva.

## 2. Project structure (package by layer)

```
src/main/java/com/sliit/mass/
 ├── model/         Entity classes (OOP hierarchy lives here)
 │    ├── Person.java              (abstract base: Patient/Doctor/Admin)
 │    ├── Patient.java
 │    ├── Doctor.java
 │    ├── Admin.java
 │    ├── Appointment.java         (abstract base: InPerson/Online)
 │    ├── InPersonAppointment.java
 │    ├── OnlineAppointment.java
 │    └── AppointmentStatus.java   (enum)
 ├── repository/     Spring Data JPA interfaces (file/DB handling layer)
 ├── service/        Service interfaces (ABSTRACTION)
 ├── service/impl/   Service implementations (business logic + validation)
 └── controller/     Spring MVC controllers (handle HTTP requests, CRUD)

src/main/resources/
 ├── templates/      Thymeleaf + Bootstrap 5 UI pages
 ├── static/css/     Custom styling
 ├── application.properties
 └── data.sql        Sample seed data
```

## 3. Where each OOP concept is implemented

| Concept | Where |
|---|---|
| **Encapsulation** | Every field in every model class (`Person`, `Patient`, `Doctor`, `Admin`, `Appointment`, ...) is `private`, only reachable through public getters/setters. |
| **Information Hiding** | `PatientServiceImpl.validate()`, `DoctorServiceImpl.validate()`, `AppointmentServiceImpl.validate()` are `private` helper methods invisible outside the class. Controllers depend only on `Service` **interfaces**, never on the `*ServiceImpl` classes or the repository directly. |
| **Abstraction** | `Person` and `Appointment` are `abstract` classes with abstract methods (`getRole()`, `getModeDetails()`, `calculateFee()`, `getAppointmentType()`). The `PatientService`, `DoctorService`, `AppointmentService`, `AdminService` interfaces describe *what* operations exist without exposing *how* they work. |
| **Inheritance** | `Patient`, `Doctor`, `Admin` all `extends Person`. `InPersonAppointment`, `OnlineAppointment` both `extends Appointment`. |
| **Polymorphism** | `appointment.calculateFee(baseFee)` and `appointment.getModeDetails()` behave differently depending on whether the actual object is an `InPersonAppointment` or `OnlineAppointment` — see `AppointmentServiceImpl.calculateAppointmentFee()` and the appointment view page, which prints the result of the polymorphic call. `getRole()` similarly differs across `Patient` / `Doctor` / `Admin`. |

## 4. CRUD operations implemented

| Module | Create | Read | Update | Delete |
|---|---|---|---|---|
| Patients | ✅ Register | ✅ List + Search + View | ✅ Edit | ✅ Delete |
| Doctors | ✅ Register | ✅ List + Search + View | ✅ Edit | ✅ Delete |
| Appointments | ✅ Book (In-Person / Online) | ✅ List + View | ✅ Update status | ✅ Delete |
| Admins | ✅ Register | ✅ List | ✅ Edit | ✅ Delete |

## 5. Data storage

This version uses a **MySQL database** via Spring Data JPA (`spring.jpa.hibernate.ddl-auto=update`
auto-generates/updates the schema from the entity classes — no manual SQL table
creation needed). This satisfies the "proper MySQL/SQL DB connection" option
allowed by the assignment brief (as an alternative to plain `.txt` file
read/write).

## 6. Git / GitHub

This folder is already a local git repository with an incremental commit
history (project setup → models → repositories → services → controllers →
UI → docs) to demonstrate individual progress. To publish it:

```bash
git remote add origin <your-empty-GitHub-repo-URL>
git branch -M main
git push -u origin main
```

Take a screenshot of the resulting commit history for the Final Report and
for the viva presentation.

## 7. Class Diagram & Final Report

See `docs/class-diagram.png` and `docs/Final_Report.docx` in this folder.
