package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.assignment.GroupAssignmentService;
import com.kostka.efhomework.service.management.assignment.PermissionAssignmentService;
import com.kostka.efhomework.service.management.assignment.UserAssignmentService;
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
@RequestMapping("/assignment")
public class AssignmentController extends AbstractController{
    private static final String TEMPLATE_ATTR_ASSIGNMENT_RESULT = "assignmentResult";
    private UserAssignmentService userAssignmentService;
    private GroupAssignmentService groupAssignmentService;
    private PermissionAssignmentService permissionAssignmentService;

    @Autowired
    public AssignmentController(final UserAssignmentService userAssignmentService,
                                final GroupAssignmentService groupAssignmentService,
                                final PermissionAssignmentService permissionAssignmentService) {
        this.userAssignmentService = userAssignmentService;
        this.groupAssignmentService = groupAssignmentService;
        this.permissionAssignmentService = permissionAssignmentService;
    }

    @PostMapping(value = "/assignUserToGroup")
    public String assignUserToGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                  final AssignmentFormDTO assignmentFormDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        userAssignmentService
                .assignUserToGroup(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deAssignUserFromGroup")
    public String deAssignUserFromGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                    final AssignmentFormDTO assignmentFormDTO,
                                    final BindingResult bindingResult,
                                    final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        userAssignmentService
                .deAssignUserFromGroup(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/assignGroupToGroup")
    public String assignGroupToGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                    final AssignmentFormDTO assignmentFormDTO,
                                    final BindingResult bindingResult,
                                    final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        groupAssignmentService
                .assignParentGroupToGroup(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deAssignGroupFromGroup")
    public String deAssignGroupFromGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                        final AssignmentFormDTO assignmentFormDTO,
                                        final BindingResult bindingResult,
                                        final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        groupAssignmentService
                .deAssignParentGroupFromGroup(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/assignRequiredPermission")
    public String assignRequiredPermission(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                     final AssignmentFormDTO assignmentFormDTO,
                                     final BindingResult bindingResult,
                                     final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        permissionAssignmentService
                .assignRequiredPermissionToPermission(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deAssignRequiredPermission")
    public String deAssignRequiredPermission(@Valid @ModelAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM)
                                         final AssignmentFormDTO assignmentFormDTO,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, assignmentFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        permissionAssignmentService
                .deAssignRequiredPermissionFromPermission(
                        assignmentFormDTO.getName(),
                        assignmentFormDTO.getTargetName());
        addModelAttributes(model);
        return INDEX;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public String handleException(final Exception e, final Model model) {
        addModelAttributes(model);
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, new AssignmentFormDTO());
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_RESULT, e.getMessage());
        return INDEX;
    }

    private void addModelAttributes(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, new RegisterFormDTO());
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, new ValidationFormDTO());
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, new PermissionFormDTO());
    }
}
