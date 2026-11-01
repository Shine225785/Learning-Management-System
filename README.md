# Learning Management System (LMS)

## 1. Project Title
**Learning Management System (LMS) — Java Full Stack MCA Mini-Project**

## 2. Project Introduction
The **Learning Management System (LMS)** is a web-based educational application developed using **HTML, CSS, JSP, Java Servlets, Hibernate ORM, and Oracle Database**. It provides an intuitive platform where students can browse structured programming courses and study topic-by-topic explanations dynamically fetched from an Oracle Database.

*(Note: As requested, the application is currently configured in `index.jsp` to bypass the login screen on startup and open directly on the Course Dashboard with an active `admin` session. The complete `LoginServlet` and `login.jsp` are also fully implemented and accessible whenever needed or by clicking Logout.)*

## 3. Project Objectives
- Understand the **MVC (Model-View-Controller)** architecture using JSP and Java Servlets.
- Implement Object-Relational Mapping (ORM) using **Hibernate 6** with **Oracle Database**.
- Demonstrate automatic database schema creation and non-destructive seeding of courses and topics.
- Build a clean, responsive frontend using standard **HTML5 and CSS3** without complex third-party JS frameworks.
- Provide a well-documented, easy-to-explain codebase suitable for an MCA practical examination and viva.

## 4. Technologies Used
| Component | Technology |
|---|---|
| Frontend | HTML5, CSS3 |
| Dynamic Web Pages | Jakarta JSP (JavaServer Pages) |
| Backend Controller | Jakarta Servlets (`jakarta.servlet-api 6.0`) |
| ORM Framework | Hibernate ORM (`6.4.4.Final`) |
| Database | Oracle Database (`jdbc:oracle:thin:@localhost:1521/FREE`) |
| Build Tool | Apache Maven (WAR Packaging) |
| Web Server | Apache Tomcat 10.1+ |
| Programming Language | Java 17 / Java 21 |

## 5. Project Features
- **Direct Dashboard Access (Skip Login Mode):** Opens directly to the Course Dashboard for rapid demonstration, while preserving full Login/Logout functionality.
- **Complete Login & Authentication Module:** Uses SHA-256 password hashing (`PasswordUtil.java`) and Hibernate parameterized queries (`UserDAO.java`).
- **Dynamic Course Dashboard:** Displays 8 core programming courses (*C, C++, Java, Kotlin, Python, HTML & CSS, JavaScript, and SQL*).
- **Single Dynamic Course Details Page (`course-details.jsp`):** Dynamically renders course metadata and all syllabus topics for whichever course the user selects.
- **Automatic Database Initialization:** Automatically creates tables and inserts the default `admin` user and all 8 courses + 86 topics if they do not already exist.
- **Resilient Fallback Mode:** If the local Oracle Database service is stopped during a viva demonstration, `CourseDAO` seamlessly falls back to the structured course data in `DataInitializer` so the application never crashes.

## 6. Complete Folder Structure
```text
LearningManagementSystem/
│
├── pom.xml
├── README.md
│
├── database/
│   └── setup.sql
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── lms/
        │           ├── entity/
        │           │   ├── User.java
        │           │   ├── Course.java
        │           │   └── CourseContent.java
        │           ├── dao/
        │           │   ├── UserDAO.java
        │           │   └── CourseDAO.java
        │           ├── servlet/
        │           │   ├── LoginServlet.java
        │           │   ├── CourseServlet.java
        │           │   └── LogoutServlet.java
        │           └── util/
        │               ├── HibernateUtil.java
        │               ├── PasswordUtil.java
        │               └── DataInitializer.java
        │
        ├── resources/
        │   └── hibernate.cfg.xml
        │
        └── webapp/
            ├── index.jsp
            ├── login.jsp
            ├── dashboard.jsp
            ├── course-details.jsp
            ├── css/
            │   ├── login.css
            │   ├── dashboard.css
            │   └── course.css
            └── WEB-INF/
                └── web.xml
```

## 7. Database Configuration
Configured inside `src/main/resources/hibernate.cfg.xml`:
- **Driver Class:** `oracle.jdbc.OracleDriver`
- **JDBC URL:** `jdbc:oracle:thin:@localhost:1521/FREE`
- **Username:** `system`
- **Password:** `Muskan123`
- **Schema Mode:** `hibernate.hbm2ddl.auto = update` (automatically creates missing tables without deleting existing data).

