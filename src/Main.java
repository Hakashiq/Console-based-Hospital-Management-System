import services.PatientService;
import services.DoctorService;
import services.AppointmentService;
import utils.InputUtils;

import models.Patient;
import models.Doctor;
import models.Appointment;

import java.util.List;

public class Main {

    private static final PatientService patientService = new PatientService();
    private static final DoctorService doctorService = new DoctorService();
    private static final AppointmentService appointmentService = new AppointmentService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n================ HOSPITAL MANAGEMENT SYSTEM ================");
            System.out.println("1. Register Patient");
            System.out.println("2. Register Doctor");
            System.out.println("3. Book Appointment");
            System.out.println("4. View All Appointments");
            System.out.println("5. Search Patient by ID");
            System.out.println("6. Search Patient by Name");
            System.out.println("7. Search Doctor by ID");
            System.out.println("8. Search Doctor by Name");
            System.out.println("9. Update Patient Details");
            System.out.println("0. Exit");
            System.out.println("============================================================");

            int choice = InputUtils.readInt("Enter your choice: ");

            switch (choice) {

                case 1 -> registerPatient();
                case 2 -> registerDoctor();
                case 3 -> bookAppointment();
                case 4 -> viewAppointments();
                case 5 -> searchPatientById();
                case 6 -> searchPatientByName();
                case 7 -> searchDoctorById();
                case 8 -> searchDoctorByName();
                case 9 -> updatePatient();

                case 0 -> {
                    System.out.println("\n👋 Exiting System... Goodbye!");
                    return;
                }

                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    // ============================================================
    // REGISTER PATIENT
    // ============================================================
    private static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        System.out.println("Type 0 anytime to cancel.");

        String name = InputUtils.readNonEmptyString("Name: ");
        if (name.equals("0")) { cancelMsg(); return; }

        int age = InputUtils.readInt("Age (0 to cancel): ");
        if (age == 0) { cancelMsg(); return; }

        String gender = InputUtils.readNonEmptyString("Gender: ");
        if (gender.equals("0")) { cancelMsg(); return; }

        String phone = InputUtils.readNonEmptyString("Phone: ");
        if (phone.equals("0")) { cancelMsg(); return; }

        String disease = InputUtils.readNonEmptyString("Disease: ");
        if (disease.equals("0")) { cancelMsg(); return; }

        patientService.addPatient(name, age, gender, phone, disease);
    }

    // ============================================================
    // REGISTER DOCTOR
    // ============================================================
    private static void registerDoctor() {
        System.out.println("\n--- Register New Doctor ---");
        System.out.println("Type 0 anytime to cancel.");

        String name = InputUtils.readNonEmptyString("Name: ");
        if (name.equals("0")) { cancelMsg(); return; }

        String specialization = InputUtils.readNonEmptyString("Specialization: ");
        if (specialization.equals("0")) { cancelMsg(); return; }

        String days = InputUtils.readNonEmptyString("Available Days: ");
        if (days.equals("0")) { cancelMsg(); return; }

        String time = InputUtils.readNonEmptyString("Available Time: ");
        if (time.equals("0")) { cancelMsg(); return; }

        doctorService.addDoctor(name, specialization, days, time);
    }

    // ============================================================
    // BOOK APPOINTMENT
    // ============================================================
    private static void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");
        System.out.println("Type 0 anytime to cancel.");

        String patientId = InputUtils.readNonEmptyString("Enter Patient ID: ");
        if (patientId.equals("0")) { cancelMsg(); return; }

        String doctorId = InputUtils.readNonEmptyString("Enter Doctor ID: ");
        if (doctorId.equals("0")) { cancelMsg(); return; }

        String date = InputUtils.readNonEmptyString("Date (YYYY-MM-DD): ");
        if (date.equals("0")) { cancelMsg(); return; }

        String time = InputUtils.readNonEmptyString("Time (HH:MM): ");
        if (time.equals("0")) { cancelMsg(); return; }

        if (patientService.findById(patientId) == null) {
            System.out.println("❌ Patient not found!");
            return;
        }

        if (doctorService.findById(doctorId) == null) {
            System.out.println("❌ Doctor not found!");
            return;
        }

