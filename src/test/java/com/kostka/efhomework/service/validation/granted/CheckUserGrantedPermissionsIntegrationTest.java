package com.kostka.efhomework.service.validation.granted;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import com.kostka.efhomework.service.validation.GrantedPermissionService;
import org.junit.Assert;
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
public class CheckUserGrantedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";

    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";
    private static final String TEST_NAME_5 = "OMEGA";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GrantedPermissionService grantedPermissionService;

    @Test
    public void checkGrantedPermissionIsPresentForUserWithNoGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        User user1 = userService.createUser(TEST_NAME_1);
        user1.setGrantedPermissions(permissionSet);

        boolean result = grantedPermissionService.isPermissionGrantedForUserWithGroup(permission1, user1);

        Assert.assertTrue(result);
    }

    @Test
    public void checkGrantedPermissionIsPresentInGroupForUserWithOneGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_2);
        group.setGrantedPermissions(permissionSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForUserWithGroup(permission1, user1);

        Assert.assertTrue(result);
    }

    @Test
    public void checkGrantedPermissionIsPresentInGroupForUserWithTwoGroupsIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group1 = groupService.createGroup(TEST_NAME_2);
        Group group2 = groupService.createGroup(TEST_NAME_3);
        Group group3 = groupService.createGroup(TEST_NAME_4);
        group2.setGrantedPermissions(permissionSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group1);
        groupSet.add(group2);
        groupSet.add(group3);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForUserWithGroup(permission1, user1);

        Assert.assertTrue(result);
    }

    @Test
    public void checkGrantedPermissionIsPresentInGroupForUserWithTwoGroupsWithParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group1 = groupService.createGroup(TEST_NAME_2);
        Group group2 = groupService.createGroup(TEST_NAME_3);
        Group group3 = groupService.createGroup(TEST_NAME_4);
        Group parentGroup = groupService.createGroup(TEST_NAME_5);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        parentGroup.setGrantedPermissions(permissionSet);
        group2.setParentGroups(parentGroupSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group1);
        groupSet.add(group2);
        groupSet.add(group3);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForUserWithGroup(permission1, user1);

        Assert.assertTrue(result);
    }

    @Test
    public void checkGrantedPermissionIsPresentInParentGroupForUserWithOneGroupWithParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_2);
        Group parentGroup = groupService.createGroup(TEST_NAME_3);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        parentGroup.setGrantedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);

        Set<Group> groupSet = new HashSet<>();
        groupSet.add(group);
        User user1 = userService.createUser(TEST_NAME_1);
        user1.setParentGroups(groupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForUserWithGroup(permission1, user1);

        Assert.assertTrue(result);
    }
}
