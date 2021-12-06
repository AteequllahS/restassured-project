package com.cydeo.tests.day11;

import com.cydeo.utility.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

@DisplayName(" Json Schema validation tests")
public class C1_JsonSchemaValidationPracticeTest extends SpartanTestBase {

    @DisplayName("Test Get/ spartans/search schema")
    @Test
    public void testSearchSchema(){

        //http://54.174.254.49:8000/api/spartans/search?nameContains=Ea&gender=Male
        //when there are more than couple key value pairs like many headers, or many query params, form params
        //RestAssured provide method to let you pass it as a map in one shot headers( Map<> )

        Map<String, Object> queryMap = new LinkedHashMap<>();
        queryMap.put("nameContains", "Ea" ) ;
        queryMap.put("gender", "Male" ) ;


        given()
                .log().uri()
                .queryParams( queryMap ).
        when()
                .get("/spartans/search").
        then()
                .statusCode(200)
                .body( matchesJsonSchemaInClasspath( "SearchSpartanSchema.json"))
        ;


    }

}
