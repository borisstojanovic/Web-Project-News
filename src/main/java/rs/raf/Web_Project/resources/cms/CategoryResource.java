package rs.raf.Web_Project.resources.cms;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Category category) {
        try {
            Category toReturn = this.categoryService.add(category);
            if (toReturn != null) {
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Name already exists!")).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Name already exists!")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id){
        boolean deleted = this.categoryService.remove(id);
        if(deleted) {
            return Response.accepted().build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity(new RestError(Response.Status.FORBIDDEN.getStatusCode(),
                    "Category contains news!")).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid Category category){
        try {
            Category toReturn = this.categoryService.update(category);
            if(toReturn != null) {
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Name already exists!")).build();
        }
        catch(Exception e){
            return Response.status(404).entity(new RestError(404, "Name already exists!")).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> all() { return this.categoryService.all(); }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginated(@QueryParam("start") int start, @QueryParam("size") int size) {
        List<Category> categories = this.categoryService.allPaginated(start, size);
        int count = this.categoryService.count();
        return Response.status(200).entity(new PageResponse(categories, count)).build();
    }

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category find(@PathParam("id") Integer id) { return this.categoryService.find(id); }
}
