package org.moppa.MoppaAPI.MobileAPI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("v1/tasks")
@Api(value = "v1/tasks", description = "Handle with tasks")
public class MobileHandler {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("getTask")
  @ApiOperation(value = "Get the task")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Something wrong in server") })
  public Response getTask() {

    // example response
    JsonObject value = Json.createObjectBuilder().add("taskId", "100500").
    		add("status", "in progress").build();
    return Response.status(200).entity(value).build();   
  }
  
  @POST
  @Path("saveResultTask")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Save result of the task after calculation")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK, result was got"),
      @ApiResponse(code = 500, message = "Something wrong in the server") })
  public Response saveResultTask( String result) {

    // example response
    JsonObject value = Json.createObjectBuilder().add("result", "120").build();
    return Response.status(200).entity(value).build();
  }

}
