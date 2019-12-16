package com.kostka.efhomework.service.management.permission.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.permission.ManageGroupPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ManageGroupPermissionServiceImpl implements ManageGroupPermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageGroupPermissionServiceImpl.class);
    private GroupService groupService;
    private PermissionService permissionService;

    @Autowired
    public ManageGroupPermissionServiceImpl(final GroupService groupService,
                                            final PermissionService permissionService) {
        this.groupService = groupService;
        this.permissionService = permissionService;
    }

    @Override
    public void grantPermissionToGroup(final String permission, final String groupName) {
        final Permission permission1 = permissionService.getPermission(permission);
        final Group group = groupService.getGroup(groupName);
        final Set<Permission> permissions = group.getGrantedPermissions();
        permissions.add(permission1);
        groupService.saveGroup(group);
        LOGGER.info("Permission '{}' granted for the group '{}'.", permission, groupName);
    }

    @Override
    public void revokePermissionFromGroup(final String permission, final String groupName) {
        final Permission permission1 = permissionService.getPermission(permission);
        final Group group = groupService.getGroup(groupName);
        final Set<Permission> permissions = group.getRevokedPermissions();
        permissions.add(permission1);
        groupService.saveGroup(group);
        LOGGER.info("Permission '{}' revoked for the group '{}'.", permission, groupName);
    }
}
