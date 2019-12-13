package com.kostka.efhomework.service;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.PermissionRepository;
import com.kostka.efhomework.service.management.entity.impl.PermissionServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceImpTest {
    private static final String TEST_PERMISSION_NAME = "VIEW_INCIDENT";

    @Mock
    PermissionRepository permissionRepository;

    @InjectMocks
    PermissionServiceImpl permissionService;

    @Test
    public void createPermissionTest() {
        Permission permission = new Permission();
        permission.setName(TEST_PERMISSION_NAME);
        Mockito.when(permissionRepository.save(any())).thenReturn(permission);

        Permission createdPermission = permissionService.createPermission(TEST_PERMISSION_NAME);

        Assert.assertEquals(permission.getName(), createdPermission.getName());
    }

    @Test
    public void getPermissionSuccessTest() {
        Permission permission = new Permission();
        permission.setName(TEST_PERMISSION_NAME);
        Mockito.when(permissionRepository.findById(TEST_PERMISSION_NAME)).thenReturn(Optional.of(permission));

        Permission foundPermission = permissionService.getPermission(TEST_PERMISSION_NAME);

        Assert.assertEquals(permission.getName(), foundPermission.getName());
        Assert.assertEquals(permission, foundPermission);
        Mockito.verify(permissionRepository).findById(eq(TEST_PERMISSION_NAME));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPermissionFailedTest() {
        Mockito.when(permissionRepository.findById(TEST_PERMISSION_NAME)).thenReturn(Optional.empty());

        permissionService.getPermission(TEST_PERMISSION_NAME);

        Mockito.verify(permissionRepository).findById(eq(TEST_PERMISSION_NAME));
    }

    @Test
    public void deleteDeviceTest() {
        permissionService.deletePermission(TEST_PERMISSION_NAME);

        Mockito.verify(permissionRepository).deleteById(eq(TEST_PERMISSION_NAME));
    }
}
