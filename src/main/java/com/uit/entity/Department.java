package com.uit.entity;

import java.time.LocalDate;

public class Department {

    private String departMentId;

    private String departmentName;

    private String departmentLeader;


    private LocalDate departmentCreatedDate;

    public String getDepartMentId() {
        return departMentId;
    }

    public void setDepartMentId(String departMentId) {
        this.departMentId = departMentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentLeader() {
        return departmentLeader;
    }

    public void setDepartmentLeader(String departmentLeader) {
        this.departmentLeader = departmentLeader;
    }

    public LocalDate getDepartmentCreatedDate() {
        return departmentCreatedDate;
    }

    public void setDepartmentCreatedDate(LocalDate departmentCreatedDate) {
        this.departmentCreatedDate = departmentCreatedDate;
    }


}
