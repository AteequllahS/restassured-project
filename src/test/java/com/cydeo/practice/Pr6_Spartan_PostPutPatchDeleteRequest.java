package com.cydeo.practice;

import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Pr6_Spartan_PostPutPatchDeleteRequest extends SpartanTestBase {

    @Test
    public void add1Spartan(){

        String strBody = "{\n" +
                "                    \"name\":\"Api Prac\",\n" +
                "                    \"gender\":\"Male\",\n" +
                "                    \"phone\":1231231231\n" +
                "          }" ;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(strBody).
        when()
                .post("/spartans").prettyPeek().
        then()
                .log().all()
                .statusCode(201)
                .body("success", is( "A Spartan is Born!") )
                .body("data.name", is("Api Prac"))
                .body("data.gender", is("Male"));


        System.out.println("==========================================================================");


        given()
                .log().uri()
                .pathParam("lastId", get("spartans").path("id[-1]"))
                .when()
                .get("spartans/{lastId}").prettyPeek();

        System.out.println("==========================================================================");


        System.out.println("get(\"spartans\").path(\"id[-1]\") = " + get("spartans").path("id[-1]"));




    }


}
