package org.moppa.MoppaAPI.CustomerAPI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("task")
@Api(value = "/task", description = "Handle with tasks")
public class TaskHandler {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get the task")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Something wrong in Server")})
  public Response getTask() {
    
      JsonObject value = Json.createObjectBuilder()
              .add("taskId", "12")
              .build();
      return Response.status(200).entity(value).build();
  }

}
