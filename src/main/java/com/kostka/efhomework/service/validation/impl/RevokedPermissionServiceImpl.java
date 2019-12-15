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

    public void checkPermissionWithRequiredRevoked(final Permission permission, final User user) {
        permission.getRequiredPermissions().forEach(required -> checkPermissionRevokedForUserWithGroup(required, user));
        checkPermissionRevokedForUserWithGroup(permission, user);
    }

    public void checkPermissionRevokedForUserWithGroup(final Permission permission, final User user) {
        user.getParentGroups().forEach(group -> checkPermissionRevokedForGroup(permission, group));
        checkPermissionRevokedInSet(permission, user.getRevokedPermissions());
    }

    public void checkPermissionRevokedForGroup(final Permission permission, final Group group) {
        group.getParentGroups().forEach(parentGroup -> checkPermissionRevokedForGroup(permission, parentGroup));
        checkPermissionRevokedInSet(permission, group.getRevokedPermissions());
    }

    public void checkPermissionRevokedInSet(final Permission permission, final Set<Permission> permissionSet) {
        if (permissionSet.contains(permission)) {
            throw new NoPermissionException("Permission '" + permission.getName() + "' is revoked.");
        }
    }
}
