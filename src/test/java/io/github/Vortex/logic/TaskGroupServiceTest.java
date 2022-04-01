package io.github.Vortex.logic;

import io.github.Vortex.model.TaskGroup;
import io.github.Vortex.model.TaskGroupRepository;
import io.github.Vortex.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {
    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        //GIVEN
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);

        //System under test
        var toTest = new TaskGroupService(null, mockTaskRepository);

        //WHEN
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        //THEN
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks.");
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //GIVEN
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        //System under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        //WHEN
        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        //THEN
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found.");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        //GIVEN
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        var group = new TaskGroup();
        var beforeToggle = group.isDone();

        var mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));

        //System under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        //WHEN
        toTest.toggleGroup(0);

        //THEN
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean value) {
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(value);
        return mockTaskRepository;
    }
}