package in.reqres.homeworkSix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;
import org.testng.Assert;
import org.testng.annotations.Test;

import data.DataUser;
import data.Resource;
import dataHomeworkSix.ResourceList;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static specification.homeworkSix.SpecificationHw.deleteSpec;
import static specification.homeworkSix.SpecificationHw.installSpec;
import static specification.homeworkSix.SpecificationHw.requestSpec;
import static specification.homeworkSix.SpecificationHw.responseSpec;

public class HomeworkSix {

    @Test
    public void getNames(){
        List<DataUser> dataUser = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", DataUser.class);
        dataUser.forEach(x->x.getAvatar());
        dataUser.forEach(x->x.getFirst_name());
        dataUser.forEach(x->x.getLast_name());
        System.out.println(dataUser.size());
        dataUser.size();
    }

    @Test
    public void getNames1() {
        Resource resource = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .body("avatar",not(hasValue("https://reqres.in/img/faces/7-image.jpg")))
                .body("avatar",not(hasValue("https://reqres.in/img/faces/8-image.jpg")))
                .body("avatar",not(hasValue("https://reqres.in/img/faces/9-image.jpg")))
                .body("avatar",not(hasValue("https://reqres.in/img/faces/10-image.jpg")))
                .body("avatar",not(hasValue("https://reqres.in/img/faces/11-image.jpg")))
                .body("avatar",not(hasValue("https://reqres.in/img/faces/12-image.jpg")))
                .statusCode(200)
                .extract().body().as(Resource.class);
        resource.getData().forEach(x -> x.getFirst_name());
        resource.getData().forEach(x -> x.getLast_name());
    }
    /**
     * это пробный тест можно не смотреть
     */
    @Test
    public void getNames2() {
        ArrayList<DataUser> userData = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().ifError()
                .body("avatar",not(equalTo("avatar")))
                .statusCode(200)
                .extract().path("data");
        System.out.println(userData.size());
        System.out.println(userData.get(0));
        System.out.println(userData.get(1));
        System.out.println(userData.get(2));
        System.out.println(userData.get(3));
        System.out.println(userData.get(4));
        System.out.println(userData.get(5));
    }

    @Test
    public void getNames3() {
        /**
         * благодаря тому, что мы создали List<Map<String,Object>> мы можем в виде списка представить
         * строку data, а там уже будут элементы объекты, у которых в качестве ключа будут строки
         * и мы их сможем дальше обрабатывать
         */
        List<Map<String, Object>> userData = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().body().jsonPath().getList("data");
        System.out.println("Это аватар первого пользователя " + userData.get(0).get("avatar"));
        System.out.println("Общее количество пользователей равно " + userData.size());
        /**
         * здесь мы распечатываем имена пользователей, которые мы получили из json
         */
        System.out.println(userData.get(0).get("first_name") + " " + userData.get(0).get("last_name"));
        System.out.println(userData.get(1).get("first_name") + " " + userData.get(1).get("last_name"));
        System.out.println(userData.get(2).get("first_name") + " " + userData.get(2).get("last_name"));
        System.out.println(userData.get(3).get("first_name") + " " + userData.get(3).get("last_name"));
        System.out.println(userData.get(4).get("first_name") + " " + userData.get(4).get("last_name"));
        System.out.println(userData.get(5).get("first_name") + " " + userData.get(5).get("last_name"));
        /**
         * нам удалось получить аватары пользователей в виде объектов
         * теперь создадим эти объекты аватаров чтобы потом можно было добавить их в массив
         */
        Object avatar1 = userData.get(0).get("avatar");
        Object avatar2 = userData.get(1).get("avatar");
        Object avatar3 = userData.get(2).get("avatar");
        Object avatar4 = userData.get(3).get("avatar");
        Object avatar5 = userData.get(4).get("avatar");
        Object avatar6 = userData.get(5).get("avatar");
        /**
         * преобразуем массив в список и собираем всю логику с помощью Java streams api
         * так у нас получится собрать эти объекты в коллекцию и проверить есть ли в коллекции
         * элементы у которых частотность больше одного, если есть, они напечатаются
         * таким образом мы узнаем, есть ли одинаковые аватары
         */
        Object[] users = new Object[] {avatar1, avatar2, avatar3, avatar4, avatar5, avatar6};
        List<Object> listOfUsers = Arrays.asList(users);
        listOfUsers.stream().filter(i -> Collections.frequency(listOfUsers, i) >1).collect(Collectors.toSet()).forEach(System.out::println);
        /**
         * с помощью этого assert мы убеждаемся в тесте, что одинаковых аватаров нет
         * так как коллекция с элементами, у которых частотность больше одного, пустая
         */
        Assert.assertTrue(listOfUsers.stream().filter(i -> Collections.frequency(listOfUsers, i) >1).collect(Collectors.toSet()).isEmpty(),
                "В этом json файле есть пользователи, у которых одинаковый аватар");
    }

