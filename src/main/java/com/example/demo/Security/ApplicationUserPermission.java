package com.example.demo.Security;

public enum ApplicationUserPermission {
    Student_READ("student:read"),
    Student_WRITE("student:write"),
    Course_READ("course:read"),
    Course_WRITE("course:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
