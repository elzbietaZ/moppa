package org.moppa.MoppaAPI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.Authentication;
import org.moppa.MoppaCore.model.User;

public class AuthenticationTest {

  Authentication testedClass = new Authentication();

  public static final String existingUsername = "Ela";
  public static final String existingPassword = "pass";
  public static final String notExistingUsername = "Devil";
  public static final String notExistingPassword = "hell";

  @Test
  public void shouldLogin() {
    Response response = testedClass.login(new User(existingUsername, existingPassword));
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void shouldNotLogin() {
    Response response = testedClass.login(new User(notExistingUsername, notExistingPassword));
    Assert.assertEquals(response.getStatus(), 401);
  }

}
