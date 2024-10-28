package org.example.demo.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.model.Address;
import org.example.demo.service.EmployeeService;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/listEmployeeAddresses")
public class ListEmployeeAddresses extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId")); // Retrieve employeeId from request parameters
        List<Address> addresses = employeeService.listEmployeeAddresses(employeeId);

        response.setContentType("application/json");
        JSONArray jsonArray = new JSONArray(addresses);
        response.getWriter().write(jsonArray.toString());
    }
}

