package com.kostka.efhomework.service.validation.grantedPermissionService;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
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
public class CheckGroupWithParentGrantedPermissionsIntegrationTest {
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
    private GrantedPermissionService grantedPermissionService;

    @Test
    public void verifyGrantedPermissionIsPresentInGroupWithParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        group.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyGrantedPermissionIsPresentInParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        parentGroup.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyGrantedPermissionIsPresentInGrandParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);
        grandParentGroup.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        parentGroup.setParentGroups(grandParentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyGrantedPermissionIsNotPresentInGrandParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        parentGroup.setParentGroups(grandParentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertFalse(result);
    }

    @Test
    public void verifyGrantedPermissionIsPresentInParentGroupWithGrandParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);

        parentGroup.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        parentGroup.setParentGroups(grandParentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyGrantedPermissionIsPresentInGrandParentGroupWithGrandParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);

        grandParentGroup.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        parentGroup.setParentGroups(grandParentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyGrantedPermissionIsPresentInParentGroupAndChildGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);

        parentGroup.setGrantedPermissions(permissionSet);
        group.setGrantedPermissions(permissionSet);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setParentGroups(parentGroupSet);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        parentGroup.setParentGroups(grandParentGroupSet);

        boolean result = grantedPermissionService.isPermissionGrantedForGroup(permission1, group);

        Assert.assertTrue(result);
    }
}