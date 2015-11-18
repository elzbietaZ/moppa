package org.moppa.MoppaAPI;

import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.junit.Assert;

import javax.ws.rs.core.Response;

public class TaskHandlerTest {

  TasksHandler testedClass = new TasksHandler();
  public final static int correctNValue = 5;
  public final static int incorrectNValue = 15;
  public final static int incorrectMinusNValue = -5;
  public final static long existingUserId = 1;
  Task correctTask=new Task(existingUserId,correctNValue);


  @Test
  public void shouldReturnTheTask() {
    Response response = testedClass.getTask(1);
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void shouldReturnTheError() {
    Response response = testedClass.getTask(1000);
    Assert.assertEquals(response.getStatus(), 500);
  }

  @Test
  public void shouldCreateTheTask(){
    Response response = testedClass.createTask(correctTask);
    Assert.assertEquals(response.getStatus(), 200);

  }
}
