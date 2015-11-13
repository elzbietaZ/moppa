package org.moppa.MoppaCore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Task {

	@Id
	@GeneratedValue
	private long taskId;
	private long userId;
	private int nValue;
	
	

	public Task(long userId, int nValue) {
		super();
		this.userId = userId;
		this.nValue = nValue;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public int getnValue() {
		return nValue;
	}

	public void setnValue(int nValue) {
		this.nValue = nValue;
	}
	
	

}
