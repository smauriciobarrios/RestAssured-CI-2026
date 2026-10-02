package tarea2;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Crud_ItemsApi {

    static String token;
    private static int itemtId;

    @BeforeAll
    static void init() throws IOException {
        Properties props = new Properties();
        InputStream inputStream = Crud_ItemsApi.class
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

    @Order(1)
    @CsvFileSource(resources = "/csv/crear-item.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void crearItem(String Content, int ItemType, boolean Checked, int Priority, int CodigoRespuesta) {

        JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();

        JSONObject payload = new JSONObject();
        payload.put("Content", Content);
        payload.put("ItemType", ItemType);
        payload.put("Checked", Checked);
        payload.put("Priority", Priority);

        Response response =
                given()
                        .header("Token", token)
                        .body(payload.toString())
                        //.log()
                        //.all()
                .when()
                        .post("https://todo.ly/api/items.json")
                .then()
                        .log().all()
                        .statusCode(CodigoRespuesta)
                        .body("Content", equalTo(Content))
                        .body("ItemType", equalTo(ItemType))
                        .body("Checked", equalTo(Checked))
                        .body("Priority", equalTo(Priority))
                        .body(
                                JsonSchemaValidator
                                        .matchesJsonSchemaInClasspath("schemas/createItemSchema.json")
                                        .using(factory)
                        )
                        .extract().response();

        itemtId = response.jsonPath().getInt("Id");
        System.out.println("*** itemtId: " + itemtId);
    }


    @Order(2)
    @Test
    void actualizarUltimoItem(){
        JSONObject payload = new JSONObject();
        payload.put("Content","Item_X4 Updated");
        payload.put("Checked",true);

        given()
                .header("Token", token)
                .body(payload.toString())
                .log()
                .all()
        .when()
                .put("https://todo.ly/api/items/"+itemtId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .body("Id",greaterThan(0))
                .statusCode(200)
                .body("Checked",equalTo(true))
                .body("Content",equalTo("Item_X4 Updated"));

    }

    @Order(3)
    @Test
    void buscarPorUltimoId(){
        given()
                .header("Token", token)
                .log()
                .all()
        .when()
                .get("https://todo.ly/api/items/"+itemtId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .body("Id",greaterThan(0))
                .statusCode(200)
                .body("Content",equalTo("Item_X4 Updated"));

    }

    @Order(4)
    @Test
    void eliminarUltimoItem(){
        given()
                .header("Token", token)
                .log()
                .all()
        .when()
                .delete("https://todo.ly/api/items/"+itemtId +".json")
        .then()
                .log().all()
                //.log().ifValidationFails()
                .statusCode(200)
                .body("Content",equalTo("Item_X4 Updated"))
                .body("Deleted",equalTo(true));
    }

}