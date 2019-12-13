package com.kostka.efhomework.service;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.UserRepository;
import com.kostka.efhomework.service.management.register.impl.UserServiceImpl;
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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String TEST_USER_NAME = "Mat";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createUserTest() {
        User user = new User();
        user.setName(TEST_USER_NAME);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        User createdUser = userService.createUser(TEST_USER_NAME);

        Assert.assertEquals(user.getName(), createdUser.getName());
    }

    @Test
    public void getUserSuccessTest() {
        User user = new User();
        user.setName(TEST_USER_NAME);
        Mockito.when(userRepository.findById(TEST_USER_NAME)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(TEST_USER_NAME);

        Assert.assertEquals(user, foundUser);
        Assert.assertEquals(user.getName(), foundUser.getName());
        verify(userRepository).findById(eq(TEST_USER_NAME));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserFailedTest() {
        Mockito.when(userRepository.findById(TEST_USER_NAME)).thenReturn(Optional.empty());

        userService.getUser(TEST_USER_NAME);

        verify(userRepository).findById(eq(TEST_USER_NAME));
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(TEST_USER_NAME);
        verify(userRepository).deleteById(eq(TEST_USER_NAME));
    }
}