## 8. Database Table Descriptions
1. **`USERS` Table:**
   - `user_uid` (VARCHAR2(50), Primary Key) — Mapped to `uid` field in `User.java` (named `user_uid` in SQL because `UID` is a reserved keyword in Oracle).
   - `password` (VARCHAR2(255), NOT NULL) — Stores the SHA-256 hashed password.
2. **`COURSES` Table:**
   - `course_id` (NUMBER, Primary Key)
   - `course_name` (VARCHAR2(100))
   - `course_description` (VARCHAR2(500))
   - `course_duration` (VARCHAR2(50))
   - `course_level` (VARCHAR2(50))
3. **`COURSE_CONTENT` Table:**
   - `content_id` (NUMBER, Primary Key)
   - `course_id` (NUMBER, Foreign Key referencing `COURSES.course_id`)
   - `topic_title` (VARCHAR2(150))
   - `topic_description` (VARCHAR2(1000))

## 9. Maven Dependencies (`pom.xml`)
- `jakarta.servlet:jakarta.servlet-api:6.0.0`
- `jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.1.1`
- `org.hibernate.orm:hibernate-core:6.4.4.Final`
- `com.oracle.database.jdbc:ojdbc11:23.3.0.23.09`

## 10. How to Build the Project
Open a terminal inside the `LearningManagementSystem` root folder and run:
```bash
mvn clean package
```
Maven will automatically download all dependencies from Maven Central and generate `target/LearningManagementSystem.war`.

## 11. How to Run Using Apache Tomcat
1. Install **Apache Tomcat 10.1+**.
2. Copy `target/LearningManagementSystem.war` into Tomcat's `webapps/` directory (or run the project directly on Tomcat from Eclipse / IntelliJ IDEA / NetBeans).
3. Start Tomcat (`bin/startup.bat` on Windows or `bin/startup.sh` on Linux/macOS).

## 12. Application URL
- **Direct Dashboard (Default):** `http://localhost:8080/LearningManagementSystem/`
- **Login Page:** `http://localhost:8080/LearningManagementSystem/login.jsp`

## 13. Sample Login Credentials
- **User ID (UID):** `admin`
- **Password:** `admin123`

## 14. Explanation of Important Java Classes
- **`User.java`, `Course.java`, `CourseContent.java`:** JPA/Hibernate `@Entity` classes representing database tables and `@OneToMany` / `@ManyToOne` relationships.
- **`HibernateUtil.java`:** Builds the singleton `SessionFactory` from `hibernate.cfg.xml` and triggers `DataInitializer`.
- **`DataInitializer.java`:** Automatically inserts the `admin` user and 8 programming courses with their topics if the database is empty.
- **`PasswordUtil.java`:** Hashes plain-text passwords using `SHA-256`.
- **`UserDAO.java` & `CourseDAO.java`:** Execute parameterized HQL queries (`FROM Course`, `FROM User`) via Hibernate Sessions.
- **`CourseServlet.java`:** Main controller servlet that loads courses for `dashboard.jsp` or loads a specific course's topics for `course-details.jsp`.
- **`LoginServlet.java` & `LogoutServlet.java`:** Manage user authentication and `HttpSession` lifecycle.

## 15. Application Workflow
1. User opens the application URL (`index.jsp`), which initializes the user session and forwards to `CourseServlet`.
2. `CourseServlet` calls `CourseDAO.getAllCourses()` via Hibernate and forwards the list to `dashboard.jsp`.
3. User clicks **View Course** on any of the 8 course cards (`CourseServlet?action=details&id=3`).
4. `CourseServlet` fetches the selected course and its `CourseContent` list via Hibernate and forwards to `course-details.jsp`.
5. Clicking **Back to Courses** returns to the dashboard; clicking **Logout** invokes `LogoutServlet`, invalidates the `HttpSession`, and opens `login.jsp`.

## 16. Troubleshooting Common Errors
- **ORA-00904 / Reserved Keyword `UID`:** In Oracle SQL, `UID` is a built-in function/keyword. This project maps the `uid` Java property to `@Column(name = "user_uid")` so Oracle never throws a syntax error.
- **Oracle Listener Refused Connection:** Ensure your Oracle `FREE` database service and `TNSListener` are running on port `1521`. Even if Oracle is offline, the built-in fallback ensures your UI and course pages still load smoothly during a demo.
- **Tomcat Version Mismatch:** Because this project uses `jakarta.servlet.*` (Jakarta EE 10), deploy it on **Apache Tomcat 10.1.x**, not Tomcat 9.
