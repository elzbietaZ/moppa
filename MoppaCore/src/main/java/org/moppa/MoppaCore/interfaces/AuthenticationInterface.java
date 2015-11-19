package org.moppa.MoppaCore.interfaces;

import org.moppa.MoppaCore.model.User;

public interface AuthenticationInterface {
	
	public User login (String username, String password);

}
