package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudProjectApi {

    private static int projectId;

    @Order(-4)
    @Test
    void crear(){
        JSONObject payload = new JSONObject();
        payload.put("Content","CRUD");
        payload.put("Icon",3);

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
                        .statusCode(200)
                        .body("Content",equalTo("CRUD"))
                        .body("Icon",equalTo(3))
                        .time(lessThan(5000L))
                        .extract().response();

        projectId = response.jsonPath().getInt("Id");

        System.out.println("*** ProjectID: "+projectId);
    }


    @Order(-1)
    @Test
    void actualizar(){
        JSONObject payload = new JSONObject();
        payload.put("Content","CRUD Updated");
        payload.put("Icon",5);


        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .body(payload.toString())
                .log()
                .all()
        .when()
                .put("https://todo.ly/api/projects/"+projectId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .body("Id",greaterThan(0))
                .statusCode(200)
                .body("Content",equalTo("CRUD Updated"))
                .body("Icon",equalTo(5));

    }

    @Order(0)
    @Test
    void buscarPorId(){
        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .log()
                .all()
        .when()
                .get("https://todo.ly/api/projects/"+projectId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .body("Id",greaterThan(0))
                .statusCode(200)
                .body("Content",equalTo("CRUD Updated"))
                .body("Icon",equalTo(5));

    }

    @Order(9)
    @Test
    void eliminar(){
        given()
                .auth()
                .preemptive()
                .basic("smbr.api.rest@gmail.com", "smbr.api.rest")
                .log()
                .all()
        .when()
                .delete("https://todo.ly/api/projects/"+projectId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .statusCode(200)
                .body("Content",equalTo("CRUD Updated"))
                .body("Icon",equalTo(5))
                .body("Deleted",equalTo(true));
    }

}
