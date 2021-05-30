package rs.raf.Web_Project.resources.cms;

import rs.raf.Web_Project.entities.Comment;
import rs.raf.Web_Project.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {
    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment) { return this.commentService.addComment(comment); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> find(@PathParam("id") Integer id) {
        return this.commentService.allCommentsForPost(id);
    }
}
