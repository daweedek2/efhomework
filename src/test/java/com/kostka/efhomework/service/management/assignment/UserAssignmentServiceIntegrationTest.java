package com.kostka.efhomework.service.management.assignment;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class UserAssignmentServiceIntegrationTest {
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";

    @Autowired
    UserAssignmentService userAssignmentService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;


    @Test
    public void assignExistingGroupToExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        User user1 = userService.createUser(TEST_NAME_2);
        int countBefore = userService.getUser(TEST_NAME_2).getParentGroups().size();

        userAssignmentService.assignUserToGroup(TEST_NAME_2, TEST_NAME_1);

        Assert.assertTrue(userService.getUser(TEST_NAME_2).getParentGroups().contains(group1));
        Assert.assertEquals(countBefore + 1, userService.getUser(TEST_NAME_2).getParentGroups().size());
    }

    @Test
    public void assign2ExistingGroupsToExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        Group group2 = groupService.createGroup(TEST_NAME_2);
        User user1 = userService.createUser(TEST_NAME_3);
        int countBefore = userService.getUser(TEST_NAME_3).getParentGroups().size();

        userAssignmentService.assignUserToGroup(TEST_NAME_3, TEST_NAME_1);
        userAssignmentService.assignUserToGroup(TEST_NAME_3, TEST_NAME_2);

        Assert.assertEquals(countBefore + 2, userService.getUser(TEST_NAME_3).getParentGroups().size());
        Assert.assertTrue(userService.getUser(TEST_NAME_3).getParentGroups().contains(group1));
        Assert.assertTrue(userService.getUser(TEST_NAME_3).getParentGroups().contains(group2));
    }

    @Test
    public void assignNotExistingGroupToExistingUserIntegrationTest() {
        User user1 = userService.createUser(TEST_NAME_2);
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            userAssignmentService.assignUserToGroup(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Group with name '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void assignExistingGroupToNotExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            userAssignmentService.assignUserToGroup(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("User with name '" + TEST_NAME_2 + "' does not exist."));

    }

    @Test
    public void deAssignExistingGroupFromExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        User user1 = userService.createUser(TEST_NAME_2);
        userAssignmentService.assignUserToGroup(TEST_NAME_2, TEST_NAME_1);
        int countBefore = userService.getUser(TEST_NAME_2).getParentGroups().size();

        userAssignmentService.deAssignUserFromGroup(TEST_NAME_2, TEST_NAME_1);

        Assert.assertFalse(userService.getUser(TEST_NAME_2).getParentGroups().contains(group1));
        Assert.assertEquals(countBefore -1 , userService.getUser(TEST_NAME_2).getParentGroups().size());
    }

    @Test
    public void deAssignNotPresentGroupFromExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        User user1 = userService.createUser(TEST_NAME_2);
        int countBefore = userService.getUser(TEST_NAME_2).getParentGroups().size();

        userAssignmentService.deAssignUserFromGroup(TEST_NAME_2, TEST_NAME_1);

        Assert.assertFalse(userService.getUser(TEST_NAME_2).getParentGroups().contains(group1));
        Assert.assertEquals(countBefore, userService.getUser(TEST_NAME_2).getParentGroups().size());
    }

    @Test
    public void deAssignNotExistingGroupFromExistingUserIntegrationTest() {
        User user1 = userService.createUser(TEST_NAME_2);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            userAssignmentService.deAssignUserFromGroup(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("Group with name '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void deAssignExistingGroupFromNotExistingUserIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            userAssignmentService.deAssignUserFromGroup(TEST_NAME_2, TEST_NAME_1);
        });

        assertTrue(e.getMessage().contains("User with name '" + TEST_NAME_2 + "' does not exist."));
    }

}