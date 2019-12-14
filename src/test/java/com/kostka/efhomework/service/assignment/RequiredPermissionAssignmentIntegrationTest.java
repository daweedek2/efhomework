package com.kostka.efhomework.service.assignment;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.assignment.RequiredPermissionAssignmentService;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class RequiredPermissionAssignmentIntegrationTest {
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";

    @Autowired
    RequiredPermissionAssignmentService requiredPermissionAssignmentService;
    @Autowired
    PermissionService permissionService;

    @Test(expected = ResourceNotFoundException.class)
    public void assignNotExistingPermissionToNotExistingPermissionIntegrationTest() {
        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void assignExistingPermissionToNotExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void assignNotExistingPermissionToExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test
    public void assignRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore + 1, permission.getRequiredPermissions().size());
        Assert.assertTrue(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test
    public void assignAlreadyRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore, permission.getRequiredPermissions().size());
        Assert.assertTrue(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deAssignNotExistingPermissionToNotExistingPermissionIntegrationTest() {
        requiredPermissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deAssignExistingPermissionToNotExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        requiredPermissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deAssignNotExistingPermissionToExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        requiredPermissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);
    }

    @Test
    public void deAssignRequiredPermissionToExistingPermissionSuccessfullyIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        requiredPermissionAssignmentService.assignRequiredPermissionToPermission(TEST_NAME_2, TEST_NAME_1);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        requiredPermissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore - 1, permission.getRequiredPermissions().size());
        Assert.assertFalse(permission.getRequiredPermissions().contains(requiredPermission));
    }

    @Test
    public void deAssignRequiredPermissionNotPresentInExistingPermissionIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_NAME_1);
        Permission requiredPermission = permissionService.createPermission(TEST_NAME_2);
        int countOfRequiredPermissionsBefore = permission.getRequiredPermissions().size();

        requiredPermissionAssignmentService.deAssignRequiredPermissionFromPermission(TEST_NAME_2, TEST_NAME_1);

        Assert.assertEquals(countOfRequiredPermissionsBefore, permission.getRequiredPermissions().size());
        Assert.assertFalse(permission.getRequiredPermissions().contains(requiredPermission));
    }
}
