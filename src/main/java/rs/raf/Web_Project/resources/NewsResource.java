package rs.raf.Web_Project.resources;

import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.response.RestError;
import rs.raf.Web_Project.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/news")
public class NewsResource {
    @Inject
    private NewsService newsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News add(@Valid News news) { return this.newsService.add(news); }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public News update(@Valid News news) { return this.newsService.update(news); }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Integer id) {
        try{
            this.newsService.remove(id);
            return Response.accepted().build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Couldn't delete news")).build();
        }
    }

    @GET
    @Path("/tag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allForTag(@PathParam("id") Integer id) {
        return this.newsService.allForTag(id);
    }

    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allForCategory(@PathParam("id") Integer id) {
        return this.newsService.allForCategory(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all() {
        return this.newsService.all();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.find(id);
    }
}
