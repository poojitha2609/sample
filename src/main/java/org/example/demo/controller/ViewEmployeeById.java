package org.example.demo.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.EmployeeService;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/viewEmployee")
public class ViewEmployeeById extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            JSONObject employeeJson = employeeService.getEmployeeById(request);
            out.write(employeeJson.toString());
        } catch (Exception e) {
            out.write("{\"message\": \"Error retrieving employee data.\"}");
        } finally {
            out.flush();
        }
    }
}

