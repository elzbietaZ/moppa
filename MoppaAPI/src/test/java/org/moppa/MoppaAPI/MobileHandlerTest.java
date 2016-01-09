package org.moppa.MoppaAPI;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.moppa.MoppaAPI.MobileAPI.MobileHandler;
import org.moppa.MoppaCore.model.Task;

public class MobileHandlerTest extends JerseyTest {

  MobileHandler testedClass = new MobileHandler();
  public static final int correctNValue = 5;
  public static final String correctDeviceId = "1234567890123456";
  public static final String incorrectDeviceId = "";
  public static final int correctResult = 123123;
  public static final int incorrectResult = 0;
  public static final Task correctTask = new Task(12, correctNValue);

  @Override
  protected Application configure() {
    return new ResourceConfig(MobileHandler.class);
  }

  @Test
  public void retrieveTaskWithValidDeviceId() {
    final Response response = target("v1/tasks/retrieveTask")
        .queryParam("deviceId", correctDeviceId).request(MediaType.APPLICATION_JSON_TYPE).get();
    assertEquals(response.getStatus(), 200);
  }

  @Test
  public void retrieveTaskWithInvalidDeviceId() {
    final Response response = target("v1/tasks/retrieveTask")
    	.queryParam("deviceId", incorrectDeviceId).request(MediaType.APPLICATION_JSON_TYPE).get();
    assertEquals(response.getStatus(), 400);
  }

  @Test
  public void saveResultTaskSuccessfully() {
    Entity<Integer> resultEntity = Entity.entity(correctResult, MediaType.APPLICATION_JSON);
    Response response = target("v1/tasks/saveResultTask/" + correctDeviceId).request().post(resultEntity);
    Assert.assertEquals(200, response.getStatus());
  }

  @Test
  public void saveResultTaskFailedBecauseResultEmpty() {
    Entity<Integer> resultEntity = Entity.entity(incorrectResult, MediaType.APPLICATION_JSON);
    Response response = target("v1/tasks/saveResultTask/" + correctDeviceId).request().post(resultEntity);
    Assert.assertEquals(400, response.getStatus());
  }

  @Test
  public void saveResultTaskFailedBecauseDeviceIdIncorrect() {
    Entity<Integer> resultEntity = Entity.entity(correctResult, MediaType.APPLICATION_JSON);
    Response response = target("v1/tasks/saveResultTask/" + incorrectDeviceId).request().post(resultEntity);
    Assert.assertEquals(403, response.getStatus());
  }
}
