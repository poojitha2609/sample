package org.example.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.demo.model.Department;
import org.example.demo.repository.DepartmentDao;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService {
    DepartmentDao departmentDAO = new DepartmentDao();

    public DepartmentService() throws SQLException {
    }

    public JSONObject addDepartment(String jsonString) {
        // Parse JSON and create Department object
        JSONObject jsonObject = new JSONObject(jsonString);
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String code = jsonObject.getString("code");

        Department department = new Department(id, name, code);

        boolean isAdded = departmentDAO.addDepartment(department);


        JSONObject responseJson = new JSONObject();
        if (isAdded) {
            responseJson.put("status", "success");
            responseJson.put("message", "Department added successfully.");
        } else {
            responseJson.put("status", "error");
            responseJson.put("message", "Failed to add department.");
        }

        return responseJson;
    }
    public String viewDepartment(int id) throws SQLException {
        DepartmentDao departmentDao=new DepartmentDao();
        return departmentDao.getDepartmentById(id);
    }
    public String deleteDepartment(HttpServletRequest req) {
        JSONObject responseJson = new JSONObject();
        try {
            int departmentId = Integer.parseInt(req.getParameter("id"));

            boolean isDeleted = departmentDAO.deleteDepartment(departmentId);

            if (isDeleted) {
                responseJson.put("status", "success");
                responseJson.put("message", "Department deleted successfully.");
            } else {
                responseJson.put("status", "error");
                responseJson.put("message", "Department not found.");
            }
        } catch (Exception e) {
            responseJson.put("status", "error");
            responseJson.put("message", "Invalid input or deletion error.");
            e.printStackTrace();
        }

        return responseJson.toString();
    }
    public String getDepartmentsAsJson() {
        try {
            List<Department> departments = departmentDAO.listDepartments();
            JSONArray jsonArray = new JSONArray();

            if (departments != null) {
                for (Department department : departments) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", department.getId());
                    jsonObject.put("name", department.getName());
                    jsonObject.put("code", department.getCode());
                    jsonArray.put(jsonObject);
                }
            } else {
                jsonArray.put(new JSONObject().put("error", "Failed to list departments"));
            }

            return jsonArray.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONArray().put(new JSONObject().put("error", "Database error occurred")).toString();
        }
    }
    public JSONObject updateDepartment(JSONObject jsonObject) {
        JSONObject responseJson = new JSONObject();
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String code = jsonObject.getString("code");

        boolean isUpdated = departmentDAO.updateDepartment(id, name, code);

        if (isUpdated) {
            responseJson.put("status", "success");
            responseJson.put("message", "Department updated successfully.");
        } else {
            responseJson.put("status", "error");
            responseJson.put("message", "Department with ID " + id + " not found.");
        }
        return responseJson;
    }
    public List<Department> listDepartments() throws SQLException {
        return departmentDAO.listDepartments();
    }

}
