package org.moppa.MoppaAPI.integrationTests;

import java.util.ArrayList;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.implementation.TaskHandlerImpl;
import org.moppa.MoppaCore.interfaces.TaskHandlerInterface;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

import org.junit.Assert;

public class TaskHandlerIntegrationTest extends JerseyTest {
  
  TaskHandlerInterface taskHandler = new TaskHandlerImpl();


  private Session session = HibernateUtil.getSessionFactory().openSession();
  private User user;
  private User userNew;
  private Task task;
  private Task task2;
  private Task correctTask;

  @Override
  protected Application configure() {
    return new ResourceConfig(TasksHandler.class);
  }

  @Before
  public void insertTheValuesToDatabase() {
    user = new User("gooduser", "goodpass");
    userNew = new User("newUser", "newPass");
    task = new Task(user, 5);
    task2 = new Task(user, 6);
    session.beginTransaction();
    session.save(user);
    session.save(userNew);
    session.save(task);
    session.save(task2);
    session.getTransaction().commit();
  }

  @After
  public void removeTheValuesFromDatabase() {
    session.beginTransaction();
    session.delete(task);
    session.delete(task2);
    session.delete(user);
    session.getTransaction().commit();
  }

  @Test
  public void shouldReturnTheTask() {
    Response response = target("v1/tasks/" + task.getTaskId()).request().get();
    Task entity = response.readEntity(Task.class);
    Assert.assertEquals(task.getTaskId(), entity.getTaskId());
    Assert.assertEquals(user.getUserId(), entity.getUser().getUserId());
    Assert.assertEquals(5, entity.getnValue());
    Assert.assertEquals(Status.PENDING, entity.getStatus());
    Assert.assertEquals("?", entity.getResult());

  }

  @Test
  public void shouldReturnTheTasks() {
    Response response = target("v1/tasks/user/" + user.getUserId()).request().get();
    JsonObject entity = response.readEntity(JsonObject.class);
    JsonArray tasksArray = (JsonArray) entity.get("tasks");
    Assert.assertEquals(2, tasksArray.size());
    Assert.assertEquals(task.getTaskId() + "",
        tasksArray.getJsonObject(0).get("taskId").toString());
    Assert.assertEquals(task2.getTaskId() + "",
        tasksArray.getJsonObject(1).get("taskId").toString());
  }

  @Test
  public void shouldCreateTheTask() {
    correctTask= new Task (userNew, 7);
    Entity<Task> taskEntity = Entity.entity(correctTask, MediaType.APPLICATION_JSON);
    Response response = target("v1/tasks/create").request().post(taskEntity);
    Assert.assertEquals(200, response.getStatus());
    JsonObject entity = response.readEntity(JsonObject.class);
    String taskId = entity.get("taskId").toString();
    
    long taskIdLong = Long.parseLong(taskId);
    Task task = taskHandler.getTask(taskIdLong);
    Assert.assertEquals(7, task.getnValue());
    Assert.assertEquals("?", task.getResult());
    Assert.assertEquals(Status.PENDING, task.getStatus());

  }
}
