package com.kostka.efhomework.service.register;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.exception.ResourceNotFoundException;
import com.kostka.efhomework.repository.GroupRepository;
import com.kostka.efhomework.service.management.register.impl.GroupServiceImpl;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {
    private static final String TEST_GROUP_NAME = "ALPHA";

    @Mock
    GroupRepository groupRepository;

    @InjectMocks
    GroupServiceImpl groupService;

    @Test
    public void createGroupTest() {
        Group group = new Group();
        group.setName(TEST_GROUP_NAME);
        when(groupRepository.save(any())).thenReturn(group);

        Group createdGroup = groupService.createGroup(TEST_GROUP_NAME);

        Assert.assertEquals(group.getName(), createdGroup.getName());
    }

    @Test
    public void getGroupSuccessTest() {
        Group group = new Group();
        group.setName(TEST_GROUP_NAME);
        when(groupRepository.findById(TEST_GROUP_NAME)).thenReturn(Optional.of(group));

        Group foundGroup = groupService.getGroup(TEST_GROUP_NAME);

        Assert.assertEquals(group.getName(), foundGroup.getName());
        Assert.assertEquals(group, foundGroup);
        Mockito.verify(groupRepository).findById(eq(TEST_GROUP_NAME));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getGroupFailedTest() {
        when(groupRepository.findById(TEST_GROUP_NAME)).thenReturn(Optional.empty());

        groupService.getGroup(TEST_GROUP_NAME);

        Mockito.verify(groupRepository).findById(eq(TEST_GROUP_NAME));
    }

    @Test
    public void deleteGroupSuccessTest() {
        groupService.deleteGroup(TEST_GROUP_NAME);

        Mockito.verify(groupRepository).deleteById(eq(TEST_GROUP_NAME));
    }
}
