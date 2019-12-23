package com.kostka.efhomework.service.validation;

import com.kostka.efhomework.entity.AbstractEntity;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;

import java.util.Set;

public interface RevokedPermissionService {
    void checkPermissionWithRequiredRevoked(Permission permission, User user);
    void checkPermissionRevoked(Permission permission, AbstractEntity entity);
    void checkPermissionRevokedInSet(Permission permission, Set<Permission> permissionSet);
}
