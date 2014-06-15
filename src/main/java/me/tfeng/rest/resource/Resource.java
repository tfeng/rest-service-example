package me.tfeng.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

  @Autowired
  private AsyncHandler asyncHandler;

  @GET
  @Path("/async")
  public void async(@Suspended AsyncResponse response) {
    asyncHandler.handle(response);
  }

  @GET
  @Path("/sync")
  public String sync() {
    return "Hello (synchronized)!";
  }
}
