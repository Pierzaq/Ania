package pl.javastart.restassured.test.tasks;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User.User;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserMethods {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        ///
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType("application/json").build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(Matchers.lessThan(1000l), TimeUnit.MILLISECONDS); // Oczekujemy, że czas odpowiedzi będzie mniejszy niż 1 sekunda (lessThan)
        ResponseSpecification responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {


        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(123);


        given().body(user)

                .when().post("user")
                .then().
                assertThat().body("code", equalTo(200))
                .assertThat().body("type", equalTo("unknown"))
                .assertThat().body("message", equalTo("445"))
                .assertThat().statusCode(200);

        given().contentType("application/json")
                .pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then().
                assertThat().body("id", equalTo(445)).
                assertThat().body("firstName", equalTo("Krzysztof")).
                assertThat().body("lastName", equalTo("Kowalski"));
    }

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest2() {
        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(123);

        given()
                .body(user)
                .when().post("user")
                .then()
                .assertThat().body("code", equalTo(200))
                .assertThat().body("type", equalTo("unknown"))
                .assertThat().body("message", equalTo("445"))
                .assertThat().statusCode(200);

        given()
                .pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then()
                .assertThat().body("id", equalTo(445))
                .assertThat().body("username", equalTo("firstuser"))
                .assertThat().body("firstName", equalTo("Krzysztof"))
                .assertThat().body("lastName", equalTo("Kowalski"))
                .assertThat().body("email", equalTo("krzysztof@test.com"))
                .assertThat().body("password", equalTo("password"))
                .assertThat().body("phone", equalTo("+123456789"))
                .assertThat().body("userStatus", equalTo(123))
                .assertThat().statusCode(200);
    }


    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {


        long responseTime= given().pathParam("param", "firstuser").
                when().get("user/{param}").then()
                .extract().timeIn(TimeUnit.MILLISECONDS);

    System.out.println("Response time is " + responseTime);

}
}