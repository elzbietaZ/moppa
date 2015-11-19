package org.moppa.MoppaAPI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.Authentication;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

public class AuthenticationTest extends JerseyTest {

  Authentication testedClass = new Authentication();

  public static final String existingUsername = "goodUser";
  public static final String existingPassword = "goodPass";
  public static final String notExistingUsername = "randomName";
  public static final String notExistingPassword = "randomPass";

  @Override
  protected Application configure() {
    return new ResourceConfig(Authentication.class);
  }

  @Test
  public void shouldLogin() {
    Entity<User> userEntity = Entity.entity(new User(existingUsername, existingPassword),
        MediaType.APPLICATION_JSON);
    Response response = target("v1/login").request().post(userEntity);
    Assert.assertEquals(200, response.getStatus());
  }

  @Test
  public void shouldNotLogin() {
    Entity<User> userEntityBadPass = Entity.entity(new User(existingUsername, notExistingPassword),
        MediaType.APPLICATION_JSON);
    Response responseBadPass = target("v1/login").request().post(userEntityBadPass);
    Assert.assertEquals(401, responseBadPass.getStatus());

    Entity<User> userEntityBadUsername = Entity
        .entity(new User(notExistingUsername, existingPassword), MediaType.APPLICATION_JSON);
    Response responseBadUsername = target("v1/login").request().post(userEntityBadUsername);
    Assert.assertEquals(401, responseBadUsername.getStatus());

    Entity<User> userEntityBad = Entity.entity(new User(notExistingUsername, notExistingPassword),
        MediaType.APPLICATION_JSON);
    Response responseBad = target("v1/login").request().post(userEntityBad);
    Assert.assertEquals(401, responseBad.getStatus());
  }

}
