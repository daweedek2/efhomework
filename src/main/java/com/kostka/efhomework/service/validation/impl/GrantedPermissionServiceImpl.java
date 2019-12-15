package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.validation.GrantedPermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GrantedPermissionServiceImpl implements GrantedPermissionService {

    /**
     *
     * @param permission
     * @param user
     * @return
     */
    public boolean isPermissionWithRequiredGranted(final Permission permission, final User user) {
        final AtomicBoolean isRequiredPermissionGranted = isRequiredPermissionGranted(permission, user);
        if (isRequiredPermissionGranted.get()) {
            return true;
        } else {
            return isPermissionGrantedForUserWithGroup(permission, user);
        }
    }

    public boolean isPermissionGrantedForUserWithGroup(final Permission permission, final User user) {
        final Set<Group> groups = user.getParentGroups();
        if (isPermissionGrantedForParentGroups(permission, groups)) {
            return true;
        }
        return isPermissionGrantedInSet(permission, user.getGrantedPermissions());
    }

    public boolean isPermissionGrantedForGroup(final Permission permission, final Group group) {
        final Set<Group> parentGroupSet = group.getParentGroups();
        if (isPermissionGrantedForParentGroups(permission, parentGroupSet)) {
            return true;
        }
        return isPermissionGrantedInSet(permission, group.getGrantedPermissions());
    }

    public boolean isPermissionGrantedInSet(final Permission permission, final Set<Permission> permissionSet) {
        return permissionSet.contains(permission);
    }

    private boolean isPermissionGrantedForParentGroups(final Permission permission,
                                                              final Set<Group> parentGroups) {
        for (Group group : parentGroups) {
            if (isPermissionGrantedForGroup(permission, group)) {
                return true;
            }
        }
        return false;
    }

    private AtomicBoolean isRequiredPermissionGranted(final Permission permission, final User user) {
        final AtomicBoolean isRequiredPermissionGranted = new AtomicBoolean(false);
        permission.getRequiredPermissions().forEach(requiredPermission -> {
            if (isPermissionGrantedForUserWithGroup(requiredPermission, user)) {
                isRequiredPermissionGranted.set(true);
            }
        });
        return isRequiredPermissionGranted;
    }
}
