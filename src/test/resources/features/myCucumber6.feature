Feature: Login
  Scenario: Como usuario quiero ingresar un email y password para registrar usuarios
    Given que tengo acceso a instagram
     When me registro con
    | nombre    | carlos    |
    | apellidos | perez     |
    | telefono  | 123       |
    | direccion | peru      |
    | dni       | 44912123  |
    Then muestra la página principal