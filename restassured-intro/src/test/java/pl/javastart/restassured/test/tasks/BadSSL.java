
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BadSSL {

    @Test
    public void sslCertExpiredTest() {
        given().relaxedHTTPSValidation().when().get("https://expired.badssl.com/").then().statusCode(200);
    }

    @Test
    public void sslUntrustedExpiredTest() {
        given().relaxedHTTPSValidation().when().get("https://untrusted-root.badssl.com/").then().statusCode(200);
    }

}