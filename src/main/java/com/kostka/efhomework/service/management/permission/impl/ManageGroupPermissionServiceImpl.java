package com.kostka.efhomework.service.management.permission.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageGroupPermissionServiceImpl extends AbstractManagePermissionServiceImpl<Group> {
    @Autowired
    public ManageGroupPermissionServiceImpl(final GroupService groupService,
                                            final PermissionService permissionService) {
        super(groupService, permissionService);
    }
}
