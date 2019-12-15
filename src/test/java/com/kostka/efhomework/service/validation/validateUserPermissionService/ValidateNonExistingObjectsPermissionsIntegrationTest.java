package com.kostka.efhomework.service.validation.validateUserPermissionService;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.ValidateUserPermissionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ValidateNonExistingObjectsPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_NAME_1 = "ALPHA";

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateUserPermissionService validateUserPermissionService;

    @Test
    public void checkNotExistingUserPermissionWithLogIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("User '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void checkUserNotExistingPermissionWithLogIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' does not exist."));
    }
}
