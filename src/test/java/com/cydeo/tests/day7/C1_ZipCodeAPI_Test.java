package com.cydeo.tests.day7;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;

@Tag("smoke")
public class C1_ZipCodeAPI_Test {

    @BeforeAll
    public static void setup(){
        baseURI = "https://api.zippopotam.us";
        basePath = "/us";

    }

    @AfterAll
    public static void reset(){

        RestAssured.reset();
    }


    @Test
    public void testZipToCity(){

        //https://api.zippopotam.us/us/22030
        // log your request, provide 22030 as path variable|param
        //send get request and assert status is 200

        //assert response is in json format
        //assert "country": "United States"
        //assert "state" : "Virginia"
        //assert "zip code" : 22030
        //assert "place name" : "Fairfax"

        given()
                .log().all().pathParam("zip", 22030).
        when()
                .get("/{zip}").
        then()
                .log().all()
        .statusCode( is(200) )
        .contentType( ContentType.JSON)
        .body("country", is ("United States"))
        .body("places[0].state", equalToIgnoringCase("virginia"))

//if we have two word with an space in between, we need to put them inside single quote after double quotation
        .body("'post code'", equalTo ("22030"))
        .body("'country abbreviation'", is ("US"))
        .body("places[0].'place name'", is("Fairfax"))
                ;
    }

    /*
        create a parameterized test to check:
        - using given zipcodes 22030, 10019, 12345, 10000, 19152
        - send out request to
            GET https://api.zippopotam.us/us/{zip}
            test the status code is 200
     */

    //Data driven example
    @ParameterizedTest
    @ValueSource(ints = { 22030, 10019, 12345, 10000, 19152 } )
    public void testZipToCityParameterized( int zipParam){

        System.out.println("zipParam = " + zipParam);

        given()
                .log().uri()
                .pathParam("zip", zipParam).
        when()
                .get("/{zip}").
        then()
                .statusCode(200) ;

    }

    //using CSV file for data driven testing
    @ParameterizedTest
    //@ValueSource(ints = { 22030, 10019, 12345, 10000, 19152 } )
    @CsvFileSource(resources = "/zipcodes.csv", numLinesToSkip = 1)
    public void testZipToCityCsvFileSource( int zipParam){

        System.out.println("zipParam = " + zipParam);

        given()
                .log().uri()
                .pathParam("zip", zipParam).
                when()
                .get("/{zip}").
                then()
                .statusCode(200) ;

    }




}
