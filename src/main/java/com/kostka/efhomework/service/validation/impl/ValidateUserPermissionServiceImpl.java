package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.GrantedPermissionService;
import com.kostka.efhomework.service.validation.RevokedPermissionService;
import com.kostka.efhomework.service.validation.ValidateUserPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateUserPermissionServiceImpl implements ValidateUserPermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateUserPermissionServiceImpl.class);
    private UserService userService;
    private PermissionService permissionService;
    private GrantedPermissionService grantedPermissionService;
    private RevokedPermissionService revokedPermissionService;

    @Autowired
    public ValidateUserPermissionServiceImpl(final UserService userService,
                                             final PermissionService permissionService,
                                             final GrantedPermissionService grantedPermissionService,
                                             final RevokedPermissionService revokedPermissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.grantedPermissionService = grantedPermissionService;
        this.revokedPermissionService = revokedPermissionService;
    }

    /**
     * Method that checks the permission of the user.
     * If the permission is granted, log message is written.
     * If the permission is not granted or revoked, NoPermissionException is thrown.
     * @param permissionName String of the permission name.
     * @param userName String of the user name.
     */
    @Override
    public void checkUserPermissions(final String permissionName, final String userName) {
        final Permission permission = permissionService.getPermission(permissionName);
        final User user = userService.get(userName);
        revokedPermissionService.checkPermissionWithRequiredRevoked(permission, user);
        if (grantedPermissionService.isPermissionWithRequiredGranted(permission, user)) {
            LOGGER.info("SUCCESS. Permission '{}' is granted for user '{}'.", permission.getName(), user.getName());
        } else {
            throw new NoPermissionException("Permission '" + permission.getName() + "' is not granted.");
        }
    }
}