    @Test
    public void getAuthorizedDone(){
        installSpec(requestSpec(), responseSpec());
        /**
         * здесь такая же логика как на лекции с запросом put
         * мы вводим логин и пароль и получаем ответ
         * в первой части задания авторизация должна быть успешной поэтому мы получаем токен
         * и по нему определяем что все нормально с помощью assert
         */
        Map<String, String> requestData = new HashMap<>();
        requestData.put("email", "eve.holt@reqres.in");
        requestData.put("password", "cityslicka");
        Response response = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .body(requestData)
                .post("/api/login")
                .then()
                .log().ifError()
                .extract().response();
        JsonPath jsonResponse = response.jsonPath();
        System.out.println(jsonResponse.getString("token"));
        System.out.println(jsonResponse);
        Assert.assertTrue(jsonResponse.getString("token").equals("QpwL5tke4Pnpja7X4"),
                "Токен получить не удалось, авторизация не удалась");

        deleteSpec();
    }

    @Test
    public void getAuthorizedFail(){
        /**
         * здесь такая же логика как на лекции с запросом put
         * мы вводим логин, а пароль не вводим, поэтому получаем ответ 400
         * в второй части задания авторизация не должна быть успешной, поэтому мы меняем статус на 400
         * чтобы не валился тест, ошибка все равно напишется в логе
         * с помощью assert мы убеждаемся, что ответ мы все равно получаем, он не null
         * просто не соответствует токену
         * сделала так, чтобы тест не валился, несмотря на не введенный пароль
         */
        Map<String, String> requestData = new HashMap<>();
        requestData.put("email", "eve.holt@reqres.in");
        requestData.put("password", "");
        Response response = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .contentType("application/json")
                .body(requestData)
                .post("https://reqres.in/api/login")
                .then()
                .log().ifError()
                .statusCode(400)
                .extract().response();
        JsonPath jsonResponse = response.jsonPath();
        System.out.println(String.valueOf(jsonResponse));
        Assert.assertTrue(jsonResponse != null,
                "Токен получить не удалось, авторизация не удалась");
    }

    @Test
    public void getSortedByYears(){
        installSpec(requestSpec(), responseSpec());
        /**
         * здесь мы получаем из json файла список данных ресурсов
         */
        List<Map<String, Object>> resourceData = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .when()
                .get("/api/unknown")
                .then()
                .log().ifError()
                .extract().body().jsonPath().getList("data");
        System.out.println("Количество ресурсов в списке данных " + resourceData.size());
        System.out.println(resourceData.get(0).get("year") + " " +
                resourceData.get(1).get("year") + " " +
                resourceData.get(2).get("year") + " " +
                resourceData.get(3).get("year") + " " +
                resourceData.get(4).get("year") + " " +
                resourceData.get(5).get("year"));
        /**
         * это список данных ресурсов как он идет в json файле
         */
        System.out.println(resourceData.stream().toList());
        /**
         * это отсортированный по годам список данных ресурсов
         */
        System.out.println(resourceData.stream()
                .sorted(Comparator.comparing(o -> o.get("year").toString()))
                .collect(Collectors.toList()));
        /**
         * здесь мы их сравниваем и они получаются одинаковые
         */
        Assert.assertTrue(resourceData.stream().toList().equals(resourceData.stream()
                .sorted(Comparator.comparing(o -> o.get("year").toString()))
                .collect(Collectors.toList())), "Года в списке данных идут не по возрастанию");

        deleteSpec();
    }
    /**
     * с использованием классов данных
     */
    @Test
    public void getSortedByYears1(){
        installSpec(requestSpec(), responseSpec());
        ResourceList resourceList = given()
                .header("X-Api-Key", "reqres_cdeb39735ec64e6b8220ae3a19774fb1")
                .when()
                .get("/api/unknown")
                .then()
                .log().ifError()
                .extract().as(ResourceList.class);
        resourceList.getData().forEach(x-> System.out.println(x.getYear()));
        Assert.assertTrue(resourceList.getData().stream().toList().equals(resourceList.getData().stream()
                .sorted(Comparator.comparing(o -> o.getYear()))
                .collect(Collectors.toList())), "Года в списке данных идут не по возрастанию");
        deleteSpec();
    }
}
