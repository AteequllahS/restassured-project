package com.cydeo.day1;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class TestSpartanAPI {

    @Test
    public void testHello(){

        System.out.println("Hello world");

        //send request to below request url and save the response
        //http://3.93.163.26:8000/api/hello

        Response response = get("http://54.174.254.49:8000/api/hello");
        System.out.println("response.statusCode() = " + response.statusCode());

        // to print the body, this is the easiest way.
        response.prettyPrint();

        //assert status code is 200
        Assertions.assertEquals(200, response.statusCode());


    }

}
