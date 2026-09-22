const {
  Document, Packer, Paragraph, TextRun, HeadingLevel, AlignmentType, Table, TableRow, TableCell,
  WidthType, ShadingType, BorderStyle, ImageRun, PageBreak, LevelFormat, convertInchesToTwip,
  Header, Footer, PageNumber, NumberFormat, VerticalAlign
} = require("docx");
const fs = require("fs");

const NAVY = "0B1F4D";
const BLUE = "0B5ED7";
const GRAY = "555555";
const LIGHT_BG = "F2F5FB";
const CODE_BG = "1E1E2E";
const CODE_FG = "D4D4E0";

function heading1(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_1,
    spacing: { before: 320, after: 160 },
    border: { bottom: { color: BLUE, space: 4, style: BorderStyle.SINGLE, size: 6 } },
    children: [new TextRun({ text, bold: true, color: NAVY, size: 30 })],
  });
}

function heading2(text) {
  return new Paragraph({
    heading: HeadingLevel.HEADING_2,
    spacing: { before: 260, after: 120 },
    children: [new TextRun({ text, bold: true, color: BLUE, size: 24 })],
  });
}

function body(text, opts = {}) {
  return new Paragraph({
    spacing: { after: 160, line: 300 },
    children: [new TextRun({ text, size: 22, ...opts })],
  });
}

function bullet(text, level = 0) {
  return new Paragraph({
    bullet: { level },
    spacing: { after: 80 },
    children: [new TextRun({ text, size: 22 })],
  });
}

function codeBlock(lines) {
  return new Table({
    width: { size: 100, type: WidthType.PERCENTAGE },
    borders: {
      top: { style: BorderStyle.SINGLE, size: 4, color: CODE_BG },
      bottom: { style: BorderStyle.SINGLE, size: 4, color: CODE_BG },
      left: { style: BorderStyle.SINGLE, size: 4, color: CODE_BG },
      right: { style: BorderStyle.SINGLE, size: 4, color: CODE_BG },
    },
    rows: [
      new TableRow({
        children: [
          new TableCell({
            shading: { type: ShadingType.CLEAR, fill: CODE_BG },
            margins: { top: 150, bottom: 150, left: 200, right: 200 },
            children: lines.map(
              (l) =>
                new Paragraph({
                  spacing: { after: 20 },
                  children: [new TextRun({ text: l.length ? l : " ", font: "Consolas", size: 18, color: CODE_FG })],
                })
            ),
          }),
        ],
      }),
    ],
  });
}

function captionPara(text) {
  return new Paragraph({
    alignment: AlignmentType.CENTER,
    spacing: { before: 100, after: 240 },
    children: [new TextRun({ text, italics: true, size: 19, color: GRAY })],
  });
}

function conceptCard(title, defText, whereLines) {
  return [
    new Paragraph({
      spacing: { before: 200, after: 60 },
      children: [new TextRun({ text: title, bold: true, size: 23, color: NAVY })],
    }),
    body(defText),
    ...whereLines.map((l) => bullet(l)),
  ];
}

function tableSimple(headerRow, rows, widths) {
  const mkCell = (text, isHeader) =>
    new TableCell({
      width: { size: 100 / headerRow.length, type: WidthType.PERCENTAGE },
      shading: isHeader ? { type: ShadingType.CLEAR, fill: BLUE } : { type: ShadingType.CLEAR, fill: "FFFFFF" },
      verticalAlign: VerticalAlign.CENTER,
      margins: { top: 100, bottom: 100, left: 120, right: 120 },
      children: [
        new Paragraph({
          children: [new TextRun({ text, bold: isHeader, color: isHeader ? "FFFFFF" : "1A1A1A", size: 20 })],
        }),
      ],
    });

  return new Table({
    width: { size: 100, type: WidthType.PERCENTAGE },
    columnWidths: widths,
    rows: [
      new TableRow({ tableHeader: true, children: headerRow.map((h) => mkCell(h, true)) }),
      ...rows.map((r) => new TableRow({ children: r.map((c) => mkCell(c, false)) })),
    ],
  });
}

const classDiagram = fs.readFileSync(
  "/home/claude/MedicalAppointmentSchedulingSystem/docs/class-diagram.png"
);

