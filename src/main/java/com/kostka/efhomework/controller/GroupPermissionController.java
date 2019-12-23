package com.kostka.efhomework.controller;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.service.management.permission.impl.AbstractManagePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("group/permission")
public class GroupPermissionController extends AbstractPermissionController<Group>{

    @Autowired
    public GroupPermissionController(final AbstractManagePermissionServiceImpl<Group> manageGroupPermissionService) {
        super(manageGroupPermissionService);
    }
}
