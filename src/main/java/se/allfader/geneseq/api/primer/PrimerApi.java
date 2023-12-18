package se.allfader.geneseq.api.primer;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;
import se.allfader.geneseq.application.PrimerManagement;

@Path("primer")
public class PrimerApi {
    private final UriInfo uriInfo;
    private final PrimerManagement primerManagement;

    public PrimerApi(UriInfo uriInfo, PrimerManagement primerManagement) {
        this.uriInfo = uriInfo;
        this.primerManagement = primerManagement;
    }

    @POST
    public Uni<RestResponse<Void>> addPrimer(AddPrimerRequest addPrimerBody) {
        PrimerManagement.AddPrimerCommand command = new PrimerManagement.AddPrimerCommand(addPrimerBody.name(), addPrimerBody.sequence());
        return primerManagement.addNewPrimer(command)
                .map(addPrimerResponse -> uriInfo.getAbsolutePathBuilder().path(addPrimerResponse.newPrimerId()).build())
                .map(RestResponse::created);
    }

    public record AddPrimerRequest(String name, String sequence) {
    }
}
