package com.kostka.efhomework.service.validation.revokedPermissionService;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.NoPermissionException;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.PermissionService;
import com.kostka.efhomework.service.validation.RevokedPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class CheckGroupRevokedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";

    private static final String TEST_NAME_1 = "ALPHA";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private RevokedPermissionService revokedPermissionService;

    @Test
    public void verifyRevokedPermissionIsPresentForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        group.setRevokedPermissions(permissionSet);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
        });

        assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void verifyRevokedPermissionIsNotPresentInEmptySetForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);

        Group group = groupService.createGroup(TEST_NAME_1);

        assertDoesNotThrow(() -> {
            revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
        });
    }

    @Test
    public void verifyRevokedPermissionIsNotPresentForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);

        Group group = groupService.createGroup(TEST_NAME_1);
        group.setRevokedPermissions(permissionSet);

        assertDoesNotThrow(() -> {
            revokedPermissionService.checkPermissionRevokedForGroup(permission2, group);
        });
    }
}
