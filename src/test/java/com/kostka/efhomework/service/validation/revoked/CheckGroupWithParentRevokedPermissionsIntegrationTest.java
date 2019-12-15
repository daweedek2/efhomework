package com.kostka.efhomework.service.validation.revoked;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
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
public class CheckGroupWithParentRevokedPermissionsIntegrationTest {
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

    // Testing presence of Revoked permission for Group with Parent Group

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentInGroupWithParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        group.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentInParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        parentGroup.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    // Testing presence of Revoked permission for Group with Parent Group with grand-parent group

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentInGrandParentGroupIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        Group parentGroup = groupService.createGroup(TEST_NAME_2);
        Set<Group> parentGroupSet = new HashSet<>();
        parentGroupSet.add(parentGroup);
        Group grandParentGroup = groupService.createGroup(TEST_NAME_3);
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);
        grandParentGroup.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);
        parentGroup.setParentGroups(grandParentGroupSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentInParentGroupWithGrandParentGroupIntegrationTest() {
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
        Set<Group> granParentGroupSet = new HashSet<>();
        granParentGroupSet.add(grandParentGroup);

        parentGroup.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);
        parentGroup.setParentGroups(granParentGroupSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentInParentGroupAndChildGroupIntegrationTest() {
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
        Set<Group> grandParentGroupSet = new HashSet<>();
        grandParentGroupSet.add(grandParentGroup);

        parentGroup.setRevokedPermissions(permissionSet);
        group.setRevokedPermissions(permissionSet);
        group.setParentGroups(parentGroupSet);
        parentGroup.setParentGroups(grandParentGroupSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }
}
