Feature: Login
  Scenario: Como usuario quiero ingresar un email y password para registrar usuarios
    Given que tengo acceso a instagram
     When me registro usando varias personas
    | nombre    | apellido    | telefono  | direccion | dni       |
    | juan      | perez       | 999555444 | peru      | 12345678  |
    | jose      | xxxxx       | 999555444 | peru      | 12345678  |
    | carmen    | yyyyyy      | 999555444 | peru      | 12345678  |
      And me registro usando 1 persona
      | nombre    | apellido    | telefono  | direccion | dni       |
      | juan      | perez       | 999555444 | peru      | 12345678  |
    Then muestra la página principal