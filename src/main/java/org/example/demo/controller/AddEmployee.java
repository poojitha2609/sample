package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.EmployeeService;

import java.io.IOException;

@WebServlet("/addEmployee")
public class AddEmployee extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        EmployeeService employeeService = new EmployeeService();
        boolean isEmployeeAdded = employeeService.addEmployee(req);
        if (isEmployeeAdded) {
            message = "Employee added successfully!";
        } else {
            message = "Failed to add employee.";
        }

        resp.getWriter().write(message);
    }

}
