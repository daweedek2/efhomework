package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.AssignmentFormDTO;
import com.kostka.efhomework.dto.PermissionFormDTO;
import com.kostka.efhomework.dto.RegisterFormDTO;
import com.kostka.efhomework.dto.ValidationFormDTO;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.validation.ValidateUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@ControllerAdvice
@RequestMapping("/validation")
public class ValidationController extends AbstractController{
    private static final String TEMPLATE_ATTR_VALIDATION_RESULT = "validationResult";
    private ValidateUserPermissionService validateUserPermissionService;

    @Autowired
    public ValidationController(final ValidateUserPermissionService validateUserPermissionService) {
        this.validateUserPermissionService = validateUserPermissionService;
    }

    @PostMapping("/validateUserPermission")
    public String validateUserPermission(@Valid @ModelAttribute(TEMPLATE_ATTR_VALIDATION_FORM)
                                             final ValidationFormDTO validationFormDTO,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, validationFormDTO);
            addModelAttributes(model);
            return INDEX;
        }
        validateUserPermissionService
                .checkUserPermissions(
                        validationFormDTO.getPermissionName(),
                        validationFormDTO.getName());
        addModelAttributes(model);
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_RESULT, createSuccessMessage(validationFormDTO));
        return INDEX;
    }

    @ExceptionHandler(value = {NoPermissionException.class, ResourceNotFoundException.class})
    public String handleException(final Exception e, final Model model) {
        addModelAttributes(model);
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_FORM, new ValidationFormDTO());
        model.addAttribute(TEMPLATE_ATTR_VALIDATION_RESULT, e.getMessage());
        return INDEX;
    }

    private String createSuccessMessage(final ValidationFormDTO dto) {
        return new StringBuilder()
                .append("SUCCESS. Permission '")
                .append(dto.getPermissionName())
                .append("' is granted for user '")
                .append(dto.getName())
                .append("'.")
                .toString();
    }

    private void addModelAttributes(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_ASSIGNMENT_FORM, new AssignmentFormDTO());
        model.addAttribute(TEMPLATE_ATTR_REGISTER_FORM, new RegisterFormDTO());
        model.addAttribute(TEMPLATE_ATTR_PERMISSION_FORM, new PermissionFormDTO());
    }
}
