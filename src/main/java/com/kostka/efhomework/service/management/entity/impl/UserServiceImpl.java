package com.kostka.efhomework.service.management.entity.impl;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.UserRepository;
import com.kostka.efhomework.service.management.entity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final String name) {
        final User user = new User();
        // validate unique name
        user.setName(name);
        return this.saveUser(user);
    }

    @Override
    public User getUser(final String name) {
        final Optional<User> user = userRepository.findById(name);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with name '" + name + "' does not exist.");
        }
        return user.get();
    }

    @Override
    public User saveUser(final User user) {
        logger.info("User '{}' is created.", user.getName());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final String name) {
        try {
            userRepository.deleteById(name);
            logger.info("User '{}' is deleted.", name);
        } catch (final EmptyResultDataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isUserInDb(final String name) {
        return userRepository.existsById(name);
    }
}
