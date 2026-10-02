@Regression
Feature: CRUD Project API
  Background: init
    Given SB i have access to todo.ly

  Scenario: Como usuario quiero CREAR un ITEM desde el API
    When SB i send a POST request to "/api/items.json" with body from CSV
    Then SB response code is 200
    And SB the attribute string "Content" is "Item_X4"
    And SB the attribute int "ItemType" is "1"
    And SB the attribute boolean "Checked" is "false"
    And SB the attribute int "Priority" is "1"
    And SB i save the value of "Id" in the variable "itemId"

  Scenario: Como usuario quiero ACTUALIZAR un ITEM desde el API
    When SB i send a PUT request to "/api/items/{itemId}.json" with body
    """
     {
      "Content": "Item_X4_Updated",
      "Checked": false
     }
     """
    Then SB response code is 200
    And SB the attribute string "Content" is "Item_X4_Updated"
    And SB the attribute boolean "Checked" is "false"

  Scenario: Como usuario quiero LEER un ITEM desde el API
    When SB i send a GET request to "/api/items/{itemId}.json" with body
    """
     """
    Then SB response code is 200
    And SB the attribute string "Content" is "Item_X4_Updated"
    And SB the attribute boolean "Checked" is "false"

  Scenario: Como usuario quiero ELIMINAR un ITEM desde el API
    When SB i send a DELETE request to "/api/items/{itemId}.json" with body
    """
     """
    Then SB response code is 200
    And SB the attribute string "Content" is "Item_X4_Updated"
    And SB the attribute boolean "Deleted" is "true"