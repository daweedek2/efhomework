package com.kostka.efhomework.service.management.register.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.exception.UniqueNameException;
import com.kostka.efhomework.repository.PermissionRepository;
import com.kostka.efhomework.service.management.register.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(final String name) {
        validateUniqueName(name);
        final Permission permission = new Permission();
        permission.setName(name);
        LOGGER.info("Permission '{}' is created.", name);
        return permissionRepository.save(permission);
    }

    @Override
    public Permission getPermission(final String name) {
        final Optional<Permission> permission = permissionRepository.findById(name);
        if (permission.isEmpty()) {
            throw new ResourceNotFoundException("Permission with name '" + name + "' does not exist.");
        }
        return permission.get();
    }

    @Override
    public Permission savePermission(final @NonNull Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermission(final String name) {
        try {
            permissionRepository.deleteById(name);
            LOGGER.info("Permission '{}' is deleted.", name);
        } catch (final EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cannot delete non-existing permission '" + name + "'.");
        }
    }

    @Override
    public boolean isPermissionInDb(final String name) {
        return permissionRepository.existsById(name);
    }

    private void validateUniqueName(String name) {
        if (isPermissionInDb(name)) {
            throw new UniqueNameException("Permission with name '" + name + "' already exists.");
        }
    }
}
