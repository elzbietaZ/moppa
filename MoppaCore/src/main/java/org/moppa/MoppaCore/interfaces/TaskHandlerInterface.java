package org.moppa.MoppaCore.interfaces;

import org.moppa.MoppaCore.model.Task;

public interface TaskHandlerInterface {
	
	public Task createTask (Task task);
	public boolean isTaskValid(Task task);
	public Task getTask(long taskId);

}
