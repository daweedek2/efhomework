package com.kostka.efhomework.service.entity;

import com.kostka.efhomework.entity.Permission;

public interface PermissionService {
    Permission createPermission(String name);
    Permission getPermission(String name);
    void deletePermission(String name);
}
