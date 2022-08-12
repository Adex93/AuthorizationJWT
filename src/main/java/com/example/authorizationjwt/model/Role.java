package com.example.authorizationjwt.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    STUDENT(Set.of(Permission.STUDENT_ROOT)),
    TEACHER(Set.of(Permission.TEACHER_ROOT)),
    ADMIN(Set.of(Permission.ADMIN_ROOT, Permission.TEACHER_ROOT, Permission.STUDENT_ROOT));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
