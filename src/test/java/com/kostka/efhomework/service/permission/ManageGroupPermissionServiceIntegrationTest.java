package com.kostka.efhomework.service.permission;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.permission.ManageGroupPermissionService;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.management.register.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class ManageGroupPermissionServiceIntegrationTest {
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_PERMISSION_1 = "VIEW";

    @Autowired
    PermissionService permissionService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;
    @Autowired
    ManageGroupPermissionService manageGroupPermissionService;

    @Test
    public void grantForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Set<Permission> permissions = groupService.getGroup(TEST_NAME_1).getGrantedPermissions();
        int countBefore = permissions.size();

        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);

        assertEquals(countBefore + 1, permissions.size());
        assertTrue(permissions.contains(permission));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void grantForNotExistingGroupIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void grantNotExistingPermissionForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);

        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
    }

    @Test
    public void grantSamePermissionForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
        Set<Permission> permissions = groupService.getGroup(TEST_NAME_1).getGrantedPermissions();
        int countBefore = permissions.size();

        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);

        assertEquals(countBefore, permissions.size());
        assertTrue(permissions.contains(permission));
    }

    @Test
    public void revokeForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        manageGroupPermissionService.grantPermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
        Set<Permission> permissions = groupService.getGroup(TEST_NAME_1).getRevokedPermissions();
        int countBefore = permissions.size();

        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);

        assertEquals(countBefore + 1, permissions.size());
        assertTrue(permissions.contains(permission));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void revokeForNotExistingGroupIntegrationTest() {
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void revokeNotExistingPermissionForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);

        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
    }

    @Test
    public void revokeSamePermissionForGroupIntegrationTest() {
        Group group = groupService.createGroup(TEST_NAME_1);
        Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
        Set<Permission> permissions = groupService.getGroup(TEST_NAME_1).getRevokedPermissions();
        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);
        int countBefore = permissions.size();

        manageGroupPermissionService.revokePermissionToGroup(TEST_PERMISSION_1, TEST_NAME_1);

        assertEquals(countBefore, permissions.size());
        assertTrue(permissions.contains(permission));
    }
}
