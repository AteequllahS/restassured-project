package com.cydeo.utility;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LibraryAPI_Util {

    //creat a method to get token

    public static String getToken(){

        return given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParams("email", "librarian47@library")
                .formParam("password", "Sdet2022*").
         when()
                .post("/login")
                .path("token");
    }

    public static Map<String, Object> getRandomBookMap(){
        Faker faker = new Faker();

        Map<String,Object> bookMap = new LinkedHashMap<>();

        bookMap.put("name",faker.book().title());
        bookMap.put("isbn",faker.code().isbn10());
        bookMap.put("year",faker.number().numberBetween(1900, 2021));
        bookMap.put("author",faker.book().author());
        bookMap.put("book_category_id",faker.number().numberBetween(1,20));
        bookMap.put("description",faker.chuckNorris().fact());

        System.out.println("bookMap = " + bookMap);

        return bookMap;

    }
}
