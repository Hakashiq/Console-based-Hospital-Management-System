package services;

import db.DBConnection;
import models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentService {

    public boolean checkClash(String doctorId, String date, String time) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM appointments WHERE doctor_id=? AND date=? AND time=? AND status='ACTIVE'";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, doctorId);
            ps.setString(2, date);
            ps.setString(3, time);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("❌ Clash check failed!");
            e.printStackTrace();
            return true;
        }
    }

    public void bookAppointment(String patientId, String doctorId, String date, String time) {

        if (checkClash(doctorId, date, time)) {
            System.out.println("\n❌ Appointment clash! Choose different time.");
            return;
        }

        String id = UUID.randomUUID().toString().substring(0, 8);

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO appointments (appointment_id, patient_id, doctor_id, date, time, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, patientId);
            ps.setString(3, doctorId);
            ps.setString(4, date);
            ps.setString(5, time);

            ps.executeUpdate();
            System.out.println("\n✅ Appointment Booked! ID: " + id);

        } catch (Exception e) {
            System.out.println("❌ Failed to book appointment!");
            e.printStackTrace();
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getString("appointment_id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to load appointments!");
            e.printStackTrace();
        }

        return list;
    }

    public void cancelAppointment(String appointmentId) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE appointments SET status='CANCELLED' WHERE appointment_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, appointmentId);

            ps.executeUpdate();
            System.out.println("\n⚠ Appointment Cancelled.");

        } catch (Exception e) {
            System.out.println("❌ Failed to cancel appointment!");
            e.printStackTrace();
        }
    }
}
