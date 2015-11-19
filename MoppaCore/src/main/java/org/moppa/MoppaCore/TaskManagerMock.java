package org.moppa.MoppaCore;

import java.util.Random;

import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;

public class TaskManagerMock implements TaskManager {
	public Task createTask(Integer n, long userId) {
		sleep(1000);
		return new Task(1, n);
	}
	
    public Task checkTask(long taskId) {
    	//when we will have DB, here we should find task with given taskId
    	//for now we just do it in new task 
    	sleep(1000);
    	Task task = new Task(1, 2);
    	int randNumber = randInt(0,2);
    	if( randNumber == 0) {
    		task.setStatus(Status.IN_PROGRESS);
    	} else {
    		if (randNumber == 1 ) {
    			task.setStatus(Status.PENDING);
    		}
    		else {
    			task.setStatus(Status.DONE);
    		}
    	}
        		
    	return task;
    }
    
    public Task tryGetTask(long deviceId) {
    	Task task = new Task(1, 2);
    	
    	if (randInt(0,1) == 0) {
    		task.setDeviceId(deviceId);
    		task.setStatus(Status.IN_PROGRESS);
    		return task;
    	}
    	else
    		return null;
    }
    
    public boolean saveTaskResult(long deviceId, String result) {
    	/*
    	 * When we will have DB, here we should find task with given deviceId 
    	 * and change status and result.
    	 * 
    	 * For now we just do it in new task to show, what exactly should we change
    	*/
    	Task task = new Task(1, 2);
    	task.setResult(result);
    	task.setStatus(Status.DONE);
    	return true;
    }
    
    private int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    // Just for emulation database request
    private void sleep(int ms) {
    	try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
