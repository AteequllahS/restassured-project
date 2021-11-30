package com.cydeo.day7;


import com.cydeo.pojo.Place;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;


public class C4_HOMEWORK1 {
/**
 Homework 1:
 *   in one test
 *   send request to GET https://api.zippopotam.us/us/va/fairfax
 *   log request all parts
 *   use va and fairfax as path variables with name state / city
 *   send get request verify
 *   status code 200 , json format

 */
//=====================================================================================================================
    @BeforeAll
    public static void setup(){
        baseURI = "https://api.zippopotam.us";
        basePath = "/us";
    }

    @AfterAll
    public static void reset(){
        RestAssured.reset();
    }
//====================================================================================================================
    @Test
    public void task1(){

        given()
                .log().all()
                .pathParam("state", "ca")
                .pathParam("city", "San Diego").
        when()
                .get("/{state}/{city}").
        then()
                .log().all()
                .statusCode(is (200) )
                .contentType( ContentType.JSON )
                .body("country", equalTo("United States"))
                .body("state", equalToIgnoringCase("California"))
                .body("'place name'",  equalTo("San Diego"));

    }
//====================================================================================================================
/*
    HOMEWORK 2
     *   in another test
     *   send same request and store the response object
     *   get JsonPath object from the response
     *   print last place name
     *   print all zip codes after storing it into the list
     *   create a pojo called Place to represent place json object
     *      with these specific fields :
     *      - name as String
     *      - postCode as int
     *      - latitude as float
     *      - longitude as float
     *      {
     *             "place name": "San Diego",
     *             "longitude": "-77.3242",
     *             "post code": "22030",
     *             "latitude": "38.8458"
     *         }
     *  de-serialize the first response into Place pojo and print it out
     *  save all the place json array into List<Place> and print it out.
 */
    @Test
    public void task1_2(){

        JsonPath jp = given()
                .log().uri()
                .pathParam("state", "va")
                .pathParam("city", "fairfax").
        when()
                .get("/{state}/{city}")
                .jsonPath();

        Place p1 = jp.getObject("places[0]", Place.class);
        System.out.println("p1 = " + p1);

        List<Place> placeList = jp.getList("places", Place.class);
        System.out.println("placeList = " + placeList);

        List<String> allCities = new ArrayList<>();
        List<String> allZipCodes = new ArrayList<>();

        for (Place each : placeList) {
            allCities.add(each.getPlaceName() );
            allZipCodes.add(each.getPostCode() );

        }

        System.out.println("all cities name = " + allCities);
        System.out.println("Last city name = " + allCities.get(allCities.size() - 1));
        System.out.println("all Zip Codes = " + allZipCodes);


    }


 }
