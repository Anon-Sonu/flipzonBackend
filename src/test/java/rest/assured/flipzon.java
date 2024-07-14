package rest.assured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class flipzon {

    private static final String AMAZON_BASE_URL = "https://www.amazon.in";
    private static final String FLIPKART_BASE_URL = "https://www.flipkart.com";
    
    private static final String AMAZON_PATH = "/s/ref=nb_sb_noss_1?field-keywords=The+Psychology+of+Money";
    private static final String FLIPKART_PATH = "/search?q=The+Psychology+of+Money";

    @Test(enabled = false)
    void amazonSearchAPI() {
        given()
            .when()
                .get(AMAZON_BASE_URL + AMAZON_PATH)
            .then()
                .log().all()
                .statusCode(200)
                .body("results", notNullValue())
                .body("searchResultItems.size()", greaterThan(0))
                .time(lessThan(5000L));
    }

    @Test(enabled = true, priority = 1)
    void flipkartSearchAPI() {
        Response response = given()
            .when()
                .get(FLIPKART_BASE_URL + FLIPKART_PATH)
            .then()
                .log().all()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0))
                .time(lessThan(5000L))
                .extract().response();

        String contentType = response.getContentType();
        System.out.println("Flipkart Response Content Type: " + contentType);

        String flipkartBookPrice = null;

        if (contentType.contains("text/html")) {
            String html = response.getBody().asString();
            Document doc = Jsoup.parse(html);
            Element priceElement = doc.select("div._30jeq3, div._1_WHN1, span._30jeq3, span._1vC4OE").first();

            if (priceElement != null) {
                flipkartBookPrice = priceElement.text();
            } else {
                System.out.println("Price not found in HTML response.");
            }
        }

        System.out.println("Flipkart Book Price: " + flipkartBookPrice);
    }
}
