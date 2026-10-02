Feature: Login
  Scenario: Como usuario quiero ingresar un email y password para iniciar sesión
    Given que tengo acceso a instagram
     When ingreso mi email: smbr.api@gmail.com
      And ingreso mi password: "admin123"
     Then hago clic en el botón iniciar sesión
      And muestra la página principal
      And deberia ver los siguientes menus
        | Configuracion |
        | Publicaciones |
        | Ajustes |
        | Perfil |
        | Contactos |