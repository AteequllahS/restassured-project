package com.cydeo.tests.day3_HamCrestMatcher;

import com.cydeo.utility.SpartanTestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("smoke")
public class C04_Spartan_Post_Put_Patch_Request_Test extends SpartanTestBase {

    @Test
    public void testAdd1DataStringBody(){

/*
        {
            "name":"porana",
            "gender":"Male",
            "phone":1234456789452
        }

        -POST /spartan
        -content type is json
        -body is :



         * expect status 201
                * body json format
         * response body
         * {
         *     "success": "A Spartan is Born!",
         *     "data": {
         *         "id": 544,
         *         "name": "API POST",
         *         "gender": "Male",
         *         "phone": 1231231231
                      }
         }

 */

        String strBody = "{\n" +
                "                    \"name\":\"Api Prac\",\n" +
                "                    \"gender\":\"Male\",\n" +
                "                    \"phone\":1231231231\n" +
                "          }" ;

        given().
                log().all().
                contentType( ContentType.JSON ). // content type header is set json
                body( strBody ).
        when().
                post("/spartans").
        then().
                log().all().
                statusCode(201)
                .contentType( ContentType.JSON )
                .body("success", is("A Spartan is Born!")   )
                .body("data.name", is("API POST")    )
                // you might get error comparing int to long
                .body("data.phone.toLong()", is(1231231231L ) )
        // permanent solution for converting the phone number we got to long ,
        //      is using groovy method to convert int to long using data.phone.toLong()

        // asserting the response format is json
        ;
    }

    // FOR THE REST OF THE TIME , PLEASE TRY OUT PUT AND PATCH IN SAME EXACT WAY
    //  SPOILER HERE
    @Test
    public void testUpdate1DataStringBody(){

        /**
         PUT /spartans
         content type is json
         body is
         {
         "name":"New Body",
         "gender":"Male",
         "phone":5555555555
         }
         *
         * expect status 204
         */
        String strBody = "{\n" +
                "             \"name\":\"New Body\",\n" +
                "             \"gender\":\"Male\",\n" +
                "             \"phone\":5555555555\n" +
                "         }" ;


        given()
                .log().all()
                .pathParam("id", 2)
                .contentType( ContentType.JSON ) // content type request header set to json, telling the server what kind of data you are sending
                .body(strBody). // this is how we provide body
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .statusCode( 204 )

        ;
    }
    @Test
    public void testPartialUpdate1DataStringBody(){

        /**
         PATCH /spartans
         content type is json
         body is
         {"phone":5555555555}
         *
         * expect status 204
         */
        String strBody = "{\"phone\":5555555555}" ;


        given()
                .log().all()
                .pathParam("id", 2)
                .contentType( ContentType.JSON ) // content type request header set to json, telling the server what kind of data you are sending
                .body(strBody). // this is how we provide body
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode( 204 )

        ;




    }


}
