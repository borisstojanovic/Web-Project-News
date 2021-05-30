package rs.raf.Web_Project.resources.read;

import rs.raf.Web_Project.entities.Category;
import rs.raf.Web_Project.response.PageResponse;
import rs.raf.Web_Project.response.RestError;
import rs.raf.Web_Project.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
public class CategoryResource {
    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> all() { return this.categoryService.all(); }

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category find(@PathParam("id") Integer id) { return this.categoryService.find(id); }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginated(@QueryParam("start") int start, @QueryParam("size") int size) {
        List<Category> categories = this.categoryService.allPaginated(start, size);
        int count = this.categoryService.count();
        return Response.status(200).entity(new PageResponse(categories, count)).build();
    }
}
