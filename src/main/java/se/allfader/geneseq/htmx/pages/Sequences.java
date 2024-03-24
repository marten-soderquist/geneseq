package se.allfader.geneseq.htmx.pages;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import se.allfader.geneseq.application.query.pagination.Page;
import se.allfader.geneseq.application.query.sequence.ListSequences;
import se.allfader.geneseq.application.query.sequence.StoredSequence;
import se.allfader.geneseq.application.query.sequence.StoredSequencePage;
import se.allfader.geneseq.domain.user.User;

import java.util.List;
import java.util.UUID;

@Path("sequences")
public class Sequences {

    @Inject
    ListSequences listSequences;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance page(StoredSequencePage sequencePage);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Operation(hidden = true)

    public Response getPage(){
        ListSequences.Response response = listSequences.getPage(new ListSequences.Command(new Page(0, 15), new SuperAdmin()));
        switch (response) {
            case ListSequences.Response.Success success -> {
//                StoredSequencePage sequencePage = success.pageOfSequences();
                StoredSequencePage sequencePage = new StoredSequencePage(new Page(0, 15), List.of(
                        randomStoredSequence(),randomStoredSequence(),randomStoredSequence(),randomStoredSequence(),
                        randomStoredSequence(),randomStoredSequence(),randomStoredSequence(),randomStoredSequence()
                ));

                sequencePage.current().pageSize();
                TemplateInstance instance = Templates.page(sequencePage);
                return Response.ok(instance).build();
            }
            case ListSequences.Response.Error ignored -> {
                return  Response.serverError().build();
            }
        }
    }

    private static class SuperAdmin implements User{
        @Override
        public boolean canAddNewSequence() {
            return true;
        }
        @Override
        public boolean canListSequences() {
            return true;
        }
    }

    private static StoredSequence randomStoredSequence() {
        return new StoredSequence() {
            @Override
            public UUID id() {
                return UUID.randomUUID();
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public String basepairs() {
                return null;
            }
        };
    }
}
