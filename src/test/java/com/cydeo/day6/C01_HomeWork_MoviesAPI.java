package com.cydeo.day6;

import com.cydeo.pojo.Movies;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
public class C01_HomeWork_MoviesAPI {

    /*
        Send this request GET http://www.omdbapi.com/?s=The Mandalorian
        create a POJO class for Movie
        match the java filed to json field
        and save the first one as Movie  POJO
        save all of them as list<POJO>

     */

    @BeforeAll
    public static void setup(){

        //BaseURI
        baseURI = "http://www.omdbapi.com";

    }

    @AfterAll
    public static void teardown(){
        reset();
    }

    @Test
    public void testMovies(){

        // http://www.omdbapi.com/?s=The%20Mandalorian&apikey=25d8fdf1
        JsonPath jp = given()
                            .log().uri()
                            .queryParam("apikey", "25d8fdf1")
                            .queryParam("s", "The Mandalorian").
                    when()
                            .get(""). // our url is already complete and no need to anything here
                            prettyPeek().
                            jsonPath() //JsonPath

                    ;

        // what is the json path to get the first object: Search[0]

        Movies m1 = jp.getObject("Search[0]", Movies.class);
        System.out.println("m1 = " + m1);

        //error cause : because we setTitle --> indicates that title is in lowercase in field (private String title) not Title.
    }
}
