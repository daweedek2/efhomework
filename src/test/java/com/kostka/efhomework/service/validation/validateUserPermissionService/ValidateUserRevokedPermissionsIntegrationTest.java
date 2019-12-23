package com.kostka.efhomework.service.validation.validateUserPermissionService;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.assignment.GroupAssignmentService;
import com.kostka.efhomework.service.management.assignment.PermissionAssignmentService;
import com.kostka.efhomework.service.management.assignment.UserAssignmentService;
import com.kostka.efhomework.service.management.permission.ManageGroupPermissionService;
import com.kostka.efhomework.service.management.register.GroupService;
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
public class ValidateUserRevokedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    GroupAssignmentService groupAssignmentService;
    @Autowired
    UserAssignmentService userAssignmentService;
    @Autowired
    PermissionAssignmentService permissionAssignmentService;
    @Autowired
    ManageGroupPermissionService manageGroupPermissionService;
    @Autowired
    ManageUserPermissionService manageUserPermissionService;
    @Autowired
    private ValidateUserPermissionService validateUserPermissionService;

    @Test
    public void checkUserPermissionIsRevokedIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        manageUserPermissionService.revokePermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });
        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionRevokedForOneGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);


        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionRevokedForOneGroupAndRevokedForUserIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);
        manageUserPermissionService.revokePermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionIsRevokedAndGrantedIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        manageUserPermissionService.revokePermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkRequiredPermissionIsRevokedForUserIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
        manageUserPermissionService.revokePermissionToUser(TEST_PERMISSION_2, TEST_NAME_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_2 + "' is revoked."));
    }

    @Test
    public void checkRequiredPermissionIsRevokedForGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_2, TEST_NAME_2);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_2 + "' is revoked."));
    }

    @Test
    public void checkRequiredPermissionIsRevokedForParentGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_3);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_2, TEST_NAME_3);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_2 + "' is revoked."));
    }

    @Test
    public void checkRequiredPermissionIsRevokedCombination1IntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_3);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_2, TEST_NAME_2);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_2 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionRevokedForOneGroupAndGrantedForUserIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionRevokedForOneGroupAndGrantedInSecondIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group group2 = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_3);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_3);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void checkUserPermissionRevokedForParentGroupAndGrantedInChildGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_2);
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_3);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }
}
