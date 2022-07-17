package io.github.Vortex.model.event;

import io.github.Vortex.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    public TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
