package se.allfader.geneseq.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.smallrye.mutiny.Uni;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@ApplicationPath("/api")
public class RestApiApplication extends Application {

    public static class ExceptionMappers {

        @ServerExceptionMapper
        public Uni<RestResponse<ErrorMessage>> constraintViolation(ConstraintViolationException error) {
            return Uni.createFrom().item(RestResponse
                    .status(RestResponse.Status.CONFLICT,
                            new ErrorMessage(RestResponse.Status.CONFLICT, error.getMessage())));
        }

        @ServerExceptionMapper
        public Uni<RestResponse<ErrorMessage>> illegalArgument(IllegalArgumentException error) {
            return Uni.createFrom().item(RestResponse
                    .status(RestResponse.Status.BAD_REQUEST,
                            new ErrorMessage(RestResponse.Status.BAD_REQUEST, error.getMessage())));
        }
    }
    public record ErrorMessage(
            @JsonIgnore
                    RestResponse.Status status,
            String message) {

        public int getCode(){
            return this.status.getStatusCode();
        }
        public String getReason() {
            return this.status.getReasonPhrase();
        }
    }
}
