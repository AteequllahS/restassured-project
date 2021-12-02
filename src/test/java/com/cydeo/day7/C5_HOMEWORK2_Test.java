package com.cydeo.day7;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;

/**
 HOMEWORK 3:

 *Create a csv file called "math.csv" under resource folder
 * add 3 columns num1, num2, expectedResult
 * add valid data for addition num1_num2=expectedResult
 * creat a @ParameterizedTest with above CsvFileSource and skip header
 * assert addition num1 +  num2 = expectedResult
 * make sure to change the number and see failed tests

 */

public class C5_HOMEWORK2_Test {

    @BeforeAll
    public static void setup(){
        baseURI = "https://api.zippopotam.us";
        basePath = "/us";
    }

    @AfterAll
    public static void reset(){
        RestAssured.reset();
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/state_city.csv", numLinesToSkip = 1)
    public void task2( String state, String city){

        System.out.println("state = " + state);
        System.out.println("city = " + city);

        given()
                .pathParam("state", state)
                .pathParam("city", city).

        when()
                .get("/{state}/{city}").
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);

    }

    //=========================================================

 }
