package com.kostka.efhomework.service.validation.revoked;

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

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionIsPresentForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Group group = groupService.createGroup(TEST_NAME_1);
        group.setRevokedPermissions(permissionSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    @Test(/* no exception expected */)
    public void verifyRevokedPermissionIsNotPresentInEmptySetForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);

        Group group = groupService.createGroup(TEST_NAME_1);

        revokedPermissionService.checkPermissionRevokedForGroup(permission1, group);
    }

    @Test(/* no exception expected */)
    public void verifyRevokedPermissionIsNotPresentForGroupWithoutParentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);

        Group group = groupService.createGroup(TEST_NAME_1);
        group.setRevokedPermissions(permissionSet);

        revokedPermissionService.checkPermissionRevokedForGroup(permission2, group);
    }
}
