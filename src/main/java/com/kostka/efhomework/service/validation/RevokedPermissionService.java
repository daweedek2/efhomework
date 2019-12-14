package com.kostka.efhomework.service.validation;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;

import java.util.Set;

public interface RevokedPermissionService {
    void checkPermissionWithRequiredRevoked(Permission permission, User user);
    void checkPermissionRevokedForUserWithGroup(Permission permission, User user);
    void checkPermissionRevokedForGroup(Permission permission, Group group);
    void checkPermissionRevokedInSet(Permission permission, Set<Permission> permissionSet);
}
