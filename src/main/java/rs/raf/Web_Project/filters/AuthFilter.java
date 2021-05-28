package rs.raf.Web_Project.filters;

import rs.raf.Web_Project.resources.CategoryResource;
import rs.raf.Web_Project.resources.CommentResource;
import rs.raf.Web_Project.resources.NewsResource;
import rs.raf.Web_Project.resources.UserResource;
import rs.raf.Web_Project.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        String token = requestContext.getHeaderString("authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }

        if(this.isAdminRequired(requestContext) && !this.userService.isAdmin(token)){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        if (!this.userService.isAuthorized(token)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAdminRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource) {
                return true;
            }
        }

        return false;
    }

    //todo mozda u url da dodam /auth ili /admin da ne bih prolazio kroz listu resursa
    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource || matchedResource instanceof CategoryResource
                    || matchedResource instanceof NewsResource) {
                return true;
            }
        }

        return false;
    }
}
