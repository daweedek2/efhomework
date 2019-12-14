package com.kostka.efhomework.service.management.assignment.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.service.management.assignment.RequiredPermissionAssignmentService;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RequiredPermissionAssignmentServiceImpl implements RequiredPermissionAssignmentService {
    private PermissionService permissionService;

    public RequiredPermissionAssignmentServiceImpl(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void assignRequiredPermissionToPermission(final String requiredPermissionName,
                                                     final String permissionName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final Permission requiredPermission = permissionService.getPermission(requiredPermissionName);
        final Set<Permission> permissionSet = permission.getRequiredPermissions();
        permissionSet.add(requiredPermission);
    }

    @Override
    public void deAssignRequiredPermissionFromPermission(final String requiredPermissionName,
                                                         final String permissionName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final Permission requiredPermission = permissionService.getPermission(requiredPermissionName);
        final Set<Permission> permissionSet = permission.getRequiredPermissions();
        permissionSet.remove(requiredPermission);
    }
}
