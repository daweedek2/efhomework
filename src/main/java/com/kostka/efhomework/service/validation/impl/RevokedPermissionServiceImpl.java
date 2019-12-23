package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.AbstractEntity;
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
        permission.getRequiredPermissions().forEach(required -> checkPermissionRevoked(required, user));
        checkPermissionRevoked(permission, user);
    }

    /**
     * Method that checks if permission is revoked for the user and his parent groups (excluding required permission).
     * If so, NoPermissionException is thrown.
     * @param permission Checked permission.
     * @param entity Checked entity.
     */
    @Override
    public void checkPermissionRevoked(final Permission permission, final AbstractEntity entity) {
        entity.getParentGroups().forEach(group -> checkPermissionRevoked(permission, group));
        checkPermissionRevokedInSet(permission, entity.getRevokedPermissions());
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
