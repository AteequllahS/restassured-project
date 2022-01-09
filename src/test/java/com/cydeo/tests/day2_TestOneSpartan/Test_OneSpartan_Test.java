package com.cydeo.tests.day2_TestOneSpartan;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Test_OneSpartan_Test {

    @Test
    public void oneSpartan(){

        //GET   http://54.174.254.49:8000/api/spartans/5
        Response response = get("http://54.174.254.49:8000/api/spartans/5");

        int statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);

        //to print the body
        //prettyPrint, prints itself no need for print statement
        response.prettyPrint();

        //another way to print the body asString
//        System.out.println("response.asString() = " + response.asString());

        //another way to print the body getBody
//        System.out.println("response.getBody() = " + response.getBody().prettyPrint());


        //==================getting the header ====================

        //getting header from the response.header
        System.out.println("response.header(\"Content-Type\") = " + response.header("Content-Type"));

        //getting header from the response.getHeader
        System.out.println("response.getHeader(\"Content-Type\") = " + response.getHeader("Content-Type"));

        //============================ using getMethods ======================================

        //try to get Data Header, Keep-Alive header, Connection (   using .header()  )

        System.out.println("response.header(\"Date\") = " + response.header("Date"));
        System.out.println("response.header(\"Keep-Alive\") = " + response.header("Keep-Alive"));
        System.out.println("response.header(\"Connection\") = " + response.header("Connection"));

        System.out.println();
        //getting all headers
        System.out.println("response.getHeaders() = " + response.getHeaders());

    }

    @Test
    public void testContentTypeHeader(){

        //GET   http://54.174.254.49:8000/api/spartans/5
        Response response = get("http://54.174.254.49:8000/api/spartans/6");

        //RestAssured has special support for common header like contentType with method
        System.out.println("response.getContentType() = " + response.getContentType());

        System.out.println("Content Type() = " + response.contentType());

        Assertions.assertEquals("application/json", response.getContentType());

        //Different type of content type is represented in
        //import io.restassured.http.ContentType
        System.out.println("ContentType.JSON = " + ContentType.JSON);
        System.out.println("ContentType.XML = " + ContentType.XML);
        System.out.println("ContentType.TEXT = " + ContentType.TEXT);
        System.out.println("ContentType.URLENC = " + ContentType.URLENC);

        //assertion: need to convert enum type to string type for this assertion
        Assertions.assertEquals(    ContentType.JSON.toString(), response.contentType()     );

    }

    @Test
    public void testAllHeaders(){

        //GET   http://54.174.254.49:8000/api/spartans/5
        Response response = get("http://54.174.254.49:8000/api/spartans/6");

        List<String> expectedHeaders = new ArrayList<>(Arrays.asList("Content-Type", "Transfer-Encoding", "Date", "Keep-Alive", "Connection"));

        List<String> actualHeaders = new ArrayList<>();

        for (Header eachHeader : response.headers()) {
            actualHeaders.add(eachHeader.getName());
        }

        System.out.println("expectedHeaders = " + expectedHeaders);
        System.out.println("actualHeaders = " + actualHeaders);

        //assertion of all headers
        Assertions.assertEquals(expectedHeaders, actualHeaders);

    }

    @Test
    public void testJSONBody(){

/*
        {
            "id": 6,
            "name": "Tedmund",
            "gender": "Male",
            "phone": 2227140732
        }
*/
        String url = "http://54.174.254.49:8000/api";

        Response response = get(url+"/spartans/6");//getting the 6th spartan

        response.prettyPrint();

        //we cal also assign it to a String variable.
        String pretty = response.prettyPrint();
        System.out.println("pretty = " + pretty);

        //just like navigating through html using xpath to get certain element you can
        //navigate through json to the value of certain key using jsonpath.

        //the easiest way to get the value using jsonpath is using "path" method from response object
        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"gender\") = " + response.path("gender"));
        System.out.println("response.path(\"phone\") = " + response.path("phone"));

        //save the json value into variables
        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        Assertions.assertEquals(id, 6);
        Assertions.assertEquals(name, "Tedmund");
        Assertions.assertEquals(gender, "Male");
        Assertions.assertEquals(phone, 2227140732L); //adding an L if used as long


    }
}
