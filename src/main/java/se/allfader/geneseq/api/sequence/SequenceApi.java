package se.allfader.geneseq.api.sequence;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import se.allfader.geneseq.application.feature.AddSequence;

import static se.allfader.geneseq.api.sequence.SequenceApiDocStrings.addSequenceDoc;

@Path("sequence")
@Tag(name = "Sequences", description = "Manage and list sequences", extensions = @Extension(name = "test-ext", value = "text-ext-value"))
public class SequenceApi {
    private final AddSequence addSequence;
    private final UriInfo uriInfo;

    public SequenceApi(AddSequence addSequence, UriInfo uriInfo) {
        this.addSequence = addSequence;
        this.uriInfo = uriInfo;
    }

    @POST
    @RunOnVirtualThread
    @Transactional
    @Operation(operationId = "add-sequence", description = addSequenceDoc)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Sequence was accepted", headers = {
                    @Header(name = "Location", description = "URI of new sequence")
            }),
            @APIResponse(responseCode = "400", description = "Malformed input"),
            @APIResponse(responseCode = "409", description = "Sequence already exists"),
            @APIResponse(responseCode = "503", description = "Operation timed out")

    })
    public Response addNewSequence(
            @NotBlank(message = "name of sequence must not by empty") @HeaderParam("Sequence-Name") String name,
            @Schema(description = "Base pairs of sequence to add", example = "aaatcggttta")
            @NotBlank(message = "sequence cannot be blank") @RequestBody String sequence) {
        Log.info("received request");
        AddSequence.Response response = addSequence.add(sequence, () -> true);
        return switch (response) {
            case AddSequence.Response.Success success -> Response.created(uriInfo.getAbsolutePathBuilder()
                    .path(success.newSequenceId().toString()).build()).build();
            case AddSequence.Response.Error error -> switch (error.code()) {
                case FORBIDDEN -> HttpProblem.valueOf(Response.Status.FORBIDDEN, error.reason()).toResponse();
                case INVALID_INPUT -> HttpProblem.valueOf(Response.Status.BAD_REQUEST, error.reason()).toResponse();
                case TIMEOUT -> HttpProblem.valueOf(Response.Status.GATEWAY_TIMEOUT, error.reason()).toResponse();
                case DUPLICATE -> HttpProblem.valueOf(Response.Status.CONFLICT, error.reason()).toResponse();
            };
        };
    }
}