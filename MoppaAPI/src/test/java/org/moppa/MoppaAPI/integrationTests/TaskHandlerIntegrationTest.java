package org.moppa.MoppaAPI.integrationTests;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.moppa.MoppaCore.model.User;

import org.junit.Assert;

public class TaskHandlerIntegrationTest extends JerseyTest {

  Session session = HibernateUtil.getSessionFactory().openSession();
  User user;
  Task task;

  @Override
  protected Application configure() {
    return new ResourceConfig(TasksHandler.class);
  }

  @Before
  public void insertTheValuesToDatabase() {
    user = new User("gooduser", "goodpass");
    task = new Task(user, 5);
    session.beginTransaction();
    session.save(user);
    session.save(task);
    session.getTransaction().commit();
  }
  
  @After
  public void removeTheValuesFromDatabase() {
    session.beginTransaction();
    session.delete(task);
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

  
}
