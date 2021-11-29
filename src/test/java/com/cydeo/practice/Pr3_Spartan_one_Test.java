package com.cydeo.practice;

import static io.restassured.RestAssured.*;

import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class Pr3_Spartan_one_Test extends SpartanTestBase {


    @Test
    public void testOne(){

        Response response = get("/spartans/5");
        System.out.println(response.statusCode());
        response.getBody().prettyPrint();
        System.out.println(response.header("Content-Type"));
        System.out.println(response.getHeader("Content-type"));
        System.out.println(response.getHeader("Transfer-Encoding"));
        System.out.println(response.getHeader("Date"));
        System.out.println(response.getHeader("Keep-Alive"));
        System.out.println(response.getHeader("Connection"));

        System.out.println("========================================================================================");

        System.out.println(response.getContentType());
        response.getBody().prettyPrint();
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
        System.out.println(ContentType.JSON);
        System.out.println("response.getStatusLine() = " + response.getStatusLine());
        System.out.println(response.getBody().prettyPrint());
        System.out.println(response.getBody().prettyPrint().replace("Male", "Female"));

        System.out.println("=======================================================================================");

        List<String> allHeaders = new ArrayList<>();

//        for (Header allHs : response.getHeaders()) {
//           allHeaders.add(allHs.getName());
//
//        }
//        System.out.println(allHeaders);

        response.getHeaders().forEach(each -> allHeaders.add(each.getName()));
        System.out.println(allHeaders);

        System.out.println("=======================================================================================");

        System.out.println("response.path(\"id\") = "+response.path("id"));
        System.out.println("response.path(\"name\") = "+response.path("name"));

        String gender = response.path("gender");
        System.out.println(gender);
    }
}
