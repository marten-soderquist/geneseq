//package se.allfader.geneseq.e2e;
//
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static io.restassured.RestAssured.given;
//
//@QuarkusTest
//public class AddPrimerViaRest extends ApiTestConfig {
//
//    private static final String ADD_PRIMER_BODY = """
//            {
//                "name": "%s",
//                "sequence": "atcgcggatttaaacgatgatga"
//            }
//            """;
//
//    @Test
//    void addingPrimerReturns201() {
//        given().body(ADD_PRIMER_BODY.formatted("aName"))
//                .contentType(ContentType.JSON)
//                .post(API_PATH + "/primer")
//                .then().assertThat().statusCode(201);
//    }
//
//    @Test
//    void shouldReturn409IfPrimerNameExists() {
//        final String name = UUID.randomUUID().toString().substring(0, 15);
//        Response firstPostResponse = createPrimerViaRest(name);
//        firstPostResponse.then().assertThat().statusCode(201);
//        Response secondPostReponse = createPrimerViaRest(name);
//        secondPostReponse.then().assertThat().statusCode(409);
//
//    }
//
//    private static Response createPrimerViaRest(final String name) {
//        return given().body(ADD_PRIMER_BODY.formatted(name))
//                .contentType(ContentType.JSON)
//                .post(API_PATH + "/primer");
//    }
//}
