package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.DepartmentService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/viewDepartment")
public class ViewDepartment extends HttpServlet {
    private final DepartmentService departmentService = new DepartmentService();

    public ViewDepartment() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); // Get the ID from request parameters
        String jsonResponse = null; // Call the service to view department
        try {
            jsonResponse = departmentService.viewDepartment(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse); // Write the JSON response
    }
}
