package pl.javastart.restassured.test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FirstRestAssuredAutomatedTest {

    @BeforeClass
    public void setupConfiguration(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
    }


    @Test
    public void givenNonExistingPetIdWhenGetPetThenPetNotFoundTest() {
        given().when().get("/v2/pets").then().statusCode(404);
    }

}
