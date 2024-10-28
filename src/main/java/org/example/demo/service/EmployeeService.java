package org.example.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.demo.model.Address;
import org.example.demo.model.Employee;
import org.example.demo.repository.EmployeeDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class EmployeeService {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    public boolean addEmployee(HttpServletRequest request) {
        String line;
        StringBuilder jsonInput = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(jsonInput.toString());

        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        double salary = jsonObject.getDouble("salary");
        int departmentId = jsonObject.getInt("departmentId");
        Employee employee = new Employee();
        employee.setEmpId(id);
        employee.setName(name);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setDepartmentId(departmentId);
        try {
            return employeeDAO.addEmployeeToDatabase(employee);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONArray getAllEmployees() {
        JSONArray jsonArray = new JSONArray();
        try {
            List<Employee> employees = employeeDAO.fetchAllEmployees();
            for (Employee employee : employees) {
                JSONObject employeeJson = new JSONObject();
                employeeJson.put("id", employee.getEmpId());
                employeeJson.put("name", employee.getName());
                employeeJson.put("age", employee.getAge());
                employeeJson.put("salary", employee.getSalary());
                employeeJson.put("department_id", employee.getDepartmentId());
                jsonArray.put(employeeJson);
            }
        } catch (SQLException e) {
        }
        return jsonArray;
    }



    public JSONObject getEmployeeById(HttpServletRequest request) throws SQLException, IOException {
        StringBuilder jsonInput = new StringBuilder();
        request.getReader().lines().forEach(line -> jsonInput.append(line));

        JSONObject jsonObject = new JSONObject(jsonInput.toString());
        int id = jsonObject.getInt("id");

        Employee employee = employeeDAO.fetchEmployeeById(id);

        if (employee != null) {
            JSONObject employeeJson = new JSONObject();
            employeeJson.put("id", employee.getEmpId());
            employeeJson.put("name", employee.getName());
            employeeJson.put("age", employee.getAge());
            employeeJson.put("salary", employee.getSalary());
            employeeJson.put("department_id", employee.getDepartmentId());
            return employeeJson;
        } else {
            return new JSONObject().put("message", "Employee not found");
        }
    }
    public JSONObject deleteEmployee(String jsonInput) {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject jsonRequest = new JSONObject(jsonInput);
            int employeeId = jsonRequest.getInt("id");

            boolean isDeleted = employeeDAO.deleteEmployeeById(employeeId);

            if (isDeleted) {
                responseJson.put("status", "success");
                responseJson.put("message", "Employee deleted successfully.");
            } else {
                responseJson.put("status", "failure");
                responseJson.put("message", "Employee not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.put("status", "error");
            responseJson.put("message", "Invalid input format.");
        }

        return responseJson;
    }
    public JSONArray searchEmployeeByName(String name) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE name ILIKE ?";
        Connection connection = null;
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "%" + name + "%");
        ResultSet resultSet = stmt.executeQuery();

        JSONArray employeesArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject employeeJson = new JSONObject();
            employeeJson.put("id", resultSet.getInt("id"));
            employeeJson.put("name", resultSet.getString("name"));
            employeeJson.put("age", resultSet.getInt("age"));
            employeeJson.put("salary", resultSet.getDouble("salary"));
            employeeJson.put("department_id", resultSet.getInt("department_id"));
            employeesArray.put(employeeJson);
        }

        resultSet.close();
        stmt.close();
        return employeesArray;
    }
    public JSONArray getSortedEmployees() {
        List<Employee> employees;
        try {
            employees = employeeDAO.getAllEmployees();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employees from database", e);
        }

        employees.sort(Comparator.comparingInt(Employee::getEmpId));

        JSONArray employeeArray = new JSONArray();
        for (Employee employee : employees) {
            JSONObject employeeJson = new JSONObject();
            employeeJson.put("id", employee.getEmpId());
            employeeJson.put("name", employee.getName());
            employeeJson.put("age", employee.getAge());
            employeeJson.put("salary", employee.getSalary());
            employeeJson.put("department_id", employee.getDepartmentId());

            employeeArray.put(employeeJson);
        }

        return employeeArray;
    }
    public List<Address> listEmployeeAddresses(int employeeId) {
        return employeeDAO.listEmployeeAddresses(employeeId);
    }
}
