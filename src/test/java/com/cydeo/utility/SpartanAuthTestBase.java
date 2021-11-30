package com.cydeo.utility;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.reset;

public class SpartanAuthTestBase {

    // this class will serve as Base class to set up BaseURI and BasePath
    //and cleans up after all test for any Spartan related test classes

    @BeforeAll
    public static void setup(){
        //BaseURI : http://54.174.254.49:8000
        //BasePath : /api
        //Anything comes after actual resources

        RestAssured.baseURI = "http://54.174.254.49:7000";
        RestAssured.basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }
}