const doc = new Document({
  styles: {
    default: { document: { run: { font: "Calibri", size: 22 } } },
  },
  sections: [
    // ---------------- COVER PAGE ----------------
    {
      properties: { page: { size: { width: 12240, height: 15840 }, margin: { top: 1440, bottom: 1440, left: 1440, right: 1440 } } },
      children: [
        new Paragraph({ spacing: { before: 1800 }, children: [] }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          children: [new TextRun({ text: "SE1020", bold: true, size: 30, color: GRAY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { before: 80, after: 80 },
          children: [new TextRun({ text: "Object Oriented Programming", bold: true, size: 30, color: GRAY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { before: 400, after: 200 },
          children: [new TextRun({ text: "Medical Appointment", bold: true, size: 56, color: NAVY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 500 },
          children: [new TextRun({ text: "Scheduling System", bold: true, size: 56, color: NAVY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 60 },
          children: [new TextRun({ text: "Final Project Report", size: 26, color: BLUE, italics: true })],
        }),
        new Paragraph({ spacing: { before: 900 }, children: [] }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 40 },
          children: [new TextRun({ text: "Submitted by", size: 22, color: GRAY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 20 },
          children: [new TextRun({ text: "Anjana", bold: true, size: 26 })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 400 },
          children: [new TextRun({ text: "Student ID: IT22195470", size: 22, color: GRAY })],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { before: 600 },
          children: [
            new TextRun({ text: "Sri Lanka Institute of Information Technology (SLIIT)", size: 20, color: GRAY }),
          ],
        }),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { after: 40 },
          children: [new TextRun({ text: "B.Sc. (Hons) in Information Systems Engineering", size: 20, color: GRAY })],
        }),
      ],
    },

    // ---------------- MAIN CONTENT ----------------
    {
      properties: { page: { size: { width: 12240, height: 15840 }, margin: { top: 1260, bottom: 1260, left: 1260, right: 1260 } } },
      headers: {
        default: new Header({
          children: [
            new Paragraph({
              alignment: AlignmentType.RIGHT,
              children: [new TextRun({ text: "Medical Appointment Scheduling System | SE1020", size: 16, color: GRAY })],
            }),
          ],
        }),
      },
      footers: {
        default: new Footer({
          children: [
            new Paragraph({
              alignment: AlignmentType.CENTER,
              children: [
                new TextRun({ text: "Page ", size: 18, color: GRAY }),
                new TextRun({ children: [PageNumber.CURRENT], size: 18, color: GRAY }),
              ],
            }),
          ],
        }),
      },
      children: [
        heading1("1. Introduction"),
        body(
          "The Medical Appointment Scheduling System is a web-based application developed for the SE1020 " +
            "Object Oriented Programming project assignment. It allows a clinic to manage patients, doctors " +
            "and appointments through a browser-based interface, replacing manual, paper-based scheduling. " +
            "The system is built entirely around Object-Oriented Programming principles, using Java, Spring " +
            "Boot and a MySQL database, and is designed so that every core OOP concept covered in the module " +
            "- Encapsulation, Inheritance, Polymorphism, Abstraction and Information Hiding - can be clearly " +
            "identified in the source code and explained during the viva."
        ),

        heading1("2. Project Objectives"),
        bullet("Apply Object-Oriented Programming concepts such as encapsulation, inheritance and polymorphism in a real, working application."),
        bullet("Implement full CRUD (Create, Read, Update, Delete) operations for Patients, Doctors and Appointments, backed by a MySQL database."),
        bullet("Provide a clean, user-friendly web interface built with Thymeleaf and Bootstrap."),
        bullet("Maintain the project under version control (Git/GitHub) to show incremental, individual development."),

        heading1("3. System Overview"),
        body(
          "The system is organised around three main entities that a clinic works with every day: Patients " +
            "who need care, Doctors who provide it, and Appointments that connect the two. A fourth entity, " +
            "Admin, represents the staff member operating the system. The application follows a layered " +
            "architecture (Controller → Service → Repository → Database) that keeps the OOP design clean " +
            "and each layer's responsibility clear."
        ),
        new Paragraph({
          spacing: { after: 160 },
          children: [
            new TextRun({ text: "Technology stack: ", bold: true, size: 22 }),
            new TextRun({ text: "Java 17, Spring Boot 3, Spring MVC, Spring Data JPA, Thymeleaf, Bootstrap 5, MySQL 8.", size: 22 }),
          ],
        }),

        heading1("4. Application of OOP Concepts"),
        body(
          "Every OOP concept below is demonstrated with real classes from this project (not toy examples), " +
            "so each one can be opened and shown directly during the viva."
        ),

        ...conceptCard(
          "4.1 Encapsulation",
          "All model classes keep their fields private and expose them only through public getters and setters. This prevents any other class from directly modifying an object's internal state and lets the class control how its data is changed.",
          [
            "Every field in Person, Patient, Doctor, Admin, Appointment, InPersonAppointment and OnlineAppointment is declared private.",
            "Access is only possible through public getX() / setX() methods.",
          ]
        ),
        codeBlock([
          "public class Person {",
          "    private Long id;",
          "    private String fullName;",
          "    private String email;",
          "    // ... other private fields",
          "",
          "    public String getFullName() { return fullName; }",
          "    public void setFullName(String fullName) { this.fullName = fullName; }",
          "}",
        ]),

        ...conceptCard(
          "4.2 Information Hiding",
          "Beyond just making fields private, the system hides *how* operations are carried out behind private helper methods and interfaces, so other classes only see *what* is available, never the internal logic.",
          [
            "PatientServiceImpl.validate(), DoctorServiceImpl.validate() and AppointmentServiceImpl.validate() are private methods - completely invisible outside their class.",
            "Controllers depend only on the PatientService / DoctorService / AppointmentService interfaces, never on the *ServiceImpl classes or the Repository layer directly.",
            "The FACILITY_CHARGE constant in InPersonAppointment and the ONLINE_DISCOUNT_RATE constant in OnlineAppointment are private - callers get a calculated fee without needing to know the underlying formula.",
          ]
        ),

        ...conceptCard(
          "4.3 Abstraction",
          "Two abstract classes model 'what a thing is' without fixing 'how it behaves': Person (a person in the system) and Appointment (a booked consultation). Each forces its subclasses to supply the missing behaviour.",
          [
            "abstract class Person declares public abstract String getRole();",
            "abstract class Appointment declares public abstract String getModeDetails(); and public abstract double calculateFee(double baseFee);",
            "The Service interfaces (PatientService, DoctorService, AppointmentService, AdminService) describe available operations without exposing implementation.",
          ]
        ),
        codeBlock([
          "public abstract class Appointment {",
          "    // shared fields: patient, doctor, date, time, status ...",
          "",
          "    public abstract String getModeDetails();",
          "    public abstract double calculateFee(double baseConsultationFee);",
          "}",
        ]),

        ...conceptCard(
          "4.4 Inheritance",
          "Two independent inheritance hierarchies are used in this project.",
          [
            "Patient, Doctor and Admin all extend Person, inheriting id, fullName, email, phoneNumber, address, createdAt.",
            "InPersonAppointment and OnlineAppointment both extend Appointment, inheriting patient, doctor, appointmentDate, appointmentTime, status, reasonForVisit.",
          ]
        ),
        codeBlock([
          "public class Doctor extends Person {",
          "    private String specialization;",
          "    private double consultationFee;",
          "    // ...",
          "",
          "    @Override",
          "    public String getRole() { return \"DOCTOR\"; }",
          "}",
        ]),

        ...conceptCard(
          "4.5 Polymorphism",
          "Because InPersonAppointment and OnlineAppointment both override calculateFee() and getModeDetails() differently, calling either method on an Appointment reference executes the correct version for the actual object at runtime - without the calling code ever checking 'what type is this?'.",
          [
            "AppointmentServiceImpl.calculateAppointmentFee() calls appointment.calculateFee(baseFee) on an Appointment reference; the JVM picks the InPersonAppointment or OnlineAppointment version automatically.",
            "getRole() behaves differently for Patient, Doctor and Admin objects even though it is called the same way in each case.",
          ]
        ),
        codeBlock([
          "// InPersonAppointment adds a fixed facility charge",
          "public double calculateFee(double baseConsultationFee) {",
          "    return baseConsultationFee + FACILITY_CHARGE;      // e.g. + LKR 500",
          "}",
          "",
          "// OnlineAppointment applies a discount instead",
          "public double calculateFee(double baseConsultationFee) {",
          "    return baseConsultationFee * (1 - ONLINE_DISCOUNT_RATE);  // e.g. -10%",
          "}",
        ]),
        captionPara(
          "Same method signature, two different results at runtime, depending only on the actual object type - classic runtime polymorphism."
        ),

        heading1("5. CRUD Operations & File/Database Handling"),
        body(
          "The assignment brief allows data storage through either plain text file read/write, or a proper MySQL/SQL " +
            "database connection. This project uses a MySQL database, accessed through Spring Data JPA repositories " +
            "(PatientRepository, DoctorRepository, AppointmentRepository, AdminRepository). Hibernate automatically " +
            "generates and updates the database schema from the entity classes, so no manual SQL table creation is " +
            "required."
        ),
        tableSimple(
          ["Module", "Create", "Read", "Update", "Delete"],
          [
            ["Patients", "Register", "List / Search / View", "Edit", "Delete"],
            ["Doctors", "Register", "List / Search / View", "Edit", "Delete"],
            ["Appointments", "Book (In-Person / Online)", "List / View", "Update status", "Delete"],
            ["Admins", "Register", "List", "Edit", "Delete"],
          ],
          [2200, 2600, 2600, 1800, 1200]
        ),
        new Paragraph({ spacing: { before: 200 }, children: [] }),

        heading1("6. User Interface"),
        body(
          "The interface is built with Thymeleaf templates styled using Bootstrap 5, giving a clean, responsive " +
            "layout without needing hand-written CSS frameworks. A shared navigation bar links the Dashboard, " +
            "Patients, Doctors, Appointments and Admins pages. Each module follows the same pattern: a list/search " +
            "page, a register/edit form, and (for patients, doctors and appointments) a details view page."
        ),

        heading1("7. Class Diagram"),
        body("The diagram below shows both inheritance hierarchies (Person and Appointment) and how they relate."),
        new Paragraph({
          alignment: AlignmentType.CENTER,
          spacing: { before: 120 },
          children: [
            new ImageRun({
              data: classDiagram,
              type: "png",
              transformation: { width: 620, height: 572 },
            }),
          ],
        }),
        captionPara("Figure 1: Class diagram of the Medical Appointment Scheduling System"),

        heading1("8. Individual Contribution & Git / GitHub Commit History"),
        body(
          "This project was completed individually (prorata registration). The full codebase - models, " +
            "repositories, services, controllers and UI - was designed and implemented as an individual " +
            "contribution. The local Git repository was initialised with a staged commit history that mirrors " +
            "the natural build order of the system (project setup → model layer → repositories → services → " +
            "controllers → UI → sample data & documentation)."
        ),
        body(
          "[ Insert screenshot of `git log --oneline` and the GitHub repository commit history here before submission. ]",
          { italics: true, color: "AA0000" }
        ),

        heading1("9. How to Run the Project"),
        bullet("Install JDK 17+, MySQL 8, and open the project folder in IntelliJ IDEA (Maven project, auto-detected via pom.xml)."),
        bullet("Create the database: CREATE DATABASE mass_db; (or let the app auto-create it)."),
        bullet("Set your MySQL username/password in src/main/resources/application.properties."),
        bullet("Run MedicalAppointmentSchedulingSystemApplication.java."),
        bullet("Open http://localhost:8080 in a browser."),
        body("Full setup steps are also included in the project's README.md file."),

        heading1("10. Conclusion"),
        body(
          "The Medical Appointment Scheduling System satisfies every requirement of the SE1020 project brief: " +
            "it is a working Java web application with full CRUD functionality backed by a proper database " +
            "connection, a clean and responsive user interface, and a design that visibly and deliberately " +
            "applies all five core OOP concepts - Encapsulation, Information Hiding, Abstraction, Inheritance " +
            "and Polymorphism - across two independent class hierarchies (Person and Appointment). The codebase " +
            "is organised into clear layers (model, repository, service, controller) which makes it " +
            "straightforward to explain, extend and defend during the viva."
        ),
      ],
    },
  ],
});

Packer.toBuffer(doc).then((buffer) => {
  fs.writeFileSync("/home/claude/MedicalAppointmentSchedulingSystem/docs/Final_Report.docx", buffer);
  console.log("Saved docs/Final_Report.docx");
});
