package com.kostka.efhomework.service.management.assignment;

public interface PermissionAssignmentService {
    void assignRequiredPermissionToPermission(String requiredPermissionName, String permissionName);
    void deAssignRequiredPermissionFromPermission(String requiredPermissionName, String permissionName);
}
