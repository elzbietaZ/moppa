package org.moppa.MoppaCore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.moppa.MoppaCore.TaskStatusValues;

@Entity
@Table
public class Task {

	@Id
	@GeneratedValue
	private long taskId;
	
	@ManyToOne
	private User user;
	private int nValue;
	private String result;
	private long deviceId;
	private String status;
	

	public Task(User user, int nValue) {
		super();
		this.user = user;
		this.nValue = nValue;
		this.deviceId = 0;
		this.result = null;
		this.status = TaskStatusValues.PENDING;	
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
