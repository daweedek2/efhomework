package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.EntityFormDTO;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.kostka.efhomework.controller.IndexController.INDEX;
import static com.kostka.efhomework.controller.IndexController.TEMPLATE_ATTR_ENTITY_FORM;

@Controller
@RequestMapping("/register")
public class RegisterController {

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
    public String createGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        groupService.createGroup(entityDTO.getName());
        return INDEX;
    }

    @PostMapping(value = "/deleteGroup")
    public String deleteGroup(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        groupService.deleteGroup(entityDTO.getName());
        return INDEX;
    }

    @PostMapping(value = "/createUser")
    public String createUser(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                             final BindingResult bindingResult,
                             final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        userService.createUser(entityDTO.getName());
        return INDEX;
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                             final BindingResult bindingResult,
                             final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        userService.deleteUser(entityDTO.getName());
        return INDEX;
    }

    @PostMapping(value = "/createPermission")
    public String createPermission(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                                   final BindingResult bindingResult,
                                   final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        permissionService.createPermission(entityDTO.getName());
        return INDEX;
    }

    @PostMapping(value = "/deletePermission")
    public String deletePermission(@Valid @ModelAttribute(TEMPLATE_ATTR_ENTITY_FORM) final EntityFormDTO entityDTO,
                                   final BindingResult bindingResult,
                                   final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, entityDTO);
            return INDEX;
        }
        permissionService.deletePermission(entityDTO.getName());
        return INDEX;
    }
}
