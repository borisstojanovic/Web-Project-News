package rs.raf.Web_Project.resources.read;

import rs.raf.Web_Project.entities.Tag;
import rs.raf.Web_Project.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tags")
public class TagResource {
    @Inject
    private TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> all() { return this.tagService.all(); }

    @GET
    @Path("/news/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allForNews(@PathParam("id") Integer id) { return this.tagService.allForNews(id); }
}
