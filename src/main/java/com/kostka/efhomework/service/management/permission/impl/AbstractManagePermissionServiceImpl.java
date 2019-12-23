package com.kostka.efhomework.service.management.permission.impl;

import com.kostka.efhomework.entity.AbstractEntity;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.service.management.permission.ManagePermissionService;
import com.kostka.efhomework.service.management.register.AbstractEntityService;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class AbstractManagePermissionServiceImpl<T extends AbstractEntity> implements ManagePermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractManagePermissionServiceImpl.class);
    private AbstractEntityService<T> service;
    private PermissionService permissionService;

    public AbstractManagePermissionServiceImpl(final AbstractEntityService service,
                                               final PermissionService permissionService) {
        this.service = service;
        this.permissionService = permissionService;
    }

    @Override
    public void grantPermission(final String permissionId, final String groupName) {
        final Permission permission = permissionService.getPermission(permissionId);
        final T entity = service.get(groupName);
        final Set<Permission> permissions = entity.getGrantedPermissions();
        permissions.add(permission);
        service.save(entity);
        LOGGER.info("Permission '{}' granted for the group '{}'.", permissionId, groupName);
    }

    @Override
    public void revokePermission(final String permissionId, final String groupName) {
        final Permission permission = permissionService.getPermission(permissionId);
        final T entity = service.get(groupName);
        final Set<Permission> permissions = entity.getRevokedPermissions();
        permissions.add(permission);
        service.save(entity);
        LOGGER.info("Permission '{}' revoked for the group '{}'.", permissionId, groupName);
    }
}
