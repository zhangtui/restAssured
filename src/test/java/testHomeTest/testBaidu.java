package testHomeTest;


import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class testBaidu {	
	@BeforeClass
	public static void setup(){
		useRelaxedHTTPSValidation();
		//RestAssured.proxy("127.0.0.1", 8080);
		RestAssured.baseURI="https://testerhome.com";
	}
//	@Test
//	public void testHtml(){
//		given()
//				.queryParam(  "q", "appium")
//		.when()
//				.get("https://testerhome.com/search")
//		.then()
//				.statusCode(200)
//				.body("html.head.title", equalTo("appium · 搜索结果 · TesterHome"));
//	}
//	@Test
//	public void testTesterHomeJson(){
//		useRelaxedHTTPSValidation();
//		given().when().get("https://testerhome.com/api/v3/topics.json").prettyPeek()
//		.then()
//				.statusCode(200)
//				//.body("topics.title", hasItems("优质招聘汇总"))
//				.body( "topics.title[3]", equalTo("Appium 执行速度变慢"))
//				//.body("topics.findAll {topic->topic.id == 13269}.title", hasItems("bootstrap3+unittest+django2 接口自动化测试平台"))
//				.body("topics.find {topic->topic.id == 12927}.title", equalTo("十年了，回顾一路走来的测试"))
//				;		
//	}
//	@Test
//	public void testTesterHomeJsonSingle(){
//		given().when().get("https://testerhome.com/api/v3/topics/10254.json")
//		.then()
//				.statusCode(200)
//				.body("topic.title", equalTo("优质招聘汇总"));
//	}
//	@Test
//	public void testXML(){
//		 given()
//		.when().get("http://127.0.0.1:8000/index.xml")
//		.then()
//				.statusCode(200)
//				.body("shopping.category.item.name[2]", equalTo("Paper"))
//				.body("shopping.category.size()",equalTo(3))
//				.body("shopping.category.find {it.@type == 'present'}.item.name", equalTo("Kathryn's Birthday"))
//				.body("**.find { it.name == 'Pens' }.price", equalTo("15"))
//				;
//	}
	@Test
	public void testJsonPost(){
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("id", "6040");
		data.put("title", "通过代理安装appium");
		data.put("name", "张瑞");
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("toplic", data);
		given()
				.contentType(ContentType.JSON)
				.body(root)
		.when()
				.post("http://www.baidu.com")
		.then()
				.statusCode(302)
				.time(lessThan(4000L)) //请求接口时间限制(当lessThan找不到路径时需要导入org.hamcrest.Matchers.*包)
		;
	}
//	@Test
//	//多数据返回取出
//	public void multApiMultData(){
//		String name = given().get("/api/v3/topics/13380.json")
//		.then()
//				.statusCode(200)
//				.extract().path("topic.user.name")
//		;
//		System.out.println(name);
//		//第二个接口去请求，添加第一个返回的参数
//		given().queryParam("q", name)
//		.when().get("/search")
//		.then().statusCode(200).body(containsString(name))
//		;
//		System.out.println("");
//	}
	@Test
	/*
	 * 测试从第一个接口中返回response ，关联到第二个接口中当参数请求
	 */
	public void multApiMultData(){
		Response response= given().get("/api/v3/topics/13380.json")
				.then().statusCode(200)
				.extract().response()
				;
		String name = response.path("topic.user.name");
		Integer uid = response.path("topic.user.id");
		System.out.println(name);
		System.out.println(uid);
		
		
		given()
			.queryParam("q", name)
			.cookie("name",name)
			.cookie("uid",uid)
		.when().get("/search")
		.then()
			.statusCode(200)
			.body(containsString(name))
			;	
	}
	/*
	 * 通用返回结果断言
	 */
	@Test
	public void testSpec(){
		ResponseSpecification rs = new ResponseSpecBuilder().build();
		rs.statusCode(200);
		rs.body(not(containsString("error"))); //在body中不存在error
		rs.time(lessThan(4000L));
		
		
		given().get("/api/v3/topics/13380.json").then().spec(rs);
	}
	/*
	 * 
	 */
	
}
