package io.github.Vortex.model.event;

import io.github.Vortex.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    public TaskDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
