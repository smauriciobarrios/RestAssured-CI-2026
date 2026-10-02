package tarea3;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import factory.ApiClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.*;
import java.util.Base64;
import java.util.Properties;
import static org.hamcrest.Matchers.equalTo;

public class CrudItemstApiStepFactorizado {

    static public ApiClient client = new ApiClient();
    static public Response response;
    static public String value;
    public static Properties props;

    @Given("SB i have access to todo.ly")
    public void sb_i_have_access_to_todo_ly() throws IOException {
        props = new Properties();
        InputStream inputStream = CrudItemstApiStepFactorizado.class
                .getClassLoader()
                .getResourceAsStream("token/usuario.properties");
        props.load(inputStream);
        String credenciales = props.getProperty("api.usuario")+":"+props.getProperty("api.clave");
        client.addHeaders("Authorization", "Basic " + Base64.getEncoder().encodeToString(credenciales.getBytes()));
    }

    @When("SB i send a POST request to {string} with body from CSV")
    public void sbISendAPOSTRequestToWithBodyFromCSV(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/csv/crear-item.csv"))) {
            String line;
            reader.readLine(); // saltar encabezado
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                JSONObject payload = new JSONObject();
                payload.put("Content", values[0]);
                payload.put("ItemType", Integer.parseInt(values[1]));
                payload.put("Checked", Boolean.parseBoolean(values[2]));
                payload.put("Priority", Integer.parseInt(values[3]));

                response = client.send("POST", path, payload.toString());
                response.then().statusCode(Integer.parseInt(values[4]));
            }
        }
    }
    @When("SB i send a {word} request to {string} with body")
    public void sb_i_send_a_post_request_to_with_body(String method, String path, String body) {
        response = client.send(method, path, body);
    }

    @Then("SB response code is {int}")
    public void sb_response_code_is(int responseCodeExpected) {
        response.then().statusCode(responseCodeExpected);
    }
    @And("SB the attribute {word} {string} is {string}")
    public void sbtheAttributeStringIs(String type, String attribute, String expectedResult) {
        System.out.println("type: " + type);
        JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();
        switch (type.toLowerCase()) {
            case "int" -> response.then().body(attribute, equalTo(Integer.parseInt(expectedResult)));
            case "boolean" -> response.then().body(attribute, equalTo(Boolean.parseBoolean(expectedResult)));
            case "string" -> response.then().body(attribute, equalTo(expectedResult));
        }
        response.then().body(
                JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schemas/createItemSchema.json")
                        .using(factory)
        );
        System.out.println("JsonSchemaFactory revisado correctamente con: " + factory);
    }

    @And("SB i save the value of {string} in the variable {string}")
    public void sbiSaveTheValueOfInTheVariable(String jsonVariable, String variableName) {
        value = response.jsonPath().getString(jsonVariable);
        client.addVariable(variableName, value);
    }
}