package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public final class RevokedPermissionServiceImpl {
    private RevokedPermissionServiceImpl() {
    }

    public static void checkPermissionWithRequiredRevoked(final Permission permission, final User user) {
        permission.getRequiredPermissions().forEach(required -> checkPermissionRevokedForUserWithGroup(required, user));
        checkPermissionRevokedForUserWithGroup(permission, user);
    }

    public static void checkPermissionRevokedForUserWithGroup(final Permission permission, final User user) {
        user.getParentGroups().forEach(group -> checkPermissionRevokedForGroup(permission, group));
        checkPermissionRevokedInSet(permission, user.getRevokedPermissions());
    }

    public static void checkPermissionRevokedForGroup(final Permission permission, final Group group) {
        group.getParentGroups().forEach(parentGroup -> checkPermissionRevokedForGroup(permission, parentGroup));
        checkPermissionRevokedInSet(permission, group.getRevokedPermissions());
    }

    public static void checkPermissionRevokedInSet(final Permission permission, final Set<Permission> permissionSet) {
        if (permissionSet.contains(permission)) {
            throw new NoPermissionException("Permission '" + permission.getName() + "' is revoked.");
        }
    }
}
