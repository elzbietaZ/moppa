package org.moppa.MoppaAPI.CustomerAPI;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.moppa.MoppaAPI.CustomerAPI.Model.User;


@Path("v1/login")
@Api(value = "v1/login", description = "Login to webpage")
public class Authentication {
  

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Login to webpage", notes = "User should be loged to use the website")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
  @ApiResponse(code = 500, message = "Something wrong in Server") })
  public Response login(@ApiParam(value = "User data")User user) {

    JsonObject value = Json.createObjectBuilder().add("login", user.getUsername()).build();
    return Response.status(200).entity(value).build();

  }
}
