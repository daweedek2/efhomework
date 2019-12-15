package com.kostka.efhomework.dto;

import javax.validation.constraints.NotEmpty;

public abstract class AbstractFormDTO {
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
