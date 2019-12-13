package com.kostka.efhomework.service.management.entity.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.GroupRepository;
import com.kostka.efhomework.service.management.entity.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    public GroupServiceImpl(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(final String name) {
        final Group group = new Group();
        // validate unique name
        group.setName(name);
        logger.info("Group '{}' is created.", name);
        return this.saveGroup(group);
    }

    @Override
    public Group getGroup(final String name) {
        final Optional<Group> group = groupRepository.findById(name);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Group with name '" + name + "' does not exist.");
        }
        return group.get();
    }

    @Override
    public Group saveGroup(final @NonNull Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(final String name) {
        try {
            groupRepository.deleteById(name);
            logger.info("Group '{}' is deleted.", name);
        } catch (final EmptyResultDataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isGroupInDb(final String name) {
        return groupRepository.existsById(name);
    }
}
