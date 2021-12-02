package com.cydeo.day5;

import com.cydeo.pojo.Spartan;
import com.cydeo.utility.SpartanTestBase;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;


public class C03_Post_PutRequestWith_POJOTest extends SpartanTestBase{

    /*
     POST /spartans
     content type is json
     body is
         {
             "name":"API POST",
             "gender":"Male",
             "phone":1231231231
         }

         we learned how to represent json body using Map
         and let Jackson data-bind library to serialize it into jason when sending to the server

         Another common way of representing json data is using special purpose Java Object created from A class that
         have filed matched to json keys and have:
            -setters and
            -getters

         This type of Object, sole purpose is to represent data, known  POJO known as POJO class
         it's used for creating POJO, just like you create any other object

         for example : in order to represent a json data with 3 keys ( name, gender, phone )
         we can create a java class with 3 instance fields with same name

         given().
                log().all().
                contentType(ContentType.JSON).
                body( bodyMap ).
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(201);
======================================================================================================================*/

    @Test
    public void testPost_WithPOJOAsBody(){

        Spartan sp1 = new Spartan("B23 POJO", "Male", 5555555555L);
        System.out.println("sp1 = " + sp1);

        //now we are going to use this body in POST request
        // and expect jackson-databind to convert that to json string when sending as body
        //so it can be added to the server as new data
        // only thing different in here is using POJO as body
        given().
                log().all().
                contentType(ContentType.JSON).
                body( sp1 ). // passing the pojo object here
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(201);
    }

    /*

    1
     HOMEWORK: Create a method under SpartanUtil class
     called getRandomSpartanPojo()
     return Spartan object with randomize field values

    2
    HOMEWORK : Use POJO for Update 1 Data PUT Request
    use your getRandomSpartanPOJO utility method for body

     */

}
