package com.cydeo.practice;

import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class Pr5_Spartan_HamcrestMatcher extends SpartanTestBase {

    @Test
    public void getOneSpartan(){

        /*
            -in one shot,
            -send request to Get / spartans/{id} with id of 2
            -log everything
            -verify status Code is 200
            -contentType is json
            -json body : id is 2,
            -name: Nels

         */

        given()
                .log().all()
                .pathParam("SpId", 2).
                accept(ContentType.JSON).
        when()
                .get("/spartans/{SpId}").prettyPeek().
        then()
                .statusCode(is(200))
                .contentType( is (ContentType.JSON.toString()))
                .body("id", is(2))
                .body("name", is ("Nels"));

    }

    @Test
    public void testSearch(){
        //http://54.174.254.49:8000/api/spartans/search?nameContains=Ea&gender=Male
        // verify status code is 200
        // content Type is json
        // "totalElement" : 3
        // jsonArray has size of 3
        //all names has item Sean
        // every gender is Male
        // every name should contain ignoring case "ea"

        given()
                .log().uri()
                .accept(ContentType.JSON)
                .queryParam("nameContains", "Ea")
                .queryParam("gender", "Male").
        when()
                .get("/spartans/search").
        then()
                .log().all()
                .statusCode(200)
                .body("totalElement",is(2))
                .body("content", hasSize(2))
                .body("content.gender", everyItem(is("Male")))
                .body("content.name", hasItem("Sean"))
                .body("content.name", everyItem( containsStringIgnoringCase("ea")))
               ;

    }


}
