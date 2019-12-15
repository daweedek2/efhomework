package com.kostka.efhomework.service.management.register.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.GroupRepository;
import com.kostka.efhomework.service.management.register.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(final String name) {
        final Group group = new Group();
        // validate unique name
        group.setName(name);
        LOGGER.info("Group '{}' is created.", name);
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
        groupRepository.deleteById(name);
        LOGGER.info("Group '{}' is deleted.", name);
    }

    @Override
    public boolean isGroupInDb(final String name) {
        return groupRepository.existsById(name);
    }
}
