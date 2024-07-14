package rest.assured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import io.restassured.filter.log.LogDetail;
import io.restassured.module.jsv.JsonSchemaValidator;

public class flipzon {
	
	@Test (enabled = false)
	void amazonSearchAPI() {
	given()
	.when()
		.get("https://www.amazon.in/s/ref=nb_sb_noss_1?field-keywords=books")
	.then()
		.log().all()
		.statusCode(200);
	}
	
	@Test (enabled = true, priority = 1)
	void flipkartSearchAPI() {
	given()
	.when()
		.get("https://www.flipkart.com/search?q=tv")
	.then()
		.log().all()
		.statusCode(200);
	}
}
