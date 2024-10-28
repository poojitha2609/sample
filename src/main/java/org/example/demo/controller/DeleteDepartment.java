package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.DepartmentService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteDepartment")
public class DeleteDepartment extends HttpServlet {
    private DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        try {
            departmentService = new DepartmentService();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize DepartmentService", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String responseJson = null;
        responseJson = departmentService.deleteDepartment(req);

        resp.getWriter().write(responseJson);
    }
}
