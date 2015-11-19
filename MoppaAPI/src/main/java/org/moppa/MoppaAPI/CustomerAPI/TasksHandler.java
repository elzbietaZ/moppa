package org.moppa.MoppaAPI.CustomerAPI;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.moppa.MoppaCore.implementation.TaskHandlerImpl;
import org.moppa.MoppaCore.interfaces.TaskHandlerInterface;
import org.moppa.MoppaCore.model.Task;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("v1/tasks")
@Api(value = "v1/tasks", description = "Handle with tasks")
public class TasksHandler {

  TaskHandlerInterface taskHandler = new TaskHandlerImpl();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{taskId}")
  @ApiOperation(value = "Get the task")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "No task with given id"),
      @ApiResponse(code = 404, message = "Unallowed parameters for request"),
      @ApiResponse(code = 500, message = "Something wrong in server") })
  public Response getTask(@ApiParam(value = "Id of the task") @PathParam("taskId") long taskId) {

    Task task = taskHandler.getTask(taskId);
    if (task != null) {
      JsonObject value = Json.createObjectBuilder().add("taskId", task.getTaskId())
          .add("userId", task.getUser().getUserId()).add("nValue", task.getnValue())
          .add("result", task.getResult()).add("status", task.getStatus().toString())
          .add("deviceId", task.getDeviceId()).build();
      return Response.status(Status.OK).entity(value).build();
    } else {
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("user/{userId}")
  @ApiOperation(value = "Get all the tasks for the user")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad parameters of request"),
      @ApiResponse(code = 404, message = "Unallowed parameters for request"),
      @ApiResponse(code = 500, message = "Something wrong in server") })
  public Response getUserTasks(
      @ApiParam(value = "ID of the user") @PathParam("userId") long userId) {

    List<Task> taskslist = taskHandler.getUserTasks(userId);

    if (taskslist != null) {
      JsonObject value = Json.createObjectBuilder().add("tasks",
          Json.createObjectBuilder().add("taskId", 54).add("status", "done").add("result", "24"))
          .build();
      System.out.println(taskslist.toString());
      return Response.status(Status.OK).entity(value).build();
    } else {
      return Response.status(Status.BAD_REQUEST).build();
    }

  }

  @POST
  @Path("create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create new task", notes = "User send to the server the number to calculate")
  @ApiResponses(value = { 
      @ApiResponse(code = 200, message = "OK, the task was succesfuly created"),
      @ApiResponse(code = 500, message = "Something wrong in the server"),
      @ApiResponse(code = 400, message = "Bad parameters of request") })
  public Response createTask(@ApiParam(value = "Task to create on server") Task task) {

    if (taskHandler.isTaskValid(task)) {
      task = taskHandler.createTask(task);
      JsonObject value = Json.createObjectBuilder().add("Added task with id", task.getTaskId())
          .build();
      return Response.status(Status.OK).entity(value).build();
    } else {
      return Response.status(Status.BAD_REQUEST).build();
    }

  }

}
