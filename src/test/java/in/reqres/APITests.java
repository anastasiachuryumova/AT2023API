package in.reqres;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.oneOf;
import org.testng.Assert;
import org.testng.annotations.Test;

import data.People;
import data.PeopleCreated;
import data.Resource;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static specification.Specification.deleteSpec;
import static specification.Specification.installSpec;
import static specification.Specification.requestSpec;
import static specification.Specification.responseSpec;

public class APITests {

    @Test
    public void firstTest(){
                given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(oneOf(200, 201))
                .log().all()
                .body("page",notNullValue())
                .body("per_page",notNullValue())
                .body("total",notNullValue())
                .body("total_pages",notNullValue())
                .body("data.id",not(hasItem(nullValue())));
    }

    @Test
    public void firstTest2(){
        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Kirill");
        requestData.put("job", "teacher");
        Response response = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .contentType("application/json")
                .body(requestData)
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertEquals(jsonResponse.get("name"), requestData.get("name"),
                "Ожидали создание пользователя с именем "+jsonResponse.get("name")+
                        ", создался с именем"+requestData.get("name"));
        Assert.assertEquals(jsonResponse.get("job"), requestData.get("job"),
                "Ожидали создание пользователя с ролью "+jsonResponse.get("job")+
                        ", создался с ролью"+requestData.get("job"));
    }

    @Test
    public void firstTest3(){
        People people = new People("Katy", "programmer");
        PeopleCreated peopleCreated = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .contentType("application/json")
                .body(people)
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(oneOf(200, 201))
                .extract().body().as(PeopleCreated.class);
        System.out.println("______________");
        System.out.println(peopleCreated.getCreatedAt());
    }

    @Test
    public void firstTest4(){
        Resource resource = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .extract().body().as(Resource.class);
        resource.getData().forEach(x-> System.out.println(x.getEmail()));
    }

    @Test
    public void specTest(){
        installSpec(requestSpec(), responseSpec());
        People people = new People("Katy", "programmer");
        PeopleCreated peopleCreated = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .body(people)
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .extract().body().as(PeopleCreated.class);
        System.out.println("______________");
        System.out.println(peopleCreated.getCreatedAt());
    }
    @Test()
    public void dtoSpecDefaultTest(){
        installSpec(requestSpec(), responseSpec());

        People people = new People("Kirill", "teacher");
        PeopleCreated peopleCreated = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .body(people)
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .extract().as(PeopleCreated.class);
        System.out.println(peopleCreated.getCreatedAt());

        deleteSpec();
    }
}
