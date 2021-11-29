package com.cydeo.day6;


import com.cydeo.pojo.Articles;
import com.cydeo.pojo.Articles2;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;


public class C06_NewsApi_DeserializationTest_Article2 {

    @BeforeAll
    public static void setup() {
        baseURI = "https://newsapi.org";
        basePath = "/v2";

    }

    @AfterAll
    public static void reset() {
        RestAssured.reset();
    }


    /*
     - GET:  https://newsapi.org/v2/top-headlines?country=us
     - Header : Authorization = Bearer 8118014ddec64af98132b56336eb242d
     */
    @Test
    public void testNews() {
        //de-serialization
        JsonPath jp =
                given()
                        .log().all()
                        .queryParam("country", "us")
                        .header("Authorization", "Bearer 8118014ddec64af98132b56336eb242d").
                        when()
                        .get("/top-headlines")
                        //.prettyPeek()
                        .jsonPath();

        // save first article into Article2 POJO
        Articles2 a2 = jp.getObject("articles[0]", Articles2.class);

        System.out.println("a2 = " + a2);


        //under source, get id and name
        System.out.println(a2.getSource().getId());
        System.out.println(a2.getSource().getName());

        //find out all articles if source id is not null
        List<Articles2> allArticles = jp.getList("articles", Articles2.class);

        for (Articles2 each : allArticles) {

            //while jackson converting null, in this case, it's storing null as String "null" from what we observed
            if (each.getSource().getId() != null) {
                System.out.println("each.getSource().getId() = " + each.getSource().getId());
                System.out.println("each.getAuthor() = " + each.getAuthor());
            }
        }
    }
}

