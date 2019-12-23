package com.kostka.efhomework.service.management.permission.impl;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageUserPermissionServiceImpl extends AbstractManagePermissionServiceImpl<User> {

    @Autowired
    public ManageUserPermissionServiceImpl(final UserService userService,
                                           final PermissionService permissionService) {
        super(userService, permissionService);
    }
}
