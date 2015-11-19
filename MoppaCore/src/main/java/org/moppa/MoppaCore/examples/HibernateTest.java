package org.moppa.MoppaCore.examples;

import java.util.List;

import org.hibernate.*;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

public class HibernateTest {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		User u = new User("Ela", "pass");
		session.save(u);
		Task t = new Task(u, 4);
		t.setStatus(Status.ERROR);
		session.save(t);

		session.getTransaction().commit();
	}

}
