package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.service.EmployeeService;
import org.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONArray employeesJsonArray = employeeService.getAllEmployees();

        if (employeesJsonArray.length() > 0) {
            out.print(employeesJsonArray.toString());
        } else {
            out.print("[]");
        }

        out.flush();
    }
}