        appointmentService.bookAppointment(patientId, doctorId, date, time);
    }

    // ============================================================
    // VIEW APPOINTMENTS (PATIENT + DOCTOR NAME)
    // ============================================================
    private static void viewAppointments() {
        System.out.println("\n--- All Appointments ---");

        List<Appointment> list = appointmentService.getAllAppointments();

        if (list.isEmpty()) {
            System.out.println("No appointments found!");
            return;
        }

        for (Appointment a : list) {

            String patientName = patientService.findById(a.getPatientId()).getName();
            String doctorName = doctorService.findById(a.getDoctorId()).getName();

            System.out.println(
                    "\nAppointment ID: " + a.getAppointmentId() +
                            "\nPatient: " + patientName +
                            "\nDoctor: " + doctorName +
                            "\nDate: " + a.getDate() +
                            "\nTime: " + a.getTime() +
                            "\nStatus: " + a.getStatus() +
                            "\n----------------------------"
            );
        }
    }

    // ============================================================
    // SEARCH DOCTOR BY ID
    // ============================================================
    private static void searchDoctorById() {
        System.out.println("\n--- Search Doctor By ID ---");

        String id = InputUtils.readNonEmptyString("Enter Doctor ID (0 to cancel): ");
        if (id.equals("0")) { cancelMsg(); return; }

        Doctor d = doctorService.findById(id);

        if (d == null) {
            System.out.println("❌ Doctor not found!");
            return;
        }

        printDoctor(d);
    }

    // ============================================================
    // SEARCH DOCTOR BY NAME
    // ============================================================
    private static void searchDoctorByName() {
        System.out.println("\n--- Search Doctor By Name ---");

        String name = InputUtils.readNonEmptyString("Enter Doctor Name (0 to cancel): ");
        if (name.equals("0")) { cancelMsg(); return; }

        List<Doctor> list = doctorService.getAllDoctors();
        boolean found = false;

        for (Doctor d : list) {
            if (d.getName().equalsIgnoreCase(name)) {
                printDoctor(d);
                found = true;
            }
        }

        if (!found) System.out.println("❌ No doctor found.");
    }

    // ============================================================
    // SEARCH PATIENT BY ID
    // ============================================================
    private static void searchPatientById() {
        System.out.println("\n--- Search Patient By ID ---");

        String id = InputUtils.readNonEmptyString("Enter Patient ID (0 to cancel): ");
        if (id.equals("0")) { cancelMsg(); return; }

        Patient p = patientService.findById(id);

        if (p == null) {
            System.out.println("❌ Patient not found!");
            return;
        }

        printPatient(p);
    }

    // ============================================================
    // SEARCH PATIENT BY NAME
    // ============================================================
    private static void searchPatientByName() {
        System.out.println("\n--- Search Patient By Name ---");

        String name = InputUtils.readNonEmptyString("Enter name (0 to cancel): ");
        if (name.equals("0")) { cancelMsg(); return; }

        List<Patient> list = patientService.getAllPatients();
        boolean found = false;

        for (Patient p : list) {
            if (p.getName().equalsIgnoreCase(name)) {
                printPatient(p);
                found = true;
            }
        }

        if (!found) System.out.println("❌ No patient found with that name.");
    }

    // ============================================================
    // UPDATE PATIENT
    // ============================================================
    private static void updatePatient() {
        System.out.println("\n--- Update Patient Details ---");

        String id = InputUtils.readNonEmptyString("Enter Patient ID (0 to cancel): ");
        if (id.equals("0")) { cancelMsg(); return; }

        Patient p = patientService.findById(id);

        if (p == null) {
            System.out.println("❌ Patient not found!");
            return;
        }

        printPatient(p);

        System.out.println("\n--- Enter New Details (0 to cancel any field) ---");

        String name = InputUtils.readNonEmptyString("Name: ");
        if (name.equals("0")) { cancelMsg(); return; }

        int age = InputUtils.readInt("Age (0 to cancel): ");
        if (age == 0) { cancelMsg(); return; }

        String gender = InputUtils.readNonEmptyString("Gender: ");
        if (gender.equals("0")) { cancelMsg(); return; }

        String phone = InputUtils.readNonEmptyString("Phone: ");
        if (phone.equals("0")) { cancelMsg(); return; }

        String disease = InputUtils.readNonEmptyString("Disease: ");
        if (disease.equals("0")) { cancelMsg(); return; }

        boolean updated = patientService.updatePatient(id, name, age, gender, phone, disease);

        if (updated) System.out.println("✅ Patient updated successfully!");
        else System.out.println("❌ Update failed!");
    }

    // ============================================================
    // PRINT METHODS
    // ============================================================
    private static void printPatient(Patient p) {
        System.out.println("\nPatient:");
        System.out.println("ID: " + p.getId());
        System.out.println("Name: " + p.getName());
        System.out.println("Age: " + p.getAge());
        System.out.println("Gender: " + p.getGender());
        System.out.println("Phone: " + p.getPhone());
        System.out.println("Disease: " + p.getDisease());
        System.out.println("----------------------------");
    }

    private static void printDoctor(Doctor d) {
        System.out.println("\nDoctor:");
        System.out.println("ID: " + d.getId());
        System.out.println("Name: " + d.getName());
        System.out.println("Specialization: " + d.getSpecialization());
        System.out.println("Available Days: " + d.getAvailableDays());
        System.out.println("Available Time: " + d.getAvailableTime());
        System.out.println("----------------------------");
    }

    private static void cancelMsg() {
        System.out.println("❌ Operation Cancelled.");
    }
}
