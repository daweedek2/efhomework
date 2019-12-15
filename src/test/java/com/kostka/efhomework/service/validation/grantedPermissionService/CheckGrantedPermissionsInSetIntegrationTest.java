package com.kostka.efhomework.service.validation.grantedPermissionService;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Permission;
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
public class CheckGrantedPermissionsInSetIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GrantedPermissionService grantedPermissionService;

    @Test
    public void verifyRevokedPermissionInSetIsPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        boolean result = grantedPermissionService.isPermissionGrantedInSet(permission1, permissionSet);

        Assert.assertTrue(result);
    }

    @Test
    public void verifyRevokedPermissionInSetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission2);

        boolean result = grantedPermissionService.isPermissionGrantedInSet(permission1, permissionSet);

        Assert.assertFalse(result);
    }

    @Test
    public void verifyRevokedPermissionInEmptySetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();

        boolean result = grantedPermissionService.isPermissionGrantedInSet(permission1, permissionSet);

        Assert.assertFalse(result);
    }
}
