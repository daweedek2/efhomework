package com.kostka.efhomework.service.entity.impl;

import com.kostka.efhomework.entity.Permission;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.PermissionRepository;
import com.kostka.efhomework.service.entity.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;
    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    public PermissionServiceImpl(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(final String name) {
        final Permission permission = new Permission();
        permission.setName(name);
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
    public void deletePermission(final String name) {
        try {
            permissionRepository.deleteById(name);
        } catch (final EmptyResultDataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
