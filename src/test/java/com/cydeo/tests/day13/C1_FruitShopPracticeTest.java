package com.cydeo.tests.day13;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class C1_FruitShopPracticeTest {

    /*
     * FruitShop Exercise :
     *
     * 1. Get All customers
     *    - verify you get 200
     *    - then extract first customer id
     *
     * 2. Get All the orders of that customer
     *    - verify status 200
     *    - then extract first order id
     */
    @BeforeAll
    public static void setup(){
        baseURI = "https://api.predic8.de:443/shop";

    }

    @AfterAll
    public static void teardown(){
        reset();
    }


    @Test
    public void testExtractPractice(){

        // Send GET /customers and verify 200 then get the first id (customer url)

        Response response = get("/customers/");
        Assertions.assertEquals(200, response.statusCode());


        String firstCustomerURL = response.path("customers.customer_url[0]");
        System.out.println("firstCustomerURL = " + firstCustomerURL);

        // Can we verify in method chain then extract the data after verification in method chain
        // once you get to then section , it will return ValidatableResponse Object
        // we can use extract() method after that to continue to extract data out if needed

        // do it in one chain
        // extract() method can be used after then section
        // to extract data out if needed , in the same chain

        String firstURL = given()
                                .log().uri().
                          when()
                                .get("/customers/").
                          then()
                                .statusCode(200)
                                .extract() // from here, it will stop validating and will extract response data
                                .response() // this will return response object
                                .path("customers.customer_url[0]")//return first customer url as String
                                ;

        System.out.println("firstURL = " + firstURL);

    }

    @Test
    public void testBreakingTheMethodChain(){

        //if we go to cucumber, we need to break this chain apart as there is a separate when, then condition on scenario
        //so we neeed to break this chain into 3 part
        given()
                .log().uri()
                .contentType(ContentType.JSON).
        when()
                .get("/customers/").
        then()
                .statusCode(200);

        //as the given() part is RequestSpecification, the intelliJ will introduce automatically if we dont do it manually
        RequestSpecification givenPart = given().log().uri().contentType(ContentType.JSON);

        //when() part is the Response part
        Response whenPart = givenPart.when().get("/customers/");

        //this is validatableResponse part
        ValidatableResponse thenPart = whenPart.then().statusCode(200);

        //now lets verify content type is json for the Response
        thenPart.contentType(ContentType.JSON);


    }
}
