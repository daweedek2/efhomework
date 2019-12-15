package com.kostka.efhomework.service.management.assignment.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.service.management.assignment.PermissionAssignmentService;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionAssignmentServiceImpl implements PermissionAssignmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAssignmentServiceImpl.class);
    private PermissionService permissionService;

    public PermissionAssignmentServiceImpl(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void assignRequiredPermissionToPermission(final String requiredPermissionName,
                                                     final String permissionName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final Permission requiredPermission = permissionService.getPermission(requiredPermissionName);
        final Set<Permission> permissionSet = permission.getRequiredPermissions();
        permissionSet.add(requiredPermission);
        permissionService.savePermission(permission);
        LOGGER.info("Required permission '{}' assigned to the permission '{}'.",
                requiredPermissionName, permissionName);
    }

    @Override
    public void deAssignRequiredPermissionFromPermission(final String requiredPermissionName,
                                                         final String permissionName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final Permission requiredPermission = permissionService.getPermission(requiredPermissionName);
        final Set<Permission> permissionSet = permission.getRequiredPermissions();
        permissionSet.remove(requiredPermission);
        permissionService.savePermission(permission);
        LOGGER.info("Required permission '{}' de-assigned from the permission '{}'.",
                requiredPermissionName, permissionName);
    }
}
