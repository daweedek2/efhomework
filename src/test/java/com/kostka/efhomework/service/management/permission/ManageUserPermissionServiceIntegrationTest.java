package com.kostka.efhomework.service.management.permission;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class ManageUserPermissionServiceIntegrationTest {
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
        ManageUserPermissionService manageUserPermissionService;

        @Test
        public void grantForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
            Set<Permission> permissions = userService.getUser(TEST_NAME_1).getGrantedPermissions();
            int countBefore = permissions.size();

            manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

            assertEquals(countBefore + 1, permissions.size());
            assertTrue(permissions.contains(permission));
        }

        @Test
        public void grantNotExistingPermissionForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);

            Exception e = assertThrows(ResourceNotFoundException.class, () -> {
                manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            });

            assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' does not exist."));
        }

        @Test
        public void grantForNotExistingUserIntegrationTest() {
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

            Exception e = assertThrows(ResourceNotFoundException.class, () -> {
                manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            });

            assertTrue(e.getMessage().contains("User '" + TEST_NAME_1 + "' does not exist."));
        }

        @Test
        public void grantSamePermissionForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
            manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            Set<Permission> permissions = userService.getUser(TEST_NAME_1).getGrantedPermissions();
            int countBefore = permissions.size();

            manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);

            assertEquals(countBefore, permissions.size());
            assertTrue(permissions.contains(permission));
        }

        @Test
        public void revokeForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
            manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            Set<Permission> permissions = userService.getUser(TEST_NAME_1).getRevokedPermissions();
            int countBefore = permissions.size();

            manageUserPermissionService.revokePermissionFromUser(TEST_PERMISSION_1, TEST_NAME_1);

            assertEquals(countBefore + 1, permissions.size());
            assertTrue(permissions.contains(permission));
        }

        @Test
        public void revokeForNotExistingUserIntegrationTest() {
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);

            Exception e = assertThrows(ResourceNotFoundException.class, () -> {
                manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            });

            assertTrue(e.getMessage().contains("User '" + TEST_NAME_1 + "' does not exist."));
        }

        @Test
        public void revokeNotExistingPermissionForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);

            Exception e = assertThrows(ResourceNotFoundException.class, () -> {
                manageUserPermissionService.grantPermissionToUser(TEST_PERMISSION_1, TEST_NAME_1);
            });

            assertTrue(e.getMessage().contains("Permission '" + TEST_PERMISSION_1 + "' does not exist."));
        }

        @Test
        public void revokeSamePermissionForUserIntegrationTest() {
            User user = userService.createUser(TEST_NAME_1);
            Permission permission = permissionService.createPermission(TEST_PERMISSION_1);
            Set<Permission> permissions = userService.getUser(TEST_NAME_1).getRevokedPermissions();
            manageUserPermissionService.revokePermissionFromUser(TEST_PERMISSION_1, TEST_NAME_1);
            int countBefore = permissions.size();

            manageUserPermissionService.revokePermissionFromUser(TEST_PERMISSION_1, TEST_NAME_1);

            assertEquals(countBefore, permissions.size());
            assertTrue(permissions.contains(permission));
        }
}
