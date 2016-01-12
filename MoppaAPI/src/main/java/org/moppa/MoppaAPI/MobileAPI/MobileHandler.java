package org.moppa.MoppaAPI.MobileAPI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

//for deviceId
//import android.provider.Settings.Secure;
//private String android_id = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID); 
@Path("v1/tasks")
@Api(value = "v1/tasks", description = "Handle with tasks")
public class MobileHandler {

	TaskHandlerInterface taskHandler = new TaskHandlerImpl();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("retrieveTask")
	@ApiOperation(value = "Retrieve the task")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK, the task was obtained"),
			@ApiResponse(code = 400, message = "Invalid value of deviceId") })
	public Response retrieveTask(@ApiParam(value = "Id of device") @QueryParam("deviceId") String deviceId) {
		// deviceId should be a positive number
		if (deviceId.isEmpty() || deviceId == null) {
			return Response.status(400).build();
		} else {
			Task task = taskHandler.retrieveTask(deviceId);
			JsonObject userValue = Json.createObjectBuilder().add("userId", task.getUser().getUserId()).build();
			JsonObject value = Json.createObjectBuilder().add("taskId", task.getTaskId()).add("user", userValue)
					.add("nValue", task.getnValue()).add("result", task.getResult())
					.add("status", task.getStatus().toString()).add("deviceId", task.getDeviceId()).build();
			return Response.status(200).entity(value).build();
		}
	}

	@POST
	@Path("saveResultTask/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save result of the task after calculation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK, result was saved"),
			@ApiResponse(code = 400, message = "DeviceId is empty"),
			@ApiResponse(code = 403, message = "Result is invalid") })
	public Response saveResultTask(@ApiParam(value = "Task to save on the server") Task t) {
		String deviceId = t.getDeviceId();
		String result = t.getResult();

		if (deviceId != null && !deviceId.isEmpty()) {
			Task task = taskHandler.saveResult(t);
			JsonObject userValue = Json.createObjectBuilder().add("userId", task.getUser().getUserId()).build();
			JsonObject value = Json.createObjectBuilder().add("taskId", task.getTaskId()).add("user", userValue)
					.add("nValue", task.getnValue()).add("result", task.getResult())
					.add("status", task.getStatus().toString()).add("deviceId", task.getDeviceId()).build();
			return Response.status(200).entity(value).build();
		} else if (deviceId == null || deviceId.isEmpty()) {
			return Response.status(400).build();
		} else {
			return Response.status(403).build();
		}
	}
}
