package com.example.demo.Security;

import com.google.common.collect.Sets;

import java.util.Set;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.Course_READ,
            ApplicationUserPermission.Course_WRITE,
            ApplicationUserPermission.Student_READ,
            ApplicationUserPermission.Student_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(
            ApplicationUserPermission.Course_READ,
            ApplicationUserPermission.Student_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

}
