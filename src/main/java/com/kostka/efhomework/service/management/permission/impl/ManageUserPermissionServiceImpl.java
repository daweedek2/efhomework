package com.kostka.efhomework.service.management.permission.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.management.permission.ManageUserPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ManageUserPermissionServiceImpl implements ManageUserPermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUserPermissionServiceImpl.class);
    private UserService userService;
    private PermissionService permissionService;

    @Autowired
    public ManageUserPermissionServiceImpl(final UserService userService,
                                           final PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public void grantPermissionToUser(final String permission, final String userName) {
        final Permission permission1 = permissionService.getPermission(permission);
        final User user = userService.getUser(userName);
        final Set<Permission> permissions = user.getGrantedPermissions();
        permissions.add(permission1);
        userService.saveUser(user);
        LOGGER.info("Permission '{}' granted for the user '{}'.", permission, userName);
    }

    @Override
    public void revokePermissionToUser(final String permission, final String userName) {
        final Permission permission1 = permissionService.getPermission(permission);
        final User user = userService.getUser(userName);
        final Set<Permission> permissions = user.getRevokedPermissions();
        permissions.add(permission1);
        userService.saveUser(user);
        LOGGER.info("Permission '{}' revoked for the user '{}'.", permission, userName);
    }
}
