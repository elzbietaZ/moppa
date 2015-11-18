package org.moppa.MoppaCore.implementation;

import org.hibernate.Session;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.interfaces.TaskHandlerInterface;
import org.moppa.MoppaCore.model.Task;

public class TaskHandlerImpl implements TaskHandlerInterface {
	

	public Task createTask(Task task) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();		  
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
		return task;
	}

}
