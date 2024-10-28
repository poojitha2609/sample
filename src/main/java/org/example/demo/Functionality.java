package org.example.demo;
import org.example.demo.model.Department;
import org.example.demo.model.Employee;

import java.sql.*;
import java.util.*;

public class Functionality {
    private Connection conn;

    public Functionality() {
        this.conn = CrudOps.getConnection();
    }

    public boolean addEmployee(Employee employee) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Employee Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Employee Age: ");
        int age = scanner.nextInt();

        System.out.println("Enter Employee Salary: ");
        double salary = scanner.nextDouble();

        System.out.println("Select Department (Enter Department ID): ");
        int deptId = scanner.nextInt();

        if (getDepartmentById(deptId) == null) {
            System.out.println("Invalid department selected!");
            return false;
        }

        try {
            String sql = "INSERT INTO Employee (id, name, age, salary, department_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setDouble(4, salary);
            stmt.setInt(5, deptId);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee added successfully.");
            }

            System.out.println("Enter number of addresses: ");
            int addressCount = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < addressCount; i++) {
                addAddress(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addAddress(int employeeId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Address Id: ");
        int addressId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Address Type: ");
        String type = scanner.nextLine();

        System.out.println("Enter Door No: ");
        String door_No = scanner.nextLine();

        System.out.println("Enter Street: ");
        String street = scanner.nextLine();

        System.out.println("Enter Town: ");
        String town = scanner.nextLine();

        System.out.println("Enter City: ");
        String city = scanner.nextLine();

        System.out.println("Enter State: ");
        String state = scanner.nextLine();

        System.out.println("Enter Country: ");
        String country = scanner.nextLine();

        System.out.println("Enter Pincode: ");
        String pincode = scanner.nextLine();

        try {
            String sqlAddress = "INSERT INTO Address (id, type, door_No, street, town, city, state, country, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtAddress = conn.prepareStatement(sqlAddress);
            stmtAddress.setInt(1, addressId);
            stmtAddress.setString(2, type);
            stmtAddress.setString(3, door_No);
            stmtAddress.setString(4, street);
            stmtAddress.setString(5, town);
            stmtAddress.setString(6, city);
            stmtAddress.setString(7, state);
            stmtAddress.setString(8, country);
            stmtAddress.setString(9, pincode);

            stmtAddress.executeUpdate();

            String sqlEmpAddress = "INSERT INTO Employee_Address (employee_id, address_id) VALUES (?, ?)";
            PreparedStatement stmtEmpAddress = conn.prepareStatement(sqlEmpAddress);
            stmtEmpAddress.setInt(1, employeeId);
            stmtEmpAddress.setInt(2, addressId);

            stmtEmpAddress.executeUpdate();

            System.out.println("Address added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee ID to view: ");
        int id = scanner.nextInt();

        try {
            String sql = "SELECT * FROM Employee";
            PreparedStatement stmt = conn.prepareStatement(sql);
            //   stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Employee ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Salary: " + resultSet.getDouble("salary"));
                System.out.println("Department ID: " + resultSet.getInt("department_id"));

                listEmployeeAddresses(id);
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listEmployeeAddresses(int employeeId) {
        try {
            String sql = "SELECT a.* FROM Address a JOIN Employee_Address ea ON a.id = ea.address_id WHERE ea.employee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            ResultSet resultSet = stmt.executeQuery();

            System.out.println("Addresses:");
            while (resultSet.next()) {
                System.out.println("Address ID: " + resultSet.getInt("id"));
                System.out.println("Type: " + resultSet.getString("type"));
                System.out.println("City: " + resultSet.getString("city"));
                System.out.println("State: " + resultSet.getString("state"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee ID to delete: ");
        int id = scanner.nextInt();

        try {
            String sqlDeleteAddresses = "DELETE FROM Employee_Address WHERE employee_id = ?";
            PreparedStatement stmtDeleteAddresses = conn.prepareStatement(sqlDeleteAddresses);
            stmtDeleteAddresses.setInt(1, id);
            stmtDeleteAddresses.executeUpdate();

            String sql = "DELETE FROM Employee WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listEmployees() {
        try {
            String sql = "SELECT * FROM Employee";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                System.out.println("Employee ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Salary: " + resultSet.getDouble("salary"));
                System.out.println("Department ID: " + resultSet.getInt("department_id"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee Name to search: ");
        String name = scanner.nextLine();

        try {
            String sql = "SELECT * FROM Employee WHERE name ILIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet resultSet = stmt.executeQuery();

            boolean found = false;
            while (resultSet.next()) {
                System.out.println("Employee ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Salary: " + resultSet.getDouble("salary"));
                System.out.println("Department ID: " + resultSet.getInt("department_id"));
                System.out.println();
                found = true;
            }

            if (!found) {
                System.out.println("No employee found with name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> sortEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (Connection conn = CrudOps.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Columns in the ResultSet:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metaData.getColumnName(i));
            }

            if (!rs.isBeforeFirst()) {
                System.out.println("No data found in the employee table.");
                return employees;
            }

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getInt("department_id")
                );
                employees.add(employee);
                System.out.println("Added Employee: " + employee);
            }

            employees.sort(Comparator.comparingInt(Employee::getEmpId));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }



    public void groupEmployeesByDepartment() {
        try {
            String sql = "SELECT d.name as department_name, COUNT(e.id) as employee_count " +
                    "FROM Employee e " +
                    "JOIN Department d ON e.department_id = d.id " +
                    "GROUP BY d.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                System.out.println("Department: " + resultSet.getString("department_name"));
                System.out.println("Employee Count: " + resultSet.getInt("employee_count"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Department ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Department Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Department Code: ");
        String code = scanner.nextLine();

        try {
            String sql = "INSERT INTO Department (id, name, code) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, code);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Department added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Department ID to view: ");
        int id = scanner.nextInt();

        try {
            String sql = "SELECT * FROM Department WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Department ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Code: " + resultSet.getString("code"));
            } else {
                System.out.println("Department with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Department ID to delete: ");
        int id = scanner.nextInt();

        try {
            String sql = "DELETE FROM Department WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Department with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Department> listDepartments() {
        try {
            String sql = "SELECT * FROM Department";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                System.out.println("Department ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Code: " + resultSet.getString("code"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDepartment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Department ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Department Name: ");
        String newName = scanner.nextLine();

        System.out.println("Enter new Department Code: ");
        String newCode = scanner.nextLine();

        try {
            String sql = "UPDATE Department SET name = ?, code = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newName);
            stmt.setString(2, newCode);
            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("Department with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Department getDepartmentById(int id) {
        try {
            String sql = "SELECT * FROM Department WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new Department(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        final String URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USER = "postgres";
        final String PASSWORD = "pooja";

        String selectSQL = "SELECT id, name, age, salary, department_id FROM employee";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmpId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartmentId(resultSet.getInt("department_id"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


}

