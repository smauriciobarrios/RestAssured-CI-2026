package basicRestAssured.csv;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CsvProjectApi {

    @CsvFileSource(resources = "/csv/crear-proyecto.csv",numLinesToSkip = 1)
    @ParameterizedTest
    void crear(String proyecto, int icono, int codigoRespuesta){
        JSONObject payload = new JSONObject();
        payload.put("Content",proyecto);
        payload.put("Icon",icono);

        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                        .body(payload.toString())
                        .log()
                        .all()
                .when()
                        .post("https://todo.ly/api/projects.json")
                .then()
                        .log().all()
                        //.log().ifValidationFails()
                        .body("Id",greaterThan(0))
                        .statusCode(codigoRespuesta)
                        .body("Content",equalTo(proyecto))
                        .body("Icon",equalTo(icono))
                        .extract().response();

    }
}
