package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.ValidateUserPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.kostka.efhomework.service.validation.impl
        .GrantedPermissionServiceImpl.isPermissionWithRequiredGranted;
import static com.kostka.efhomework.service.validation.impl
        .RevokedPermissionServiceImpl.checkPermissionWithRequiredRevoked;

@Service
public class ValidateUserPermissionServiceImpl implements ValidateUserPermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateUserPermissionServiceImpl.class);
    private UserService userService;
    private PermissionService permissionService;




    public ValidateUserPermissionServiceImpl(final UserService userService,
                                             final PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public void checkUserPermissions(final String permissionName, final String userName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final User user = userService.getUser(userName);
        checkPermissionWithRequiredRevoked(permission, user);
        if (isPermissionWithRequiredGranted(permission, user)) {
            LOGGER.info("SUCCESS. Permission '{}' is granted for user '{}'.", permission.getName(), user.getName());
        } else {
            throw new NoPermissionException("Permission '" + permission.getName() + "' is not granted.");
        }
    }
}
