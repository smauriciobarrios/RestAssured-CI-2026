package steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class MyStepDefs {

    /**
     * {}       -> reemplaza cualquier valor como parametro
     * {word}   -> reemplaza una sola palabra
     * {string} -> reemplaza un valor cuando está en comilla dobles
     * {int}    -> reemplaza un valor numerico
     * {float}  -> reemplaza un valor decimal
     */


    @Given("que tengo acceso a {}")
    public void queTengoAccesoAFacebook(String appName){
        System.out.println("Step: que tengo acceso a: "+appName);
    }

    @When("ingreso mi email: {word}")
    public void ingresoMiEmailSmbrApiGmailCom(String email) {
        System.out.println("Step: ingreso mail: "+ email);
    }

    @And("ingreso mi password: {string}")
    public void ingresoMiPassword(String password) {
        System.out.println("Step: ingreso password: "+password);
    }

    @Then("hago clic en el botón iniciar sesión")
    public void hagoClicEnElBotónIniciarSesión() {
        System.out.println("Step: click iniciar sesion");
    }

    @And("muestra la página principal")
    public void muestraLaPáginaPrincipal() {
        System.out.println("Step: muestra pag principal");
    }

    @And("no muestra la página principal")
    public void noMuestraLaPáginaPrincipal() {
        System.out.println("Step: NO muestra pag principal");
    }


    @And("deberia ver los siguientes menus")
    public void deberiaVerLosSiguientesMenus(List<String> menus) {
        for (String menu: menus){
            System.out.println("Lista menu: "+menus);
        }
    }

    @When("me registro con")
    public void meRegistroCon(Map<String, String> data) {
        data.forEach((key, value) ->{
            System.out.println("Clave: "+key+" - valor: "+value);
        });
    }

    @When("me registro usando 1 persona")
    public void meRegistroUsando1Persona(Persona persona) {
        System.out.println("Persona:");
        System.out.println("nombre:"+persona.getNombre());
        System.out.println("apellido:"+persona.getApellidos());
        System.out.println("telefono:"+persona.getTelefono());
        System.out.println("direccion:"+persona.getDireccion());
        System.out.println("dni:"+persona.getDni());
        System.out.println("-------------------------");
    }

    @When("me registro usando varias personas")
    public void meRegistroUsandoVariasPersonas(List<Persona> personas) {
        for(Persona persona : personas){
            System.out.println("Persona:");
            System.out.println("nombre:"+persona.getNombre());
            System.out.println("apellido:"+persona.getApellidos());
            System.out.println("telefono:"+persona.getTelefono());
            System.out.println("direccion:"+persona.getDireccion());
            System.out.println("dni:"+persona.getDni());
            System.out.println("-------------------------");
        }
    }

    @DataTableType
    public Persona convertToPersona(Map<String,String> data){
        Persona persona = new Persona();
        persona.setNombre(data.get("nombre"));
        persona.setApellidos(data.get("apellido"));
        persona.setTelefono(data.get("telefono"));
        persona.setDireccion(data.get("direccion"));
        persona.setDni(data.get("dni"));
        System.out.println("-------------------------");
        return persona;
    }

    @Then("deberia ver sus terminos de aceptacion")
    public void deberiaVerSusTerminosDeAceptacion(String data) {
        System.out.println("terminos de aceptacion: \n"+data);
    }
}
