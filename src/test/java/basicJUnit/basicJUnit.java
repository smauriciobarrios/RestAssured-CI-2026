package basicJUnit;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class basicJUnit {

    @BeforeAll
    static void BeforeAll(){
        System.out.println("Este metodo se ejecuta 1 vez antes de todos los TEST");
    }

    @AfterAll
    static void AfterAll(){
        System.out.println("Este metodo se ejecuta 1 vez después de todos los TEST");
    }

    @BeforeEach
    void inicializar(){
        System.out.println("Este metodo se ejecuta antes de cada test...");
    }

    @AfterEach
    void limpiar(){
        System.out.println("Este metodo se ejecuta después de cada test...");
    }

    @Order(1)
    @Test
    void crearProyecto(){
        System.out.println("crearProyecto");
    }

    @Order(2)
    @Test
    void actualizarProyecto(){
        System.out.println("actualizarProyecto!");
    }
}
