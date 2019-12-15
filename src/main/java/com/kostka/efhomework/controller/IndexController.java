package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController extends AbstractController{

    @GetMapping
    public String indexPage(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, new RegisterFormDTO());
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, new AssignmentFormDTO());
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, new ValidationFormDTO());
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, new PermissionFormDTO());
        return INDEX;
    }
}
