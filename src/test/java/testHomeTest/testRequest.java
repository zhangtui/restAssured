package testHomeTest;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class testRequest {
	@BeforeClass
	public static void sutUp(){
		//RestAssured.baseURI="";
		RestAssured.proxy("127.0.0.1", 8080);
	}
	@Test
	public void testJsonPost(){
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("id", "6040");
		data.put("title", "通过代理安装appium");
		data.put("name", "张瑞");
		given()
				.contentType(ContentType.JSON)
				.body(data)
		.when()
				.post("http.baidu.com")
		.then()
				.statusCode(200)
				.time(lessThan(1500L));
		;
		
	}

}
