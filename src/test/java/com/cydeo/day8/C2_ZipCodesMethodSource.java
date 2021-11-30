package com.cydeo.day8;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class C2_ZipCodesMethodSource {

    /**
     * Write a method that return all zip codes for fairfax, VA
     * using https://api.zippopotam.us/us/va/fairfax"
     * and return List<String>
     *
     * write a parameterized test to test each every zipcode in fairfax VA
     * using GET https://api.zippopotam.us/us/{zip}
     * use @MethodSource to use the method you created above as source
     */

    @BeforeAll
    public static void setup() {
        baseURI = "https://api.zippopotam.us/";
        basePath = "/us";
    }

    @AfterAll
    public static void teardown() {
        reset();
    }


    //a method to get all zipcodes
    public static List<String> getAllZipCodes(){

        // send request to GET  "https://api.zippopotam.us/us/va/fairfax and store all zip codes
        // since this is not a @Test method, @BeforeAll will not affect this
        //and baseURI and basePath will not be set, so we need to do it ourselves
        //one way to do this is to provide full url directly
        //or we have use.baseURI("base uri here")   .basePath("basePath here")
        //in given section for one time use.

        List<String> allZips = get("https://api.zippopotam.us/us/va/fairfax")
                .path("places.'post code'");
        return allZips;
    }

    @ParameterizedTest(name = "Getting zip code {0}")
    //-> if we want a name to be displayed on ParameterizedTest,
    // we can open a parenthesis and give a message ending with index {0} to have the value of zipParam (in here)
    @MethodSource("getAllZipCodes")
    public void testAllZipCodes(String zipParam){

        given()
                .log().uri()
                .pathParam("zip", zipParam).
        when()
                .get("/{zip}").
        then()
                .statusCode(200) ;

    }
}
