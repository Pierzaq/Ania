package pl.javastart.restassured.test.tasks;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Pet;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.util.Arrays;

public class QueryParamsTests {

    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {

        Pet pet = new Pet();
        pet.setId(777);
        pet.setStatus("sold");

        given().log().uri().body(pet).contentType("application/json")

                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")

                .then().log().all().statusCode(200);

        Pet[] pets = given().log().all().body(pet).contentType("application/json")
                .queryParam("status", "sold")
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/findByStatus")
                .then().log().all().statusCode(200).extract().as(Pet[].class);

        assertTrue(Arrays.asList(pets).size() > 0, "List of pets");

    }

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        Pet pet = new Pet();
        pet.setId(777);
        pet.setStatus("sold");
        pet.setName("Burek");

        given().log().uri().body(pet).contentType("application/json")

                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")

                .then().log().all().statusCode(200);

        JsonPath jsonPathResponse = given().log().method().log().uri()
                .pathParam("petId", pet.getId())
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200)
                .extract().jsonPath();

        String petName = jsonPathResponse.getString("name");

        assertEquals(petName, pet.getName());
    }
}