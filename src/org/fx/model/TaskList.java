package org.fx.model;

import java.util.ArrayList;

public class TaskList extends ArrayList {

    public TaskList() {
        super();
    }

    public TaskList(Task task) {
        super();
        this.add(task);
    }
}
