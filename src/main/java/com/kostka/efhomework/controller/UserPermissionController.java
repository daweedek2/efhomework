package com.kostka.efhomework.controller;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.management.permission.impl.AbstractManagePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/permission")
public class UserPermissionController extends AbstractPermissionController<User>{

    @Autowired
    public UserPermissionController(final AbstractManagePermissionServiceImpl<User> manageUserPermissionService) {
        super(manageUserPermissionService);
    }
}
