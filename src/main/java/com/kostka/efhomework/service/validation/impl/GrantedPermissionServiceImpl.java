package com.kostka.efhomework.service.validation.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.validation.GrantedPermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GrantedPermissionServiceImpl implements GrantedPermissionService {

    /**
     * Method that checks granted permissions (including the required permissions) for user.
     * @param permission Checked permission.
     * @param user Checked user.
     * @return true if permission (including required permission) is granted for the user, otherwise false.
     */
    @Override
    public boolean isPermissionWithRequiredGranted(final Permission permission, final User user) {
        if (isRequiredPermissionGranted(permission, user)) {
            return true;
        } else {
            return isPermissionGrantedForUserWithGroup(permission, user);
        }
    }

    /**
     * Method that checks granted permission for the user and his parent groups.
     * @param permission Checked permission.
     * @param user Checked user.
     * @return true if permission is granted for user or its group, otherwise false.
     */
    @Override
    public boolean isPermissionGrantedForUserWithGroup(final Permission permission, final User user) {
        final Set<Group> groups = user.getParentGroups();
        if (isPermissionGrantedForParentGroups(permission, groups)) {
            return true;
        }
        return isPermissionGrantedInSet(permission, user.getGrantedPermissions());
    }

    /**
     * Method that checks granted permission for the group and his parent group.
     * @param permission Checked permission.
     * @param group Checked group.
     * @return true if permission is granted for group or parent group.
     */
    @Override
    public boolean isPermissionGrantedForGroup(final Permission permission, final Group group) {
        final Set<Group> parentGroupSet = group.getParentGroups();
        if (isPermissionGrantedForParentGroups(permission, parentGroupSet)) {
            return true;
        }
        return isPermissionGrantedInSet(permission, group.getGrantedPermissions());
    }

    /**
     * Method that checks if permission is present in Set of permissions.
     * @param permission Checked permission.
     * @param permissionSet Checked Set of permissions.
     * @return true if the permission is present in Set of permissions.
     */
    @Override
    public boolean isPermissionGrantedInSet(final Permission permission, final Set<Permission> permissionSet) {
        return permissionSet.contains(permission);
    }

    /**
     * Method that checks if the permission is granted for the Set of Groups.
     * @param permission Checked permission.
     * @param parentGroups Checked Set of parent groups.
     * @return
     */
    private boolean isPermissionGrantedForParentGroups(final Permission permission, final Set<Group> parentGroups) {
        for (Group group : parentGroups) {
            if (isPermissionGrantedForGroup(permission, group)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that checks if the required permission is granted for the user.
     * @param permission Checked required permission.
     * @param user Checked user.
     * @return true if the required permission is granted for the user.
     */
    private boolean isRequiredPermissionGranted(final Permission permission, final User user) {
        for (Permission requiredPermission : permission.getRequiredPermissions()) {
            if (isPermissionGrantedForUserWithGroup(requiredPermission, user)) {
                return true;
            }
        }
        return false;
    }
}
