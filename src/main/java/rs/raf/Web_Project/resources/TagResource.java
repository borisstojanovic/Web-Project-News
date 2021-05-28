package rs.raf.Web_Project.resources;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tag create(@Valid Tag tag) { return this.tagService.add(tag); }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Integer id){ this.tagService.remove(id) ;}

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Tag update(@Valid Tag tag){ return this.tagService.update(tag); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> all() { return this.tagService.all(); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allForNews(@PathParam("id") Integer id) { return this.tagService.allForNews(id); }
}
