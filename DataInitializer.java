package com.lms.util;

import com.lms.entity.Course;
import com.lms.entity.CourseContent;
import com.lms.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    private static final List<Course> FALLBACK_COURSES = new ArrayList<>();
    static {
        buildSampleCourses();
    }

    public static List<Course> getFallbackCourses() {
        return FALLBACK_COURSES;
    }

    public static void initializeData(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            return;
        }
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

        
            User existingAdmin = session.get(User.class, "admin");
            if (existingAdmin == null) {
                User admin = new User("admin", PasswordUtil.hashPassword("admin123"));
                session.persist(admin);
            }

            // 2. Insert 8 courses and their topics if not exists
            for (Course sampleCourse : FALLBACK_COURSES) {
                Course existingCourse = session.get(Course.class, sampleCourse.getCourseId());
                if (existingCourse == null) {
                    Course newCourse = new Course(
                            sampleCourse.getCourseId(),
                            sampleCourse.getCourseName(),
                            sampleCourse.getCourseDescription(),
                            sampleCourse.getCourseDuration(),
                            sampleCourse.getCourseLevel()
                    );
                    session.persist(newCourse);

                    for (CourseContent sampleTopic : sampleCourse.getContents()) {
                        CourseContent newTopic = new CourseContent(
                                sampleTopic.getContentId(),
                                newCourse,
                                sampleTopic.getTopicTitle(),
                                sampleTopic.getTopicDescription()
                        );
                        session.persist(newTopic);
                    }
                }
            }

            tx.commit();
            System.out.println("LMS Database initialization check completed successfully.");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Database seeding warning (using fallback if DB offline): " + e.getMessage());
        }
    }

    private static void buildSampleCourses() {
        int contentIdCounter = 1;

        // Course 1: C Programming
        Course c1 = new Course(1, "C Programming",
                "Master structured programming, memory management, pointers, and low-level system fundamentals in C.",
                "6 Weeks", "Beginner");
        String[][] c1Topics = {
                {"Introduction to C", "History of C, procedural programming paradigm, and why C is the foundation of modern operating systems."},
                {"Structure of a C Program", "Preprocessor directives (#include), main() function, variable declarations, and return statements."},
                {"Variables and Data Types", "Primitive types including int, float, char, double, and size qualifiers like short and long."},
                {"Operators and Expressions", "Arithmetic, relational, logical, bitwise, and assignment operators with precedence rules."},
                {"Control Flow Statements", "Decision making using if, if-else, nested if, and switch-case blocks."},
                {"Loops in C", "Iterative execution using for, while, and do-while loops along with break and continue."},
                {"Arrays and Strings", "One-dimensional and multi-dimensional arrays, and null-terminated character arrays (strings)."},
                {"Functions and Recursion", "Function prototypes, call by value vs. call by reference, and recursive problem solving."},
                {"Pointers", "Understanding memory addresses, pointer arithmetic, and dynamic memory allocation (malloc, free)."},
                {"Structures and Unions", "Grouping heterogeneous data using struct and union keywords, and file handling basics."}
        };
        for (String[] t : c1Topics) {
            c1.getContents().add(new CourseContent(contentIdCounter++, c1, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c1);

        // Course 2: C++ Programming
        Course c2 = new Course(2, "C++ Programming",
                "Learn object-oriented programming, classes, inheritance, templates, and the Standard Template Library (STL).",
                "8 Weeks", "Intermediate");
        String[][] c2Topics = {
                {"Introduction to C++", "Evolution from C to C++, Bjarne Stroustrup's vision, and hybrid object-oriented capabilities."},
                {"Input/Output Streams", "Using cin, cout, cerr, and stream insertion (<<) and extraction (>>) operators."},
                {"Classes and Objects", "Defining blueprints with data members and member functions, and instantiating objects."},
                {"Constructors and Destructors", "Default, parameterized, and copy constructors along with automatic resource cleanup."},
                {"Inheritance", "Code reusability through single, multiple, multilevel, and hierarchical inheritance."},
                {"Polymorphism", "Compile-time (function/operator overloading) and run-time polymorphism (virtual functions)."},
                {"Encapsulation and Abstraction", "Access specifiers (private, protected, public) and abstract classes with pure virtual functions."},
                {"Friend Functions and Classes", "Granting controlled access to private and protected members outside the class."},
                {"Templates", "Writing generic functions and classes that work seamlessly across any data type."},
                {"Standard Template Library (STL)", "Working with vectors, maps, sets, iterators, and built-in sorting/searching algorithms."}
        };
        for (String[] t : c2Topics) {
            c2.getContents().add(new CourseContent(contentIdCounter++, c2, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c2);

        // Course 3: Java Programming (All 16 required topics)
        Course c3 = new Course(3, "Java Programming",
                "Learn Java programming fundamentals and object-oriented programming concepts.",
                "10 Weeks", "Beginner to Intermediate");
        String[][] c3Topics = {
                {"Introduction to Java", "Overview of Java as a high-level, class-based, object-oriented programming language designed for portability."},
                {"Features of Java", "Key pillars of Java: Platform Independence (Write Once, Run Anywhere), Object-Oriented, Robust, Secure, and Multithreaded."},
                {"JDK, JRE and JVM", "Java Development Kit (compiler & tools), Java Runtime Environment (libraries), and Java Virtual Machine (bytecode execution)."},
                {"Variables and Data Types", "Primitive types (byte, short, int, long, float, double, char, boolean) and reference variables."},
                {"Operators", "Arithmetic, unary, relational, logical, bitwise, ternary, and assignment operators in Java."},
                {"Conditional Statements", "Controlling program execution paths using if, else-if ladder, nested if, and switch statements."},
                {"Loops", "Repeating tasks efficiently using for, enhanced for-each, while, and do-while loops."},
                {"Arrays", "Single-dimensional and multi-dimensional fixed-size contiguous collections of elements."},
                {"Classes and Objects", "Creating object blueprints with fields, methods, constructors, and the 'this' keyword."},
                {"Inheritance", "Extending parent classes using the 'extends' keyword and invoking parent members with 'super'."},
                {"Polymorphism", "Method overloading (compile-time) and method overriding (run-time dynamic method dispatch)."},
                {"Abstraction", "Hiding implementation complexity using abstract classes and interfaces."},
                {"Encapsulation", "Binding data and methods together using private fields and public getter/setter methods."},
                {"Exception Handling", "Managing runtime errors gracefully using try, catch, finally, throw, and throws keywords."},
                {"Collections", "Dynamic data structures in the Java Collections Framework: ArrayList, LinkedList, HashSet, and HashMap."},
                {"JDBC", "Java Database Connectivity architecture, Drivers, Connection, PreparedStatement, and ResultSet for database operations."}
        };
        for (String[] t : c3Topics) {
            c3.getContents().add(new CourseContent(contentIdCounter++, c3, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c3);

        // Course 4: Kotlin Programming
        Course c4 = new Course(4, "Kotlin Programming",
                "Build concise, null-safe, modern applications using Kotlin for JVM and Android development.",
                "6 Weeks", "Intermediate");
        String[][] c4Topics = {
                {"Introduction to Kotlin", "Modern, expressive, statically-typed language by JetBrains that is 100% interoperable with Java."},
                {"Variables: val vs var", "Immutable read-only references (val) versus mutable variables (var) and smart type inference."},
                {"Null Safety", "Eliminating NullPointerExceptions using nullable types (?), safe calls (?.), and the Elvis operator (?:)."},
                {"Control Flow and When Expression", "Using 'if' as an expression and powerful pattern matching with the 'when' construct."},
                {"Functions and Default Arguments", "Single-expression functions, named parameters, and default parameter values."},
                {"Classes and Data Classes", "Primary/secondary constructors and auto-generated equals, hashCode, and toString with data classes."},
                {"Extension Functions", "Adding new functionality to existing classes without inheriting from them."},
                {"Lambda Expressions and Higher-Order Functions", "Passing functions as arguments and processing collections with filter, map, and forEach."},
                {"Collections in Kotlin", "Distinguishing between read-only (listOf, mapOf) and mutable collections (mutableListOf)."},
                {"Coroutines Basics", "Writing asynchronous, non-blocking code sequentially using suspend functions and dispatchers."}
        };
        for (String[] t : c4Topics) {
            c4.getContents().add(new CourseContent(contentIdCounter++, c4, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c4);

        // Course 5: Python Programming
        Course c5 = new Course(5, "Python Programming",
                "Write clean, readable Python code for automation, web backends, data analysis, and scripting.",
                "6 Weeks", "Beginner");
        String[][] c5Topics = {
                {"Introduction to Python", "Interpreted, high-level, dynamically typed language celebrated for its clean indentation-based syntax."},
                {"Variables and Dynamic Data Types", "Working with int, float, str, bool, and checking types dynamically with type()."},
                {"String Manipulation and F-Strings", "Slicing strings, built-in string methods, and clean string interpolation using f-strings."},
                {"Conditional Statements", "Decision control using if, elif, and else blocks."},
                {"Loops and Range", "Iterating over sequences with for loops, range(), enumerate(), and while loops."},
                {"Lists, Tuples and Sets", "Ordered mutable lists, immutable tuples, and unique unordered sets."},
                {"Dictionaries", "Storing and retrieving fast key-value pairs using Python dicts."},
                {"Functions and Modules", "Defining functions with def, *args, **kwargs, and importing standard library modules."},
                {"File Handling", "Reading and writing text and CSV files safely using 'with open()' context managers."},
                {"Object-Oriented Python", "Classes, __init__ constructor, self parameter, inheritance, and exception handling with try-except."}
        };
        for (String[] t : c5Topics) {
            c5.getContents().add(new CourseContent(contentIdCounter++, c5, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c5);

        // Course 6: HTML and CSS
        Course c6 = new Course(6, "HTML and CSS",
                "Design and build responsive, accessible, and visually appealing modern web pages from scratch.",
                "4 Weeks", "Beginner");
        String[][] c6Topics = {
                {"Introduction to Web & HTML5", "How browsers render web pages, HTTP basics, and the anatomy of HTML elements and attributes."},
                {"Semantic HTML Structure", "Using meaningful layout tags like <header>, <nav>, <main>, <section>, <article>, and <footer>."},
                {"Text, Links, Lists and Media", "Formatting headings, paragraphs, anchor links, ordered/unordered lists, and responsive images."},
                {"HTML Forms and Validation", "Building interactive forms with input types, labels, select dropdowns, and required attributes."},
                {"Introduction to CSS3", "Inline, internal, and external stylesheets, and the structure of CSS rulesets."},
                {"Selectors and Specificity", "Element, class, ID, attribute, and pseudo-class selectors (:hover, :focus) and cascade rules."},
                {"The CSS Box Model", "Understanding content, padding, border, margin, and box-sizing: border-box."},
                {"Typography and Colors", "Web fonts, rem/em units, hex, RGB, HSL color systems, and linear gradients."},
                {"Flexbox Layout", "One-dimensional alignment using display: flex, justify-content, align-items, and flex-wrap."},
                {"CSS Grid and Responsive Design", "Two-dimensional layouts with CSS Grid and adapting to mobile screens using @media queries."}
        };
        for (String[] t : c6Topics) {
            c6.getContents().add(new CourseContent(contentIdCounter++, c6, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c6);

        // Course 7: JavaScript
        Course c7 = new Course(7, "JavaScript",
                "Make web pages interactive by mastering modern ES6+ JavaScript, DOM manipulation, and async APIs.",
                "8 Weeks", "Beginner to Intermediate");
        String[][] c7Topics = {
                {"Introduction to JavaScript", "Role of JavaScript in the browser, script loading strategies, and console debugging."},
                {"Variables: let, const and Data Types", "Block-scoped declarations and primitive vs. reference data types."},
                {"Operators and Template Literals", "Strict equality (===), logical operators, and multi-line strings with ${} interpolation."},
                {"Functions and Arrow Syntax", "Function declarations, expressions, and concise ES6 arrow functions (=>)."},
                {"Arrays and Higher-Order Methods", "Transforming data cleanly using map(), filter(), reduce(), and find()."},
                {"Objects and Destructuring", "Object literals, methods, the 'this' keyword, and object/array destructuring."},
                {"DOM Manipulation", "Selecting elements with querySelector, modifying text/styles, and creating DOM nodes dynamically."},
                {"Events and Event Delegation", "Handling user clicks, form submissions, keyboard events, and event bubbling."},
                {"Asynchronous JS and Promises", "Understanding the event loop, timers, Promises, and non-blocking execution."},
                {"Fetch API and Async/Await", "Fetching JSON data from REST APIs using async/await and handling errors with try/catch."}
        };
        for (String[] t : c7Topics) {
            c7.getContents().add(new CourseContent(contentIdCounter++, c7, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c7);

        // Course 8: SQL and Database Management
        Course c8 = new Course(8, "SQL and Database Management",
                "Master relational database design, Oracle SQL queries, joins, constraints, and transaction management.",
                "6 Weeks", "Beginner to Intermediate");
        String[][] c8Topics = {
                {"Relational Database Concepts", "Tables, rows, columns, relational schemas, and RDBMS architecture."},
                {"SQL Data Types and DDL", "Creating, altering, and dropping tables using CREATE TABLE, ALTER, and DROP commands."},
                {"Database Constraints", "Enforcing data integrity using PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL, and CHECK."},
                {"DML: INSERT, UPDATE and DELETE", "Populating tables with new records and safely updating or deleting existing rows."},
                {"SELECT Queries and Filtering", "Retrieving specific columns and filtering rows with WHERE, AND, OR, IN, BETWEEN, and LIKE."},
                {"Sorting and Limiting Results", "Ordering query output with ORDER BY ASC/DESC and fetching top records."},
                {"Aggregate Functions and GROUP BY", "Summarizing data using COUNT(), SUM(), AVG(), MIN(), MAX(), GROUP BY, and HAVING."},
                {"SQL Joins", "Combining related tables using INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN."},
                {"Subqueries and Views", "Writing nested queries and creating virtual tables (Views) for security and simplicity."},
                {"Transactions and Normalization", "ACID properties, COMMIT, ROLLBACK, and normalizing tables (1NF, 2NF, 3NF) to eliminate redundancy."}
        };
        for (String[] t : c8Topics) {
            c8.getContents().add(new CourseContent(contentIdCounter++, c8, t[0], t[1]));
        }
        FALLBACK_COURSES.add(c8);
    }
}
