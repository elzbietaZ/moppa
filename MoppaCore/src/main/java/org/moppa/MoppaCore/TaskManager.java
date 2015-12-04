package org.moppa.MoppaCore;

import org.moppa.MoppaCore.model.Task;

public interface TaskManager {
	
	public Task createTask(Integer n, long userId);
    public Task checkTask(long taskId);
    public Task tryGetTask(String deviceId);
    public boolean saveTaskResult(String deviceId, String result);
}
