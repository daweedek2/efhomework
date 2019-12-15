package com.kostka.efhomework.service.validation.validateUserPermissionService;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.assignment.GroupAssignmentService;
import com.kostka.efhomework.service.management.assignment.PermissionAssignmentService;
import com.kostka.efhomework.service.management.assignment.UserAssignmentService;
import com.kostka.efhomework.service.management.permission.ManageGroupPermissionService;
import com.kostka.efhomework.service.management.permission.ManageUserPermissionService;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.ValidateUserPermissionService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ValidateUserGrantedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void checkUserPermissionGrantedIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }

    @Test
    public void checkUserPermissionIsNotGrantedIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);
        });

        Assert.assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is not granted."));
    }

    @Test
    public void checkUserPermissionGrantedForOneGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);

        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }

    @Test
    public void checkRequiredPermissionIsGrantedForUserIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_2, TEST_NAME_1);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);

        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }

    @Test
    public void checkRequiredPermissionIsGrantedForGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_2, TEST_NAME_2);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);

        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }

    @Test
    public void checkRequiredPermissionIsGrantedForParentGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_3);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_2, TEST_NAME_3);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);

        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }

    @Test
    public void checkRequiredPermissionIsGrantedInUserAndParentGroupIntegrationTest() {
        User user = userService.createUser(TEST_NAME_1);
        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Permission reqPermission = permissionService.createPermission(TEST_PERMISSION_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_1, TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_2);
        permissionAssignmentService.assignRequiredPermissionToPermission(TEST_PERMISSION_2, TEST_PERMISSION_1);
        manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_2, TEST_NAME_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_2);

        validateUserPermissionService.checkUserPermissions(TEST_PERMISSION_1, TEST_NAME_1);

        Assert.assertTrue(outContent.toString().contains("SUCCESS. Permission '" + TEST_PERMISSION_1 + "' is granted for user '" + TEST_NAME_1 + "'."));
    }
}
