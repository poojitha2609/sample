package org.example.demo.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.DepartmentService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateDepartment")
public class UpdateDepartment extends HttpServlet {
    private final DepartmentService departmentService = new DepartmentService();

    public UpdateDepartment() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        StringBuilder jsonBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(jsonBody.toString());
        jsonResponse = departmentService.updateDepartment(jsonObject);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}

