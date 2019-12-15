package com.kostka.efhomework.service.management.assignment;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class PermissionAssignmentServiceIntegrationTest {
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";

    @Autowired
    PermissionAssignmentService permissionAssignmentService;
    @Autowired
    PermissionService permissionService;

    @Test
    public void assignNotExistingPermissionToNotExistingPermissionIntegrationTest() {
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void assignExistingPermissionToNotExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_1, TEST_NAME_2);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void assignNotExistingPermissionToExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void assignRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore + 1, permission.getRequiredPermissions().size());
        Assert.assertTrue(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test
    public void assignAlreadyRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore, permission.getRequiredPermissions().size());
        Assert.assertTrue(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test
    public void deAssignNotExistingPermissionToNotExistingPermissionIntegrationTest() {
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void deAssignExistingPermissionToNotExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_1, TEST_NAME_2);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void deAssignNotExistingPermissionToExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            permissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Permission with name '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void deAssignRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        permissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore - 1, permission.getRequiredPermissions().size());
        Assert.assertFalse(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test
    public void deAssignRequiredPermissionNotPresentInExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        permissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore, permission.getRequiredPermissions().size());
        Assert.assertFalse(permission.getRequiredPermissions().contains(requiredPermission));
    }
}
