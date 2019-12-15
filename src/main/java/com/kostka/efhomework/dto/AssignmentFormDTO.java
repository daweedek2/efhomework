package com.kostka.efhomework.dto;

import javax.validation.constraints.NotEmpty;

public class AssignmentFormDTO extends AbstractFormDTO{
    @NotEmpty(message = "Target name cannot be empty.")
    private String targetName;

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(final String targetName) {
        this.targetName = targetName;
    }
}
