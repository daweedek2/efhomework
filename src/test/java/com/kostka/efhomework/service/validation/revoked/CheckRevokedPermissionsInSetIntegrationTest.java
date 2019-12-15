package com.kostka.efhomework.service.validation.revoked;

import com.kostka.efhomework.EfHomeworkApplication;
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
public class CheckRevokedPermissionsInSetIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private RevokedPermissionService revokedPermissionService;

    @Test(expected = NoPermissionException.class)
    public void verifyRevokedPermissionInSetIsPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
    }

    @Test(/* no exception expected */)
    public void verifyRevokedPermissionInEmptySetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();

        revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
    }

    @Test(/* no exception expected */)
    public void verifyRevokedPermissionInSetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission2);

        revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
    }
}
