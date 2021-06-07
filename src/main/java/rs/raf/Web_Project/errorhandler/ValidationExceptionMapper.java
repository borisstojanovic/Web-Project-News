package rs.raf.Web_Project.errorhandler;

import rs.raf.Web_Project.response.RestError;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        if (e != null) {
            return Response.status(404).entity(new RestError(404, "Validation failed - All fields are required")).build();
        }

        // Default
        return Response.status(500).entity(new RestError(500, "Server error")).build();
    }
}
