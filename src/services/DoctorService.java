package services;

import db.DBConnection;
import models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DoctorService {

    public void addDoctor(String name, String specialization, String days, String time) {
        String id = "D" + (getAllDoctors().size() + 1);

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO doctors (id, name, specialization, available_days, available_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, specialization);
            ps.setString(4, days);
            ps.setString(5, time);

            ps.executeUpdate();
            System.out.println("\n✅ Doctor Added Successfully! ID: " + id);

        } catch (Exception e) {
            System.out.println("❌ Failed to add doctor!");
            e.printStackTrace();
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM doctors";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("available_days"),
                        rs.getString("available_time")
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch doctors!");
            e.printStackTrace();
        }

        return list;
    }

    public Doctor findById(String id) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM doctors WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("available_days"),
                        rs.getString("available_time")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch doctor!");
            e.printStackTrace();
        }

        return null;
    }
}
