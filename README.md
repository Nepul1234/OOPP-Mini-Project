
# TECMIS - Student Management Information System (Java Swing with MySQL)

**TECMIS** (Student Management Information System) is a desktop application developed using **Java Swing** and **MySQL** to manage student records, courses, marks, attendance, and other academic-related information.

This application is designed as a part of the **Object-Oriented Programming Practicum course module** for **Level II (Semester I)**.

## Features

The TECMIS system has the following roles and functionalities:

### **Admin**
- Create and manage user profiles (Admin, Lecturer, Student, Technical Officer).
- Create and manage courses, timetables, and notices.
- Maintain student marks, attendance, and medical records.

### **Lecturer**
- Update their profiles (except username and password).
- Modify and add materials to courses.
- Upload marks for exams and view student details (marks, grades, GPA).
- View student attendance and medical records.

### **Technical Officer**
- Update their profiles (except username and password).
- Maintain student attendance and medical details.
- View notices and timetables.

### **Student**
- Update only contact details and profile picture.
- View their attendance, medical details, grades, and GPA.
- View course details and timetables.
- View notices.

## System Requirements

- **Java Development Kit (JDK)**: Version 8 or higher.
- **NetBeans IDE**: Used for project development.
- **MySQL**: For storing data related to users, courses, attendance, grades, etc.
- **JDBC Driver**: For MySQL database connectivity.

## Installation

### Step 1: Clone the Repository

Clone the project repository to your local machine:

```bash
git clone https://github.com/yourusername/tecmis-student-management.git
```

### Step 2: Open the Project in NetBeans

1. Open **NetBeans IDE**.
2. Go to **File > Open Project**.
3. Select the cloned project directory to open it.

### Step 3: Set Up the Database

1. Create a MySQL database named `tecmis` (or use the configured database name).

### Step 4: Run the Project

1. Right-click on the project folder in **NetBeans**.
2. Select **Run** to start the application.

### Step 5: Application Access

Once the system is running, you can log in using the following credentials:

- **Admin Login**:
  - Username: admin
  - Password: admin123

- **Student Login**:
  - Username: student1
  - Password: student123

## Project Structure

- **src/**: Contains Java source files, including GUI components and classes for managing user profiles, courses, marks, attendance, and database connectivity.
- **lib/**: Contains external libraries, like the MySQL JDBC driver.


## Usage

### Admin Features
- Manage user profiles (Admin, Lecturer, Student, Technical Officer).
- Create and maintain courses, timetables, and notices.
- Manage student attendance, marks, and medical details.

### Lecturer Features
- Modify course content and upload marks.
- View and manage student details, including grades and attendance.

### Technical Officer Features
- Add and maintain student attendance and medical records.
- View notices and department timetables.

### Student Features
- Update personal contact details and profile picture.
- View course details, grades, attendance, and medical records.
- Check timetables and notices.

## Screenshots
### Login page

![Image](https://github.com/user-attachments/assets/1f1e9ac5-633c-4f08-be58-f10523b4fcf7)

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more details.

