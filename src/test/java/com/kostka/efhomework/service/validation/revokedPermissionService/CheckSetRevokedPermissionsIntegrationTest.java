package com.kostka.efhomework.service.validation.revokedPermissionService;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.NoPermissionException;
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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class CheckSetRevokedPermissionsIntegrationTest {
    private static final String TEST_PERMISSION_1 = "VIEW_DETAIL";
    private static final String TEST_PERMISSION_2 = "CREATE_DETAIL";
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RevokedPermissionService revokedPermissionService;

    @Test
    public void verifyRevokedPermissionInSetIsPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Exception e = assertThrows(NoPermissionException.class, () -> {
            revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
        });

        assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' is revoked."));
    }

    @Test
    public void verifyRevokedPermissionInEmptySetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();

        assertDoesNotThrow(() -> {
            revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
        });
    }

    @Test
    public void verifyRevokedPermissionInSetIsNotPresentIntegrationTest() {
        Permission permission1 = permissionService.createPermission(TEST_PERMISSION_1);
        Permission permission2 = permissionService.createPermission(TEST_PERMISSION_2);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission2);

        assertDoesNotThrow(() -> {
            revokedPermissionService.checkPermissionRevokedInSet(permission1, permissionSet);
        });
    }
}
