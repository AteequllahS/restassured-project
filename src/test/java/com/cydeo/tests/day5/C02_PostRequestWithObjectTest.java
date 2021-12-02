package com.cydeo.tests.day5;

import com.cydeo.utility.SpartanTestBase;
import com.cydeo.utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;


/**
 * Serialization :
 *      Process of Converting from Java Object to Json (or any other text)
 * De-Serialization :
 *       Process of Converting from Json(any text) to Java Object
 */

@Tag("smoke")
public class C02_PostRequestWithObjectTest extends SpartanTestBase {

    /*
     POST /spartans
     content type is json
     body is
         {
             "name":"API POST",
             "gender":"Male",
             "phone":1231231231
         }

    Serialize => means to turn a java object into json

    instead of providing above body in String ,
    We want to be able to provide the body as java object (like map or POJO)
    and let any kind of Serialization library convert that into String for us
    while sending the request

    */
    @Test
    public void testPostWithMapWithMethodFaker(){

        // calling method fro utility
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMapBody();

        given().
                log().all().
                contentType(ContentType.JSON).
                body( bodyMap ).
        when().
                post("/spartans").
        then().
            log().all().
            statusCode(201);

    }

    //send request to  PUT /spartans{id} with Random Map Body
    @Test
    public void testUpdate1DataWithRandomBody(){

        //instead of guessing what id exists in my server
        // I will send request to get all spartans and get last json object id

        int lastId = get("/spartans").path("id[-1]") ;
        System.out.println("lastId = " + lastId);

        //Update body:

        Map<String, Object> updatedBodyMap = SpartanUtil.getRandomSpartanMapBody();

        given().
                log().all().
                pathParam("id", lastId).
                contentType( ContentType.JSON).
                body( updatedBodyMap).
        when().
                put("/spartans/{id}").
        then().
                log().all().
                statusCode(204) ;


        //verify if it was added
        System.out.println("Assertion of the updated body : ");
        get("/spartans/"+lastId).prettyPrint();
    }
}
