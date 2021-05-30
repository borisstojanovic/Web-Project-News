package rs.raf.Web_Project.resources.read;

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

    @PUT
    @Path("/views/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response incrementViews(@PathParam("id") Integer id) {
        try{
            boolean incremented = this.newsService.incrementViews(id);
            if(incremented) {
                return Response.status(200).build();
            }else{
                return Response.status(500).entity(new RestError(500, "Increment failed")).build();
            }
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Pagination error")).build();
        }
    }

    @GET
    @Path("/mostViewed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostViewed(@QueryParam("start") int start, @QueryParam("size") int size) {
        try{
            List<News> news = this.newsService.mostViewed(start, size);
            int count = this.newsService.count();
            return Response.status(200).entity(new PageResponse(news, count)).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Pagination error")).build();
        }
    }

    @GET
    @Path("/tag/page/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPaginatedForTag(@PathParam("id") Integer id, @QueryParam("start") int start, @QueryParam("size") int size) {
        try{
            List<News> news = this.newsService.allPaginatedForTag(id, start, size);
            int count = this.newsService.countForTag(id);
            return Response.status(200).entity(new PageResponse(news, count)).build();
        }catch (Exception e){
            return Response.status(404).entity(new RestError(404, "Pagination error")).build();
        }

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
    @Path("/newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allNewest(@QueryParam("start") int start, @QueryParam("size") int size) {
        List<News> news = this.newsService.allNewest(start, size);
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
