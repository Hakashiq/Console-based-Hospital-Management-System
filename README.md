Hospital Management System (Console-Based Java Project)

A simple, menu-driven Hospital Management System built using Core Java.
This project manages Patients, Doctors, and Appointments using clean OOP structure and service classes.

⭐ Features

👨‍⚕️ Doctor Management
Add new doctors
View all doctors
Search doctor by name or ID
Update doctor details
Delete doctor

🧑‍⚕️ Patient Management
Add patients
View all patients
Search patient by name or ID
Update patient details
Delete patient

📅 Appointment Management
Create a new appointment
View appointments by doctor
View appointments by patient
Cancel appointments

🗂️ Project Structure
/src
 ├── models/
 │    ├── Doctor.java
 │    ├── Patient.java
 │    └── Appointment.java
 ├── services/
 │    ├── DoctorService.java
 │    ├── PatientService.java
 │    └── AppointmentService.java
 ├── utils/
 │    └── InputUtils.java
 └── Main.java

🛠️ Tech Used
Java 17+
OOP Concepts
Collections (List, Map)
Clean layered architecture

🚀 How to Run

1. Compile
javac Main.java

2. Run
java Main

If using packages:

javac -d . src/**/*.java
java Main

🎯 Highlights

Fully console-based
Zero external libraries
Clean input-handling utility
Easy to extend (database, GUI, Spring Boot, etc.)

📌 Future Enhancements

 Add MySQL database support
 Add login system (Admin/Doctor/Receptionist)
 Convert into Spring Boot REST API
 Add a frontend dashboard.
