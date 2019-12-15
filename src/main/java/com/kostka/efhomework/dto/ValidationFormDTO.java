package com.kostka.efhomework.dto;

import javax.validation.constraints.NotEmpty;

public class ValidationFormDTO extends AbstractFormDTO{
    @NotEmpty(message = "Permission name cannot be empty.")
    private String permissionName;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(final String permissionName) {
        this.permissionName = permissionName;
    }
}
