package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*; //lessThan

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class basicRestAssured {

    /**
     * given() -> controla la configuración de la petición
     *            - headers
     *            - parámetros
     *            - body/payload
     *            - authorization
     *            - logs
     *
     *  when() -> especificamos el metodo: GET | POST | PUT | DELETE
     *            especificamos la url => ip:puerto | hostname
     *
     *  then() -> controlar la respuesta
     *             - response code
     *             - response body
     *             - response header
     *             - response size
     *             - response time
     *            implementar verificaciones
     *            implementar extracciones (extraer valores del response y asignarlo a variables)
     *            logs
     */

    @Test
    void crearProyecto() {
        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body("""
                        {
                            "Content": "RestAssured1",
                            "Icon": 5
                        }
                        """)
                .log()
                .all()
        .when()
                .post("https://todo.ly/api/projects.json");

    }

    @Test
    void crearProyectoUsandoJsonObject() {
        JSONObject payLoad = new JSONObject();
        payLoad.put("Content","RestAssuredJsonObject");
        payLoad.put("Icon",3);

        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body(payLoad.toString())
                .log()
                .all()
        .when()
                .post("https://todo.ly/api/projects.json");

    }

    @Test
    void crearProyectoUsandoResourcesJsonFile() {
        String rutaJsonFile = getClass()
                .getClassLoader()
                .getResource("projects.json")
                .getPath();
        System.out.println("Ruta Json File: "+rutaJsonFile);

        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body(new File(rutaJsonFile))
                .log()
                .all()
                .when()
                .post("https://todo.ly/api/projects.json");

    }


    @Test
    void crearProyecto_Request1_MultipleJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/dataRequest.json"));

        System.out.println("Ruta Json File: "+root);
        // Extraer el body del usuario
        String userBody = root.get("ProjectRequest1").toString();

        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body(userBody)
                .log()
                .all()
                .when()
                .post("https://todo.ly/api/projects.json");

    }

    @Test
    void crearProyecto_Request2_MultipleJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/test/resources/dataRequest.json"));

        System.out.println("Ruta Json File: "+root);
        // Extraer el body del usuario
        String userBody = root.get("ProjectRequest2").toString();

        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body(userBody)
                .log()
                .all()
                .when()
                .post("https://todo.ly/api/projects.json");

    }


    @Test
    void crearProyectoconVerificaciones() {
        JSONObject payLoad = new JSONObject();
        payLoad.put("Content","RestAssuredV3");
        payLoad.put("Icon",6);

        Response response =
            given()
                    .auth()
                    .preemptive()
                    .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                    .body(payLoad.toString())
                    .log()
                    .all()
            .when()
                    .post("https://todo.ly/api/projects.json")
            .then()
                    //.log().all()
                    .log().ifValidationFails()
                    .body("Id",greaterThan(0))
                    .statusCode(200)
                    .body("Content",equalTo("RestAssuredV3"))
                    .body("Icon",equalTo(6))
                    .time(lessThan(5000L))
            .extract().response();

        int id = response.jsonPath().getInt("Id");
        String content = response.jsonPath().getString("Content");
        int icon = response.jsonPath().getInt("Icon");

        System.out.println("Id:" +id);
        System.out.println("Content:" +content);
        System.out.println("Icon:" +icon);

    }

}