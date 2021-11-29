package com.cydeo.day2;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


public class TestSpartan3 {

    @BeforeAll
    public static void setup(){
        //BaseURI : http://54.174.254.49:8000
        //BasePath : /api
        //Anything comes after actual resources

        RestAssured.baseURI = "http://54.174.254.49:8000";
        RestAssured.basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    //send request to GET /spartan and check status code 200 and content type json
    @Test
    public void testAllSpartan(){

        Response response = get("/spartans");

        //print the payload and return String
        //response.prettyPrint();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(ContentType.JSON.toString(), response.contentType());

        //to get the gender at index 0 ( as it is an Json Array )
        System.out.println("response.path(\"[0].gender\") = " + response.path("[0].gender"));

        //another way
        System.out.println("response.path(\"gender[2]\") = " + response.path("gender[2]"));

        System.out.println("response.path(\"name[2]\") = " + response.path("name[2]"));

        //get all athe id (instead of one ) and store it into List<Integer.
        List<Integer> idList = response.path("id");
        System.out.println("idList = " + idList);

    }

    // send request to GET  / spartans and provide accept header as application xml
    // send check status code 200 and content type application xml
    @Test
    public void testGetXMLResponse(){

        //RestAssured use method chaining extensively to combine all part of requests
        // and verify the response in one shot
        // here since we need to provide additional header information to get xml response
        // we will start learning some method chaining to see
        //how we can provide additional information to the request

        //when we need to provide keys and values to the header to get a result like : "Accept" in header part
        //given().header("Accept", "application/xml").when().get("/spartans");
        Response response = given()
                                //.header("Accept", "application/xml")
                                //.header("Accept", ContentType.XML)
                                .accept(ContentType.XML)
                            .when()
                                .get("/spartans");

        response.prettyPrint();

        //assertions
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(    ContentType.XML.toString(), response.contentType()  );
    }

    //http://54.174.254.49:8000/api/spartans/search?nameContains=Ea&gender=Male
    @Test
    public void testSearch(){

        // search for name contains Ea and gender = Male

        //the usage of path & queryParam
        Response response =
                given()
                    //.accept(ContentType.JSON) // no necessary as it already is in json type
                    .queryParam("nameContains", "Ea") //this is how you provide one query param
                    .queryParam("gender", "Male")   //this is how you provide another query param
                .when()
                    .get("/spartans/search");

        response.prettyPrint();

        // "totalElement" value
        response.path("totalElement");
        System.out.println("response.path(\"totalElement\") = " + response.path("totalElement"));

        //get the first person name (  from "content" part of json Array)
        response.path("content[0].name");
        //or
        response.path("content.name[0]");
        System.out.println("1st person's name = " + response.path("content[0].name"));

        //get the 2nd person id
        System.out.println("2nd peron's id = " + response.path("content.id[1]"));

        //store all names into the List
        List<String> allNames =  response.path("content.name"); // it gives null if json path is not correct
        System.out.println(allNames);
    }

    //GET http://54.174.254.49:8000/api/spartans/5
    @Test
    public void testOneSpartanPathParam(){

        //and example of METHOD CHAINING WE ALREADY LEARNED.
        String str = "B23 is Great!";
        str.toUpperCase().toLowerCase().replace("Great", "Excellent!").startsWith("B23");

        //the usage of pathParam
        Response r =
                given()
                    .pathParam("id", 6)
                        .log().all() // this will log everything about the request
                        //.log().uri() // only logs the url part
                .when()// this is optional here
                    .get("/spartans/{id}"); // the id value is taken from pathParam

        r.prettyPrint();


    }


}
