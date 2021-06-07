package rs.raf.Web_Project.resources.read;

import rs.raf.Web_Project.entities.Comment;
import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.response.PageResponse;
import rs.raf.Web_Project.response.RestError;
import rs.raf.Web_Project.services.CommentService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
public class CommentResource {
    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Comment comment) {
        try{
            comment = this.commentService.addComment(comment);
            return Response.status(200).entity(comment).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(404).entity(new RestError(404, "Validation failed - All fields are required")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> find(@PathParam("id") Integer id) {
        return this.commentService.allCommentsForPost(id);
    }

    @GET
    @Path("/page/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginatedForCategory(@PathParam("id") Integer id, @QueryParam("start") int start, @QueryParam("size") int size) {
        List<Comment> news = this.commentService.allPaginated(start, size, id);
        int count = this.commentService.count(id);
        return Response.status(200).entity(new PageResponse(news, count)).build();
    }
}
