package rs.raf.Web_Project.resources.cms;

import rs.raf.Web_Project.entities.Category;
import rs.raf.Web_Project.entities.Comment;
import rs.raf.Web_Project.entities.User;
import rs.raf.Web_Project.requests.LoginRequest;
import rs.raf.Web_Project.response.PageResponse;
import rs.raf.Web_Project.response.RestError;
import rs.raf.Web_Project.services.CommentService;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid User user) {
        try {
            User toReturn = this.userService.create(user);
            if(toReturn != null) {
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Email already exists!")).build();
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.status(404).entity(new RestError(404, "Email already exists!")).build();
        }
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, Object> response = new HashMap<>();

        String loginResponse = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (loginResponse == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        if(loginResponse.equals("Inactive")){
            response.put("message", "Inactive user");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", loginResponse);
        response.put("user", this.userService.find(loginRequest.getEmail()));

        return Response.ok(response).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid User user) {
        try {
            User toReturn = this.userService.update(user);
            if(toReturn != null) {
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Email already exists!")).build();
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.status(404).entity(new RestError(404, "Email already exists!")).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@QueryParam("email") String email) {
        return this.userService.find(email);
    }

    @POST
    @Path("/deactivate/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deactivate(@PathParam("id") Integer id) {
        return this.userService.deactivate(id);
    }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginated(@QueryParam("start") int start, @QueryParam("size") int size) {
        List<User> users = this.userService.allPaginated(start, size);
        int count = this.userService.count();
        return Response.status(200).entity(new PageResponse(users, count)).build();
    }
}
