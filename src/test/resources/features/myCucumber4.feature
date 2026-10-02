Feature: Login

  Background: init
    Given que tengo acceso a instagram
    When ingreso mi email: smbr.api@gmail.com

  Rule: Credenciales validas
    Scenario: Como usuario quiero ingresar un email y password para iniciar sesión
        And ingreso mi password: "admin123"
       Then hago clic en el botón iniciar sesión
        And muestra la página principal

  Rule: Credenciales invalidas
    Scenario: Como usuario quiero ingresar un email y sin password para validar campos
      Then hago clic en el botón iniciar sesión
       And no muestra la página principal

    Scenario: Como usuario quiero ingresar un email y password sin click en iniciar sesion
      And ingreso mi password: "admin123"
      And no muestra la página principal