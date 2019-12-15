package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.exception.UniqueNameException;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
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
@RequestMapping("/register")
public class RegisterController extends AbstractController{
    private static final String TEMPLATE_ATTR_REGISTER_RESULT = "registerResult";
    private GroupService groupService;
    private UserService userService;
    private PermissionService permissionService;

    @Autowired
    public RegisterController(final GroupService groupService,
                              final UserService userService,
                              final PermissionService permissionService) {
            this.groupService = groupService;
            this.userService = userService;
            this.permissionService = permissionService;
        }

    @PostMapping(value = "/createGroup")
    public String createGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                  final RegisterFormDTO registerFormDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        groupService.createGroup(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deleteGroup")
    public String deleteGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                  final RegisterFormDTO registerFormDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        groupService.deleteGroup(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/createUser")
    public String createUser(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                 final RegisterFormDTO registerFormDTO,
                             final BindingResult bindingResult,
                             final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        userService.createUser(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                 final RegisterFormDTO registerFormDTO,
                             final BindingResult bindingResult,
                             final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        userService.deleteUser(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/createPermission")
    public String createPermission(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                       final RegisterFormDTO registerFormDTO,
                                   final BindingResult bindingResult,
                                   final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        permissionService.createPermission(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @PostMapping(value = "/deletePermission")
    public String deletePermission(@Valid @ModelAttribute(TEMPLATE_ATTR_REGISTER_FORM)
                                       final RegisterFormDTO registerFormDTO,
                                   final BindingResult bindingResult,
                                   final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, registerFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        permissionService.deletePermission(registerFormDTO.getName());
        addModelAttributes(model);
        return INDEX;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class, UniqueNameException.class})
    public String handleException(final Exception e, final Model model) {
        addModelAttributes(model);
        model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, new RegisterFormDTO());
        model.addAttribute(TEMPLATE_ATTR_REGISTER_RESULT, e.getMessage());
        return INDEX;
    }

    private void addModelAttributes(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, new AssignmentFormDTO());
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, new ValidationFormDTO());
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, new PermissionFormDTO());
    }
}
