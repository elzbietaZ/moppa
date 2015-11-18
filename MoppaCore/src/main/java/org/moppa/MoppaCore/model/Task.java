package org.moppa.MoppaCore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Task {

	@Id
	@GeneratedValue
	private long taskId;
	
	@ManyToOne
	private User user;
	private int nValue;
	private int result;
	private Status status;
	
	

	public Task(User user, int nValue) {
		super();
		this.user = user;
		this.nValue = nValue;
		this.result = 0;
		this.status = Status.PENDING;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user= user;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	
	

}
