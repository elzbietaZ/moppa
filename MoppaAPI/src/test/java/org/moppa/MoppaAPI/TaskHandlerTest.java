package org.moppa.MoppaAPI;

import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import org.moppa.MoppaCore.model.Status;
import org.moppa.MoppaCore.model.Task;
import org.junit.Assert;

import javax.ws.rs.core.Response;

public class TaskHandlerTest {

  TasksHandler testedClass = new TasksHandler();
  public static final int correctNValue = 5;
  public static final int incorrectNValue = 150;
  public static final int incorrectMinusNValue = -5;
  public static final long existingUserId = 1;
  public static final long fakeUserId = 151900;
  public static final Task correctTask = new Task(existingUserId, correctNValue);
  public static final Task minCorrectTask = new Task(existingUserId, 1);
  public static final Task maxCorrectTask = new Task(existingUserId, 100);
  public static final Task incorrectTaskWithTooBigN = new Task(existingUserId, incorrectNValue);
  public static final Task incorrectTaskWithMinus = new Task(existingUserId, incorrectMinusNValue);

  @Test
  public void shouldReturnTheTask() {
    Response response = testedClass.getTask(1);
    Assert.assertEquals(response.getStatus(), 200);
  }

  @Test
  public void shouldReturnTheError() {
    Response response = testedClass.getTask(1000);
    Assert.assertEquals(response.getStatus(), 400);
  }

  @Test
  public void shouldCreateTheTask() {
    Response response = testedClass.createTask(correctTask);
    Response responseForMin = testedClass.createTask(minCorrectTask);
    Response responseForMax = testedClass.createTask(maxCorrectTask);

    Assert.assertEquals(response.getStatus(), 200);
    Assert.assertEquals(responseForMin.getStatus(), 200);
    Assert.assertEquals(responseForMax.getStatus(), 200);
  }

  @Test
  public void shouldNotCreateTheTask() {
    Response responseForTaskWithMinusN = testedClass.createTask(incorrectTaskWithMinus);
    Response responseForTaskWithTooBigN = testedClass.createTask(incorrectTaskWithTooBigN);

    Assert.assertEquals(responseForTaskWithMinusN.getStatus(), 400);
    Assert.assertEquals(responseForTaskWithTooBigN.getStatus(), 400);
  }

  @Test
  public void shouldReturnTheTasks() {
    Response response = testedClass.getUserTasks(existingUserId);
    System.out.println(response.getEntity().toString());
    Assert.assertEquals(response.getStatus(), 200);
  }
  
  @Test
  public void shouldNotReturnTheTasks() {
    Response response = testedClass.getUserTasks(fakeUserId);
    Assert.assertEquals(response.getStatus(), 400);
  }

}
