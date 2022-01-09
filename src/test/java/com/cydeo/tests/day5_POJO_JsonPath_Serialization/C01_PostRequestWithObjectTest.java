package com.cydeo.tests.day5_POJO_JsonPath_Serialization;

import com.cydeo.utility.SpartanTestBase;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

/**
 * Serialization :
 *      Process of Converting from Java Object to Json (or any other text)
 * De-Serialization :
 *       Process of Converting from Json(any text) to Java Object
 */

@Tag("smoke")
public class C01_PostRequestWithObjectTest extends SpartanTestBase {

    /*
     POST /spartans
     content type is json
     body is
         {
             "name":"API POST",
             "gender":"Male",
             "phone":1231231231
         }


    Serialize => means to turn a java object into json (could be any String type)

    instead of providing above body in String ,
    We want to be able to provide the body as java object (like map or POJO)
    and let any kind of Serialization library convert that into String for us
     while sending the request

    */
    @Test
    public void testPostWithMap(){

        Map<String, Object> bodyMap  = new LinkedHashMap<>();

        bodyMap.put("name", "API POST");
        bodyMap.put("gender", "Male");
        bodyMap.put("phone", "1231231231");

        System.out.println("bodyMap = " + bodyMap);

        /*
        Jackson-databind is the library for serialization and de-serialization rest assured need it in dependency so
        it can automatically convert the java object to json without additional code
         */
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
    //use Faker
    @Test
    public void testPostWithMapWithRandomData(){

        //create random data instead of always creating with same body
        //optionally : create a utility out of it

        Faker faker = new Faker();

        Map<String, Object> bodyMap  = new LinkedHashMap<>();

        bodyMap.put("name", faker.name().firstName()    ); //name only accept 2 to 15 characters
        bodyMap.put("gender", faker.demographic().sex() ); // male or female
        bodyMap.put("phone", faker.number().numberBetween(5000000000L, 9999999999L));
        // we need to numerify to match the number format or between 500,000,000 - 999,999,999

        System.out.println("bodyMap = " + bodyMap);

        /*
        Jackson-databind is the library for serialization and de-serialization rest assured need it in dependency so
        it can automatically convert the java object to json without additional code
         */
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
}
