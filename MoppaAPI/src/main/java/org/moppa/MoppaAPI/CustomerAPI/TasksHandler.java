package org.moppa.MoppaAPI.CustomerAPI;

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

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("v1/tasks")
@Api(value = "v1/tasks", description = "Handle with tasks")
public class TasksHandler {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{taskId}")
  @ApiOperation(value = "Get the task")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Something wrong in server") })
  public Response getTask(@ApiParam(value = "ID of the task") @PathParam("taskId") int taskId) {

    // example response
    JsonObject value = Json.createObjectBuilder().add("taskId", taskId).add("status", "in progress")
        .add("result", "?").build();
    return Response.status(200).entity(value).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("user/{userId}")
  @ApiOperation(value = "Get all the tasks for the user")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Something wrong in server") })
  public Response getUserTasks(
      @ApiParam(value = "ID of the user") @PathParam("userId") String userId) {

    // example response
    JsonObject value = Json.createObjectBuilder()
        .add("tasks",
            Json.createObjectBuilder().add("taskId", 54).add("status", "done").add("result", "24"))
        .build();
    return Response.status(200).entity(value).build();
  }

  @POST
  @Path("create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create new task", 
                notes = "User send to the server the number to calculate")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK, the task was succesfuly created"),
      @ApiResponse(code = 500, message = "Something wrong in the server") })
  public Response createTask(@ApiParam(value = "Task to create on server") Task task) {

    TaskHandlerInterface taskHandler = new TaskHandlerImpl();
    task=taskHandler.createTask(task);
    JsonObject value = Json.createObjectBuilder().add("Added task with id", task.getTaskId()).build();
    return Response.status(200).entity(value).build();

  }

}
