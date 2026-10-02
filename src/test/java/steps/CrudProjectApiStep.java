package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CrudProjectApiStep {

    Response response;
    static int projectId;

    @Given("que tengo acceso al API todo.ly")
    public void queTengoAccesoAlAPITodoLy() {

    }

    @When("envio el POST request a la url {string} con el body")
    public void envioElPOSTRequestALaUrlConElBody(String url, String body) {
        response =
                given()
                        .auth()
                        .preemptive()
                        .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                        .body(body)
                        .log().all()
                        .when()
                        .post(url)
                        .then()
                        .log()
                        .all()
                        .extract().response();
    }

    @Then("el codigo de respuesta deberia ser {int}")
    public void elCodigoDeRespuestaDeberiaSer(int codigoRespuestaEsperado) {
        response
                .then()
                .statusCode(codigoRespuestaEsperado);
    }

    @And("el nombre del proyecto deberia ser {string}")
    public void elNombreDelProyectoDeberiaSer(String nombreProyectoEsperado) {
        response
                .then()
                .body("Content", equalTo(nombreProyectoEsperado));
    }

    @And("el icono del proyecto deberia ser {int}")
    public void elIconoDelProyectoDeberiaSer(int iconoProyectoEsperado) {
        response
                .then()
                .body("Icon", equalTo(iconoProyectoEsperado));
    }

    @And("guardo el id del proyecto de la variable {string}")
    public void guardoElIdDelProyectoDeLaVariable(String variable) {
        projectId = response.jsonPath().getInt(variable);
    }

    @When("envio el PUT request a la url {string} con el body")
    public void envioElPUTRequestALaUrlConElBody(String url, String body) {
        response =
            given()
                    .auth()
                    .preemptive()
                    .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                    .body(body)
                    .log().all()
            .when()
                    .put(url.replace("PROJECT_ID",""+projectId))
            .then()
                    .log()
                    .all()
                    .extract().response();


    }

    @When("envio el GET request a la url {string}")
    public void envioElGETRequestALaUrl(String url) {
        response =
                given()
                        .auth()
                        .preemptive()
                        .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                        .log().all()
                        .when()
                        .get(url.replace("PROJECT_ID", "" + projectId))
                        .then()
                        .log().all()
                        .extract().response();
    }

    @When("envio el DELETE request a la url {string}")
    public void envioElDELETERequestALaUrl(String url) {
        response =
                given()
                        .auth()
                        .preemptive()
                        .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                        .log().all()
                        .when()
                        .delete(url.replace("PROJECT_ID", "" + projectId))
                        .then()
                        .log().all()
                        .extract().response();
    }
}