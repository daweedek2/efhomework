package com.kostka.efhomework.service.validation;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;

import java.util.Set;

public interface GrantedPermissionService {
    boolean isPermissionWithRequiredGranted(Permission permission, User user);
    boolean isPermissionGrantedForUserWithGroup(Permission permission, User user);
    boolean isPermissionGrantedForGroup(Permission permission, Group group);
    boolean isPermissionGrantedInSet(Permission permission, Set<Permission> permissionSet);
}
