package org.moppa.MoppaCore.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.interfaces.TaskHandlerInterface;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

public class TaskHandlerImpl implements TaskHandlerInterface {

	Session session = HibernateUtil.getSessionFactory().openSession();

	public boolean isTaskValid(Task task) {
		if (task.getnValue() < 1 || task.getnValue() > 100) {
			return false;
		}
		if (task.getUser() == null || task.getUser().getUserId() == 0) {
			return false;
		}

		return true;
	}

	public Task createTask(Task task) {

		task = new Task(task.getUser(), task.getnValue());
		session.beginTransaction();
		session.save(task);
		session.getTransaction().commit();
		return task;
	}

	public Task getTask(long taskId) {
		return (Task) session.get(Task.class, taskId);
	}

	public List<Task> getUserTasks(long userId) {

		User user = (User) session.get(User.class, userId);
		if (user != null) {
			Query hqlQuery = session.createQuery("From Task where user.id = ?");
			@SuppressWarnings("unchecked")
			List<Task> results = hqlQuery.setLong(0, userId).list();
			return results;
		}
		return null;

	}

	// methods for mobile api
	
	public Task saveResult(Task task) {
		
		session.beginTransaction();
		Task t= (Task) session.get(Task.class, task.getTaskId());
		t.setResult(task.getResult());
		t.setStatus(Status.DONE);
		session.getTransaction().commit();
		return t;
		
	}

	public Task retrieveTask(String deviceId) {
		
		Query hqlQuery = session.createQuery("From Task where deviceId = ?");
		List<Task> results = hqlQuery.setString(0, "").list();
		if(results.size()>0){
			session.beginTransaction();
			Task task=results.get(0);
			task.setDeviceId(deviceId);
			session.getTransaction().commit();
			return task;
		}
		else{
			return null;
		}
	}

}
