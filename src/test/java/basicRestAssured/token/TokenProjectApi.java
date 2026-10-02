package basicRestAssured.token;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TokenProjectApi {

    static String token;

    @BeforeAll
    static void init() throws IOException {
        Properties props = new Properties();
        InputStream inputStream = TokenProjectApi.class
                .getClassLoader()
                .getResourceAsStream("token/usuario.properties");

        props.load(inputStream);

        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic(props.getProperty("api.usuario"), props.getProperty("api.clave"))
                        .log().all()
                        .when()
                        .get("https://todo.ly/api/authentication/token.json")
                        .then()
                        .statusCode(200)
                        .extract().response();

        token = response.jsonPath().getString("TokenString");

    }

    @CsvFileSource(resources = "/csv/crear-proyecto.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void crear(String proyecto, int icono, int codigoRespuesta) {
        JSONObject payload = new JSONObject();
        payload.put("Content", proyecto);
        payload.put("Icon", icono);

        Response response =
                given()
                        .header("Token", token)
                        .body(payload.toString())
                        .log()
                        .all()
                        .when()
                        .post("https://todo.ly/api/projects.json")
                        .then()
                        .log().all()
                        .statusCode(codigoRespuesta)
                        .body("Content", equalTo(proyecto))
                        .body("Icon", equalTo(icono))
                        .extract().response();

        int projectId = response.jsonPath().getInt("Id");
        System.out.println("*** projectId: " + projectId);
    }

}