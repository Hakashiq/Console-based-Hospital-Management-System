package services;

import db.DBConnection;
import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientService {

    public void addPatient(String name, int age, String gender, String phone, String disease) {
        String id = "P" + (getAllPatients().size() + 1);

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO patients (id, name, age, gender, phone, disease) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setString(5, phone);
            ps.setString(6, disease);

            ps.executeUpdate();
            System.out.println("\n✅ Patient Registered Successfully! ID: " + id);

        } catch (Exception e) {
            System.out.println("❌ Failed to add patient!");
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM patients";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("disease")
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch patients!");
            e.printStackTrace();
        }

        return patients;
    }

    public Patient findById(String id) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM patients WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to find patient!");
            e.printStackTrace();
        }

        return null;
    }

    public boolean updatePatient(String id, String name, int age, String gender, String phone, String disease) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE patients SET name=?, age=?, gender=?, phone=?, disease=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setString(5, disease);
            ps.setString(6, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("❌ Failed to update patient!");
            e.printStackTrace();
            return false;
        }
    }
}
