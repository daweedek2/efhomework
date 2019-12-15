package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.validation.RevokedPermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RevokedPermissionServiceImpl implements RevokedPermissionService {

    /**
     * Method that checks if the permission is revoked for the user and
     * his parent groups (including required permissions). If so, NoPermissionException is thrown.
     * @param permission Checked permission.
     * @param user Checked user.
     */
    @Override
    public void checkPermissionWithRequiredRevoked(final Permission permission, final User user) {
        permission.getRequiredPermissions().forEach(required -> checkPermissionRevokedForUserWithGroup(required, user));
        checkPermissionRevokedForUserWithGroup(permission, user);
    }

    /**
     * Method that checks if permission is revoked for the user and his parent groups (excluding required permission).
     * If so, NoPermissionException is thrown.
     * @param permission Checked permission.
     * @param user Checked user.
     */
    @Override
    public void checkPermissionRevokedForUserWithGroup(final Permission permission, final User user) {
        user.getParentGroups().forEach(group -> checkPermissionRevokedForGroup(permission, group));
        checkPermissionRevokedInSet(permission, user.getRevokedPermissions());
    }

    /**
     * Method that checks if permission is revoked for the group and its parent group (excluding required permission).
     * If so, NoPermissionException is thrown.
     * @param permission Checked permission.
     * @param group Checked group.
     */
    @Override
    public void checkPermissionRevokedForGroup(final Permission permission, final Group group) {
        group.getParentGroups().forEach(parentGroup -> checkPermissionRevokedForGroup(permission, parentGroup));
        checkPermissionRevokedInSet(permission, group.getRevokedPermissions());
    }

    /**
     * Method that checks if permission is present in the Set of permissions. If so, NoPermissionException is thrown.
     * @param permission Checked permission.
     * @param permissionSet Checked Set of permissions.
     */
    @Override
    public void checkPermissionRevokedInSet(final Permission permission, final Set<Permission> permissionSet) {
        if (permissionSet.contains(permission)) {
            throw new NoPermissionException("Permission '" + permission.getName() + "' is revoked.");
        }
    }
}
