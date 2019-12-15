package com.kostka.efhomework.service.management.register.impl;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.exception.UniqueNameException;
import com.kostka.efhomework.repository.UserRepository;
import com.kostka.efhomework.service.management.register.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final String name) {
        validateUniqueName(name);
        final User user = new User();
        user.setName(name);
        LOGGER.info("User '{}' is created.", user.getName());
        return this.saveUser(user);
    }

    @Override
    public User getUser(final String name) {
        final Optional<User> user = userRepository.findById(name);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User '" + name + "' does not exist.");
        }
        return user.get();
    }

    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final String name) {
        try {
            userRepository.deleteById(name);
            LOGGER.info("User '{}' is deleted.", name);
        } catch (final EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Cannot delete non-existing user '" + name + "'.");
        }
    }

    @Override
    public boolean isUserInDb(final String name) {
        return userRepository.existsById(name);
    }

    private void validateUniqueName(final String name) {
        if (isUserInDb(name)) {
            throw new UniqueNameException("User with name '" + name + "' already exists.");
        }
    }
}
