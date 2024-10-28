package org.example.demo.repository;

import org.example.demo.model.Department;
import org.example.demo.util.DBConnection;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private Connection connection;

    public DepartmentDao() throws SQLException {
        this.connection = DBConnection.getConnection();

    }
    public boolean addDepartment(Department department) {
        String sql = "INSERT INTO department (id, name, code) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, department.getId());
            statement.setString(2, department.getName());
            statement.setString(3, department.getCode());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDepartmentById(int id) {
        JSONObject departmentJson = new JSONObject();

        String sql = "SELECT * FROM Department WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                departmentJson.put("id", resultSet.getInt("id"));
                departmentJson.put("name", resultSet.getString("name"));
                departmentJson.put("code", resultSet.getString("code"));
                return departmentJson.toString(); // Return department details as JSON string
            } else {
                departmentJson.put("status", "error");
                departmentJson.put("message", "Department with ID " + id + " not found.");
                return departmentJson.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
            departmentJson.put("status", "error");
            departmentJson.put("message", "Database error: " + e.getMessage());
            return departmentJson.toString();
        }
    }
    public boolean deleteDepartment(int departmentId) throws SQLException {
        String sql = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departmentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public List<Department> listDepartments() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        List<Department> departments = new ArrayList<>();
            String sql = "SELECT * FROM Department";
            Connection conn = dbConnection.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Department department = new Department(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("code")
                    );
                    departments.add(department);
                }
            }
            return departments;

    }

    public boolean updateDepartment(int id, String name, String code) {
        String sql = "UPDATE Department SET name = ?, code = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, code);
            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
}
