package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.exception.UniqueNameException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
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

    @Test
    public void getUserFailedTest() {
        Mockito.when(userRepository.findById(TEST_USER_NAME)).thenReturn(Optional.empty());

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUser(TEST_USER_NAME);
        });

        verify(userRepository).findById(eq(TEST_USER_NAME));
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(TEST_USER_NAME);

        verify(userRepository).deleteById(eq(TEST_USER_NAME));
    }

    @Test
    public void isUserInDb() {
        userService.isUserInDb(TEST_USER_NAME);

        verify(userRepository).existsById(eq(TEST_USER_NAME));
    }

    @Test
    public void createGroupAlreadyExistingNameTest() {
        when(userRepository.existsById(TEST_USER_NAME)).thenReturn(true);

        Exception e = assertThrows(UniqueNameException.class, () -> {
            userService.createUser(TEST_USER_NAME);
        });

        assertTrue(e.getMessage().contains("User with name '" + TEST_USER_NAME + "' already exists."));
    }
}
