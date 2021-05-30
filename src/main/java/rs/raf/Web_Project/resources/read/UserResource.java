package rs.raf.Web_Project.resources.read;


import rs.raf.Web_Project.entities.User;
import rs.raf.Web_Project.requests.LoginRequest;
import rs.raf.Web_Project.response.PageResponse;
import rs.raf.Web_Project.response.RestError;
import rs.raf.Web_Project.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") Integer id) {
        return this.userService.findById(id);
    }
}

