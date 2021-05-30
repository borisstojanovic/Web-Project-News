package rs.raf.Web_Project.resources.cms;

import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.response.PageResponse;
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
    public Response add(@Valid News news) {
        try{
            News toReturn = this.newsService.add(news);
            if(toReturn != null){
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Save not successful")).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Save not successful")).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid News news) {
        try{
            News toReturn = this.newsService.update(news);
            if(toReturn != null){
                return Response.accepted().entity(toReturn).build();
            }
            return Response.status(404).entity(new RestError(404, "Save not successful")).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Save not successful")).build();
        }
    }

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
    @Path("/tag/page/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginatedforTag(@PathParam("id") Integer id, @QueryParam("start") int start, @QueryParam("size") int size) {
        try{
            List<News> news = this.newsService.allPaginatedForTag(id, start, size);
            int count = this.newsService.countForTag(id);
            return Response.status(200).entity(new PageResponse(news, count)).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Pagination error")).build();
        }

    }

    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allForCategory(@PathParam("id") Integer id) {
        return this.newsService.allForCategory(id);
    }

    @GET
    @Path("/category/page/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginatedForCategory(@PathParam("id") Integer id, @QueryParam("start") int start, @QueryParam("size") int size) {
        List<News> news = this.newsService.allPaginatedForCategory(id, start, size);
        int count = this.newsService.countForCategory(id);
        return Response.status(200).entity(new PageResponse(news, count)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all() {
        return this.newsService.all();
    }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginated(@QueryParam("start") int start, @QueryParam("size") int size) {
        List<News> news = this.newsService.allPaginated(start, size);
        int count = this.newsService.count();
        return Response.status(200).entity(new PageResponse(news, count)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.find(id);
    }
}
