package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.permission.ManageGroupPermissionService;
import com.kostka.efhomework.service.management.permission.ManageUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/permission")
public class PermissionController extends AbstractController{
    private static final String TEMPLATE_ATTR_PERMISSION_RESULT = "permissionResult";
    private ManageUserPermissionService manageUserPermissionService;
    private ManageGroupPermissionService manageGroupPermissionService;

    @Autowired
    public PermissionController(final ManageUserPermissionService manageUserPermissionService,
                                final ManageGroupPermissionService manageGroupPermissionService) {
        this.manageUserPermissionService = manageUserPermissionService;
        this.manageGroupPermissionService = manageGroupPermissionService;
    }

    @PostMapping("/grantForUser")
    public String grantPermissionForUser(@Valid @ModelAttribute(TEMPLATE_ATTR_PERMISSION_FORM)
                                             final PermissionFormDTO permissionFormDTO,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, permissionFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        manageUserPermissionService
                .grantPermissionToUser(
                        permissionFormDTO.getName(),
                        permissionFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping("/revokeForUser")
    public String revokePermissionForUser(@Valid @ModelAttribute(TEMPLATE_ATTR_PERMISSION_FORM)
                                         final PermissionFormDTO permissionFormDTO,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, permissionFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        manageUserPermissionService
                .revokePermissionToUser(
                        permissionFormDTO.getName(),
                        permissionFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping("/grantForGroup")
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
                .grantPermissionToGroup(
                        permissionFormDTO.getName(),
                        permissionFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping("/revokeForGroup")
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
                .revokePermissionToGroup(
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
