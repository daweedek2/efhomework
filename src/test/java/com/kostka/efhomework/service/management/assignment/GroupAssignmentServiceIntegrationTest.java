package com.kostka.efhomework.service.management.assignment;

import com.kostka.efhomework.EfHomeworkApplication;
import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.exception.ParentGroupRestrictionException;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EfHomeworkApplication.class)
@Transactional
public class GroupAssignmentServiceIntegrationTest {
    private static final String TEST_NAME_1 = "ALPHA";
    private static final String TEST_NAME_2 = "BETA";
    private static final String TEST_NAME_3 = "GAMA";
    private static final String TEST_NAME_4 = "DELTA";

    @Autowired
    GroupAssignmentService groupAssignmentService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @Test
    public void assignExistingParentGroupToExistingGroupIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        Group group2 = groupService.createGroup(TEST_NAME_2);

        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_1, TEST_NAME_2);

        Assert.assertTrue(groupService.getGroup(TEST_NAME_2).getParentGroups().contains(group1));
        Assert.assertEquals(1, groupService.getGroup(TEST_NAME_2).getParentGroups().size());
    }

    @Test
    public void deAssignExistingParentGroupFromExistingGroupIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        Group group2 = groupService.createGroup(TEST_NAME_2);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_2, TEST_NAME_1);

        groupAssignmentService.deAssignParentGroupFromGroup(TEST_NAME_2, TEST_NAME_1);

        Assert.assertFalse(groupService.getGroup(TEST_NAME_2).getParentGroups().contains(group1));
        Assert.assertEquals(0, groupService.getGroup(TEST_NAME_2).getParentGroups().size());
    }

    @Test
    public void deAssignParentGroupFromNotExistingGroupIntegrationTest() {
        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            groupAssignmentService.deAssignParentGroupFromGroup(TEST_NAME_2, TEST_NAME_1);
        });

        Assertions.assertTrue(e.getMessage().contains("Group '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void deAssignNotPresentParentGroupFromExistingGroupIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            groupAssignmentService.deAssignParentGroupFromGroup(TEST_NAME_2,TEST_NAME_1);
        });

        Assertions.assertTrue(e.getMessage().contains("Group '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void assignNotExistingParentGroupToExistingGroupIntegrationTest() {
        Group group2 = groupService.createGroup(TEST_NAME_2);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            groupAssignmentService.assignParentGroupToGroup(TEST_NAME_1, TEST_NAME_2);
        });

        Assertions.assertTrue(e.getMessage().contains("Group '" + TEST_NAME_1 + "' does not exist."));
    }

    @Test
    public void assignExistingParentGroupToNotExistingGroupIntegrationTest() {
        Group group1 = groupService.createGroup(TEST_NAME_1);

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            groupAssignmentService.assignParentGroupToGroup(TEST_NAME_1, TEST_NAME_2);
        });

        Assertions.assertTrue(e.getMessage().contains("Group '" + TEST_NAME_2 + "' does not exist."));
    }

    @Test
    public void assignParentGroupWhenAlreadyPresentParentGroup() {
        Group group1 = groupService.createGroup(TEST_NAME_1);
        Group group2 = groupService.createGroup(TEST_NAME_2);
        Group group3 = groupService.createGroup(TEST_NAME_3);
        groupAssignmentService.assignParentGroupToGroup(TEST_NAME_2, TEST_NAME_1);

        Exception e = assertThrows(ParentGroupRestrictionException.class, () -> {
            groupAssignmentService.assignParentGroupToGroup(TEST_NAME_3, TEST_NAME_1);
        });

        Assertions.assertTrue(e.getMessage().contains("Group '" + TEST_NAME_1 + "' has already one parent group."));
    }

}