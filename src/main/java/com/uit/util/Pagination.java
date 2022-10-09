package com.uit.util;

import com.uit.entity.Department;
import com.uit.entity.Employee;

import java.util.List;

public class Pagination {
    private final int total;
    private final List<Employee> employees;
    private final List<Department> departments;

    public Pagination(int total, List<Employee> employees, List<Department> departments) {
        this.total = total;
        this.employees = employees;
        this.departments = departments;
    }

    public int getTotal() {
        return total;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

}
