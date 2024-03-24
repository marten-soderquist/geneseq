package se.allfader.geneseq.htmx.pages;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.smallrye.openapi.OpenApiFilter;
import io.vertx.core.cli.annotations.Hidden;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/")
@Schema(hidden = true)
public class Index {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index(String test);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Operation(hidden = true)
    public String getIndex() {
        TemplateInstance index = Templates.index("ApaBanen");

        return index.data("test", "ApaBanan").render();
    }


}
