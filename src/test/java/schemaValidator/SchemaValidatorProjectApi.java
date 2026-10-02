package schemaValidator;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidatorProjectApi {

    @Test
    void crear() {

        JsonSchemaFactory factory =
                JsonSchemaFactory
                        .newBuilder()
                        .setValidationConfiguration(
                                ValidationConfiguration
                                        .newBuilder()
                                        .setDefaultVersion(SchemaVersion.DRAFTV4)
                                        .freeze()
                        ).freeze();

        JSONObject payload = new JSONObject();
        payload.put("Content", "CRUD");
        payload.put("Icon", 3);

        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic("api.rest.setiembre2026@jbgroup.com", "admin123")
                        .body(payload.toString())
                        .log()
                        .all()
                        .when()
                        .post("https://todo.ly/api/projects.json")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("Content", equalTo("CRUD"))
                        .body("Icon", equalTo(3))
                        .body(
                                JsonSchemaValidator
                                        .matchesJsonSchemaInClasspath("schemas/createProjectSchema.json")
                                        .using(factory)
                        )
                        .extract().response();

        int projectId = response.jsonPath().getInt("Id");
        System.out.println("*** projectId: " + projectId);

    }

}