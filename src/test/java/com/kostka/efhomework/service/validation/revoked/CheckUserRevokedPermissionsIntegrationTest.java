package com.kostka.efhomework.service.validation.revoked;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.RevokedPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class CheckUserRevokedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";

    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private RevokedPermissionService revokedPermissionService;

    @Test(expected = NoPermissionException.class)
    public void checkRevokedPermissionIsPresentForUserWithNoGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        User user1 = userService.createUser(TEST_NAME_1);
        user1.setRevokedPermissions(permissionSet);

        revokedPermissionService.checkPermissionRevokedForUserWithGroup(permission1, user1);
    }

    @Test(expected = NoPermissionException.class)
    public void checkRevokedPermissionIsPresentInGroupForUserWithOneGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_2);
        group.setRevokedPermissions(permissionSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        revokedPermissionService.checkPermissionRevokedForUserWithGroup(permission1, user1);
    }

    @Test(expected = NoPermissionException.class)
    public void checkRevokedPermissionIsPresentInGroupForUserWithTwoGroupsIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group1 = groupService.createGroup(TEST_NAME_2);
        Group group2 = groupService.createGroup(TEST_NAME_3);
        Group group3 = groupService.createGroup(TEST_NAME_4);
        group2.setRevokedPermissions(permissionSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group1);
        groupSet.add(group2);
        groupSet.add(group3);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        revokedPermissionService.checkPermissionRevokedForUserWithGroup(permission1, user1);
    }

    @Test(expected = NoPermissionException.class)
    public void checkRevokedPermissionIsPresentInParentGroupForUserWithOneGroupWithParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        parentGroup.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        revokedPermissionService.checkPermissionRevokedForUserWithGroup(permission1, user1);
    }
}
