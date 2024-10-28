package org.example.demo.model;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private int departmentId;
    private List<Address> addresses;
    private int age;
    private int salary;



    public Employee() {}

    public Employee(int id, String name, int age, double salary, int departmentId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = (int)salary;
        this.departmentId = departmentId;
    }


    public int getEmpId() {
        return id;
    }

    public void setEmpId(int empId) {
        this.id = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + id +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", addresses=" + addresses +
                '}';
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = (int) salary;
    }


    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }



}

