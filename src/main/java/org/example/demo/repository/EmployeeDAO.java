package org.example.demo.repository;

import org.example.demo.model.Address;
import org.example.demo.model.Department;
import org.example.demo.model.Employee;
import org.example.demo.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public boolean addEmployeeToDatabase(Employee employee) throws ClassNotFoundException, SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection =dbConnection.getConnection();
        String sql = "INSERT INTO employee (id, name, age, salary, department_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employee.getEmpId());
            statement.setString(2, employee.getName());
            statement.setInt(3, employee.getAge());
            statement.setDouble(4, employee.getSalary());
            statement.setInt(5, employee.getDepartmentId());
            System.out.println(sql);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }
    public List<Employee> fetchAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        try (Connection connection = dbConnection.getConnection()) {
            String sql = "SELECT * FROM Employee";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getDouble("salary"),
                        resultSet.getInt("department_id")
                );
                employees.add(employee);
            }
        }
        return employees;
    }
    public Employee fetchEmployeeById(int id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        try (Connection connection = dbConnection.getConnection()) {
            String sql = "SELECT * FROM Employee WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getDouble("salary"),
                        resultSet.getInt("department_id")
                );
            }
        }
        return null;
    }
    public boolean deleteEmployeeById(int employeeId) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        try (Connection connection = dbConnection.getConnection()) {
            String sqlDeleteAddresses = "DELETE FROM Employee_Address WHERE employee_id = ?";
            try (PreparedStatement stmtDeleteAddresses = connection.prepareStatement(sqlDeleteAddresses)) {
                stmtDeleteAddresses.setInt(1, employeeId);
                stmtDeleteAddresses.executeUpdate();
            }

            String sqlDeleteEmployee = "DELETE FROM Employee WHERE id = ?";
            try (PreparedStatement stmtDeleteEmployee = connection.prepareStatement(sqlDeleteEmployee)) {
                stmtDeleteEmployee.setInt(1, employeeId);
                int rowsDeleted = stmtDeleteEmployee.executeUpdate();
                return rowsDeleted > 0;
            }
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getInt("department_id")
                );
                employees.add(employee);
            }
        }
        return employees;
    }
    public List<Department> listDepartments() {
        List<Department> departmentList = new ArrayList<>();
        String sql = "SELECT * FROM Department";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setCode(resultSet.getString("code"));
                departmentList.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentList;
    }
    public List<Address> listEmployeeAddresses(int employeeId) {
        List<Address> addressList = new ArrayList<>();
        String sql = "SELECT a.* FROM Address a JOIN Employee_Address ea ON a.id = ea.address_id WHERE ea.employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setType(resultSet.getString("type"));
                address.setCity(resultSet.getString("city"));
                address.setState(resultSet.getString("state"));
                addressList.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }
}
