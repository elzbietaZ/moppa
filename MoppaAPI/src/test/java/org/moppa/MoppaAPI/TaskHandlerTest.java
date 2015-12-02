package org.moppa.MoppaAPI;

import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TaskHandlerTest extends JerseyTest {

  TasksHandler testedClass = new TasksHandler();
  static Session session = HibernateUtil.getSessionFactory().openSession();
  public static final int correctNValue = 5;
  public static final int incorrectNValue = 150;
  public static final long fakeUserId = 151900;
  public static final User goodUser = new User("gooduser", "goodpass");
  public static final Task correctTask = new Task(goodUser, correctNValue);
  public static final Task correctTask2 = new Task(goodUser, correctNValue + 1);
  public static final Task correctTask3 = new Task(goodUser, correctNValue + 2);
  public static final Task incorrectTaskWithBadUserId = new Task(fakeUserId, correctNValue);
  public static final Task minCorrectTask = new Task(goodUser, 1);
  public static final Task maxCorrectTask = new Task(goodUser, 100);
  public static final Task incorrectTaskWithTooBigN = new Task(goodUser, incorrectNValue);

  @BeforeClass
  public static void insertTheValuesToDatabase() {
    session.beginTransaction();
    session.save(goodUser);
    session.save(correctTask);
    session.save(correctTask2);
    session.getTransaction().commit();
  }

  @AfterClass
  public static void deleteTheValuesFromDatabase() {
    session.beginTransaction();
    session.delete(correctTask);
    session.delete(correctTask2);
    session.delete(correctTask3);
    session.delete(minCorrectTask);
    session.delete(maxCorrectTask);
    session.getTransaction().commit();
    session.delete(goodUser);

  }

  @Override
  protected Application configure() {
    return new ResourceConfig(TasksHandler.class);
  }

  @Test
  public void shouldReturnTheTask() {
    Response responseForBoundary = target("v1/tasks/" + correctTask.getTaskId()).request().get();
    Response responseForCorrect = target("v1/tasks/" + correctTask2.getTaskId()).request().get();
    Assert.assertEquals(200, responseForBoundary.getStatus());
    Assert.assertEquals(200, responseForCorrect.getStatus());
  }

  @Test
  public void shouldReturnTheError() {
    Response responseForBadId = target("v1/tasks/1500").request().get();
    Response responseForMalformedId = target("v1/tasks/1abc").request().get();
    Assert.assertEquals(400, responseForBadId.getStatus());
    Assert.assertEquals(404, responseForMalformedId.getStatus());

  }

  @Test
  public void shouldCreateTheTask() {
    Entity<Task> taskEntity = Entity.entity(correctTask3, MediaType.APPLICATION_JSON);
    Response response = target("v1/tasks/create").request().post(taskEntity);
    Assert.assertEquals(200, response.getStatus());

    Entity<Task> taskEntityMin = Entity.entity(minCorrectTask, MediaType.APPLICATION_JSON);
    Response responseForMin = target("v1/tasks/create").request().post(taskEntityMin);
    Assert.assertEquals(200, responseForMin.getStatus());

    Entity<Task> taskEntityMax = Entity.entity(maxCorrectTask, MediaType.APPLICATION_JSON);
    Response responseForMax = target("v1/tasks/create").request().post(taskEntityMax);
    Assert.assertEquals(200, responseForMax.getStatus());
  }

  @Test
  public void shouldNotCreateTheTaskBecauseOfnValue() {
    Entity<Task> taskEntityTooBig = Entity.entity(incorrectTaskWithTooBigN,
        MediaType.APPLICATION_JSON);
    Response responseForTaskWithTooBigN = target("v1/tasks/create").request()
        .post(taskEntityTooBig);
    Assert.assertEquals(400, responseForTaskWithTooBigN.getStatus());

    JsonObject jsonWithMalformedN = Json.createObjectBuilder()
        .add("user", Json.createObjectBuilder().add("userId", 1).build()).add("nValue", "1bb")
        .build();

    Entity<JsonObject> taskWithMalformedN = Entity.entity(jsonWithMalformedN,
        MediaType.APPLICATION_JSON);
    Response responseForTaskWithMalformedN = target("v1/tasks/create").request()
        .post(taskWithMalformedN);
    Assert.assertEquals(400, responseForTaskWithMalformedN.getStatus());

  }

  @Test
  public void shouldNotCreateTheTaskBecauseOfUserId() {
    Entity<Task> taskEntityWithBadUserId = Entity.entity(incorrectTaskWithBadUserId,
        MediaType.APPLICATION_JSON);
    Response responseForTaskWithBadUserId = target("v1/tasks/create").request()
        .post(taskEntityWithBadUserId);
    Assert.assertEquals(400, responseForTaskWithBadUserId.getStatus());

    JsonObject jsonWithMalformedUserId = Json.createObjectBuilder()
        .add("user", Json.createObjectBuilder().add("userId", "@@").build()).add("nValue", "5")
        .build();

    Entity<JsonObject> taskWithMalformedUserId = Entity.entity(jsonWithMalformedUserId,
        MediaType.APPLICATION_JSON);
    Response responseForTaskWithMalformedUserId = target("v1/tasks/create").request()
        .post(taskWithMalformedUserId);
    Assert.assertEquals(400, responseForTaskWithMalformedUserId.getStatus());

  }

  @Test
  public void shouldReturnTheTasks() {
    Response response = target("v1/tasks/user/" + goodUser.getUserId()).request().get();
    Assert.assertEquals(200, response.getStatus());
  }

  @Test
  public void shouldNotReturnTheTasks() {
    Response responseNotExisting = target("v1/tasks/user/2222222").request().get();
    Assert.assertEquals(400, responseNotExisting.getStatus());

    Response responseMalformed = target("v1/tasks/user/!@&").request().get();
    Assert.assertEquals(404, responseMalformed.getStatus());
  }

}
