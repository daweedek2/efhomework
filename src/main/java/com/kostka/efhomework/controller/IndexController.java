package com.kostka.efhomework.controller;

import com.kostka.efhomework.dto.EntityFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    /**
     * String with the name if the html file.
     */
    public static final String INDEX = "index";

    /**
     * String with the template attribute of the DTO.
     */
    public static final String TEMPLATE_ATTR_ENTITY_FORM = "entityFormDTO";

    @GetMapping
    public String indexPage(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_ENTITY_FORM, new EntityFormDTO());
        return INDEX;
    }
}
