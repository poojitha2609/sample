package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.model.Department;
import org.example.demo.service.DepartmentService;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/listDepartments")
public class ListDepartments extends HttpServlet {
    private final DepartmentService departmentService = new DepartmentService();

    public ListDepartments() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Department> departments = null;
        try {
            departments = departmentService.listDepartments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        JSONArray jsonArray = new JSONArray(departments);
        response.getWriter().write(jsonArray.toString());
    }
}
