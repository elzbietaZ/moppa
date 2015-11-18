package org.moppa.MoppaAPI;

import org.junit.Test;
import org.moppa.MoppaAPI.CustomerAPI.TasksHandler;
import javax.ws.rs.core.Response;

public class TaskHandlerTest {

  @Test
  public void shouldReturnTheTask() {
    TasksHandler testedClass = new TasksHandler();
    Response response = testedClass.getTask(1);
    System.out.println(response);
  }
}
