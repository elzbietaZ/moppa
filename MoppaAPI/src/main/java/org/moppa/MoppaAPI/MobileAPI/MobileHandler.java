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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("retrieveTask")
  @ApiOperation(value = "Retrieve the task")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK, the task was got"),
      @ApiResponse(code = 400, message = "Invalid value of deviceId") })
  public Response retrieveTask(
      @ApiParam(value = "Id of device") @QueryParam("deviceId") String deviceId) {
    // deviceId should be a positive number
    if  ( deviceId.isEmpty() || deviceId == null ) {
      return Response.status(400).build();
    } else {
      JsonObject value = Json.createObjectBuilder().add("taskId", "1234").add("nValue", 5)
          .add("deviceId", "1234567890123456").add("status", "in progress").build();
      return Response.status(200).entity(value).build();
    }
  }

  @POST
  @Path("saveResultTask/{deviceId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Save result of the task after calculation")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK, result was got"),
      @ApiResponse(code = 400, message = "Saving was failed") })
  public Response saveResultTask(
      @ApiParam(value = "Id of device") @PathParam("deviceId") String deviceId,
      @ApiParam(value = "Result of task") String result) {
    if ( deviceId != null && !deviceId.isEmpty() && result != null && !result.isEmpty() ) {
      JsonObject value = Json.createObjectBuilder().add("result", "120").add("deviceId", "100500")
          .build();
      return Response.status(200).entity(value).build();
    } else {
      return Response.status(400).build();
    }
  }
//MobileHandler close
}
