package com.cydeo.tests.day6;


import com.cydeo.pojo.Articles;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static io.restassured.RestAssured.*;


@Tag("smoke")
public class C06_NewsApi_DeserializationTest {

    @BeforeAll
    public static void setup(){
        baseURI = "https://newsapi.org";
        basePath = "/v2";

    }

    @AfterAll
    public static void reset(){
        RestAssured.reset();
    }



    /*
     - GET:  https://newsapi.org/v2/top-headlines?country=us
     - Header : Authorization = Bearer 8118014ddec64af98132b56336eb242d
     */
    @Test
    public void testNews(){
        //de-serialization
        JsonPath jp =
                given()
                        .log().all()
                        .queryParam("country", "us")
                        .header("Authorization","Bearer 8118014ddec64af98132b56336eb242d" ).
                when()
                        .get("/top-headlines")
                        //.prettyPeek()
                        .jsonPath() ;


        //Create a POJO class to represent Article
        //required fields : source, author, title
        //get a List<Articles> from json array
        //finally print out the name of author and title of article if source id is null

        // get the first Article into POJO
        Articles ar1 = jp.getObject("articles[0]", Articles.class);
        System.out.println("ar1 = " + ar1);

        //check if the source id=null or not
        // the "source" id is inside source Map field
        //we can use getter to private field to get the map
        // using this map, to get the value of the key

        System.out.println("ar1.getSource().get(\"id\") = " + ar1.getSource().get("id"));

       //get a List<Articles> from json array

        List<Articles> allArticles = jp.getList("articles", Articles.class);
        System.out.println("allArticles = " + allArticles);

        for (Articles each : allArticles) {

            //check if the source id is null
            if ( each.getSource().get("id") != null){
                System.out.println(  each.getAuthor() );
            }
        }


    }

}
