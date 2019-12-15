package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.Permission;

public interface PermissionService {
    Permission createPermission(String name);
    Permission getPermission(String name);
    Permission savePermission(Permission permission);
    void deletePermission(String name);
    boolean isPermissionInDb(String name);
}
