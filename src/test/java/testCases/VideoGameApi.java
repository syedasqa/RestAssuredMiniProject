package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class VideoGameApi {

	@Test(priority = 1)
	public void test_getAllVideoGames() {
		given().when().get("http://localhost:8080/app/videogames/10").then().statusCode(200);

	}

	@Test(priority = 2)
	public void test_addNewVideoGames() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response response = given().contentType("application/jason")
				.body(data).when()
				.post("http://localhost:8080/app/videogames")
				.then().statusCode(200).log().body().extract().response();
		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}

	@Test(priority = 3)
	public void test_getOneVideoGames() {
		given().when().get("http://localhost:8080/app/videogames/100").then().statusCode(200)
				.body("videoGame.id", equalTo("100")).body("videoGame.name", equalTo("Spider-Man"));

	}

	@Test(priority = 4)
	public void test_updateVideoGames() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Pac-Man");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		given()
		.contentType("application/jason")
		.body(data).when()
		.put("http://localhost:8080/app/videogames/100")
				.then().statusCode(200)
				.log().body().body("videoGame.id", equalTo("100"))
				.body("videoGame.name", equalTo("Pac-Man"));

	}
	@Test(priority = 5)
	public void test_DeleteVideoGames() {

	Response response = 
			given()
			.when()
			.delete("http://localhost:8080/app/videogames/100")
			.then().statusCode(200)
			.log().body()
			.extract().response();
	String jsonString = response.asString();
	Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
}
}