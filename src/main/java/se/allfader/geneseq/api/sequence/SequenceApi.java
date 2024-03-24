package se.allfader.geneseq.api.sequence;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import se.allfader.geneseq.application.commands.AddSequence;
import se.allfader.geneseq.application.commands.AddSequence.Response.Success;
import se.allfader.geneseq.domain.user.User;

import static se.allfader.geneseq.api.sequence.SequenceApiDocStrings.addSequenceDoc;

@Path("api/sequence")
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
    @Counted(name = "addNewSequence", description = "How many times the addNewSequence endpoint has been called.")
    @Timed(name = "addSequenceTimer", description = "A measure of how long it takes to add a new sequence.", unit = MetricUnits.MILLISECONDS)
    public Response addNewSequence(
            @NotBlank(message = "name of sequence must not by empty") @FormParam("Sequence-Name")
            String name,
            @Schema(description = "Base pairs of sequence to add", example = "aaatcggttta")
            @NotBlank(message = "sequence cannot be blank") @FormParam("sequence")
            String sequence) {

        AddSequence.Response response = addSequence.handle(new AddSequence.Command(name, sequence, new SuperAdmin()));

        switch (response) {
            case Success success -> {
                return Response.created(uriInfo.getAbsolutePathBuilder()
                        .path(success.newSequenceId().toString()).build()).build();
            }
            case AddSequence.Response.Error error -> throw switch (error.code()) {
                case FORBIDDEN -> HttpProblem.valueOf(Response.Status.FORBIDDEN, error.reason());
                case INVALID_INPUT -> HttpProblem.valueOf(Response.Status.BAD_REQUEST, error.reason());
                case TIMEOUT -> HttpProblem.valueOf(Response.Status.GATEWAY_TIMEOUT, error.reason());
                case DUPLICATE -> HttpProblem.valueOf(Response.Status.CONFLICT, error.reason());
            };
        }
    }

    private static class SuperAdmin implements User {
        @Override
        public boolean canAddNewSequence() {
            return true;
        }
    }
}
