package org.moppa.MoppaCore.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.interfaces.AuthenticationInterface;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

public class AuthenticationImpl implements AuthenticationInterface {
	
	Session session = HibernateUtil.getSessionFactory().openSession();


	public User login(String username, String password) {
				
		Query hqlQuery = session.createQuery("From User where username = ? and password = ?");
		hqlQuery.setString(0, username);
		hqlQuery.setString(1, password);
		List<User> result = hqlQuery.list();
		
		if(result.isEmpty()){
			return null;
		}
		else{
			return result.get(0);
		}
	}

}
