package com.cydeo.tests.day10;

import com.cydeo.utility.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class JsonSchemaValidationTest extends SpartanTestBase {

    @DisplayName( "Testing Get/; spartan/{id} response schema")
    @Test
    public void testSingleSpartanSchema(){

        //send request to GET /spartans/{id}
        //and verify the response json match the schema document
        // under resources folder
        // with the name of SingleSpartanSchema.json

        int firstIdInAPP = get("/spartans").path("id[0]"); // first id

        given()
                .pathParam("id", firstIdInAPP)
                .log().uri().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .statusCode(200)
        .body( matchesJsonSchemaInClasspath("SingleSpartanSchema.json") )
                ;

    }



}
