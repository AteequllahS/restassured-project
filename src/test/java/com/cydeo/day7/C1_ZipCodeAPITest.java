package com.cydeo.day7;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;


public class C1_ZipCodeAPITest {

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

    /*
     Homework 1:
     *   in one test
     *   send request to GET https://api.zippopotam.us/us/va/fairfax
     *   log request all parts
     *   use va and fairfax as path variables with name state / city
     *   send get request verify
     *   status code 200 , json format


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
     *             "place name": "Fairfax",
     *             "longitude": "-77.3242",
     *             "post code": "22030",
     *             "latitude": "38.8458"
     *         }
     *  de-serialize the first response into Place pojo and print it out
     *  save all the place json array into List<Place> and print it out.



     HOMEWORK 2:

     * creat a file called state_city.csv underd resources folder
     * it has two columns state, city
     * add some valid data
     *
     * send request to GET : https://api.zippopotam.us/us/{state}/{city}
     * log request uri
     * use state and city as path variables with name state/city
     * for actual value of path params get it from csv file
     * send get request verify
     * status code 200 , json format
     *
     */
}
