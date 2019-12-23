package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import com.kostka.efhomework.entity.AbstractEntity;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.permission.impl.AbstractManagePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public abstract class AbstractPermissionController<T extends AbstractEntity> extends AbstractController{
    private static final String TEMPLATE_ATTR_PERMISSION_RESULT = "permissionResult";
    private final AbstractManagePermissionServiceImpl<T> manageGroupPermissionService;

    protected AbstractPermissionController(AbstractManagePermissionServiceImpl<T> manageGroupPermissionService) {
        this.manageGroupPermissionService = manageGroupPermissionService;
    }

    @PostMapping("/grant")
    public String grantPermissionForGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_PERMISSION_FORM)
                                         final PermissionFormDTO permissionFormDTO,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, permissionFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        manageGroupPermissionService
                .grantPermission(
                        permissionFormDTO.getName(),
                        permissionFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping("/revoke")
    public String revokePermissionForGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_PERMISSION_FORM)
                                          final PermissionFormDTO permissionFormDTO,
                                          final BindingResult bindingResult,
                                          final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, permissionFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        manageGroupPermissionService
                .revokePermission(
                        permissionFormDTO.getName(),
                        permissionFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public String handleException(final Exception e, final Model model) {
        addModelAttributes(model);
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, new PermissionFormDTO());
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_RESULT, e.getMessage());
        return INDEX;
    }

    private void addModelAttributes(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, new RegisterFormDTO());
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, new ValidationFormDTO());
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, new AssignmentFormDTO());
    }
}
