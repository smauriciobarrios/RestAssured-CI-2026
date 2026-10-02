Feature: CRUD Project API

  @projects
  Scenario: Como usuario quiero hacer el CREAR de un proyecto por API
    #crear
    Given que tengo acceso al API todo.ly
    When envio el POST request a la url "https://todo.ly/api/projects.json" con el body
    """
    {
      "Content": "Cucumber",
      "Icon": 10
    }
    """
    Then el codigo de respuesta deberia ser 200
    And el nombre del proyecto deberia ser "Cucumber"
    And el icono del proyecto deberia ser 10
    And guardo el id del proyecto de la variable "Id"

    #ACTUALIZAR
  Scenario: Como usuario quiero hacer el ACTUALIZAR de un proyecto por API
    When envio el PUT request a la url "https://todo.ly/api/projects/PROJECT_ID.json" con el body
    """
    {
      "Content": "CucumberUpdated",
      "Icon": 11
    }
    """
    Then el codigo de respuesta deberia ser 200
    And el nombre del proyecto deberia ser "CucumberUpdated"
    And el icono del proyecto deberia ser 11

  #LEER
  Scenario: Como usuario quiero hacer el LEER de un proyecto por API
    When envio el GET request a la url "https://todo.ly/api/projects/PROJECT_ID.json"
    Then el codigo de respuesta deberia ser 200
    And el nombre del proyecto deberia ser "CucumberUpdated"
    And el icono del proyecto deberia ser 11

  #eliminar
  Scenario: Como usuario quiero hacer el ELIMINAR de un proyecto por API
    When envio el DELETE request a la url "https://todo.ly/api/projects/PROJECT_ID.json"
    Then el codigo de respuesta deberia ser 200
    And el nombre del proyecto deberia ser "CucumberUpdated"
    And el icono del proyecto deberia ser 11