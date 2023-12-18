package se.allfader.geneseq.e2e;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AddPrimerViaRest extends ApiTestConfig {

    private static final String ADD_PRIMER_BODY = """
            {
                "name": "TestPrimer",
                "sequence": "atcgcggatttaaacgatgatga"
            }
            """;

    @Test
    void addingPrimerReturns201() {
        given().body(ADD_PRIMER_BODY)
                .contentType(ContentType.JSON)
                .post(API_PATH + "/primer")
                .then().assertThat().statusCode(201);
    }
}
