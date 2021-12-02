package com.cydeo.day4;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class C01_Spartan_Put_Patch_Delete_PracticeTest extends SpartanTestBase {

    @Test
    public void testUpdate() {

/*
         -PUT /spartans/{id}
         -content type is json
         -body is
             {
                 "name":"New Body",
                 "gender":"Male",
                 "phone":5555555555
             }

        -expect status 204
 */

        String updatedBody = "             {\n" +
                "                 \"name\":\"Akbar H\",\n" +
                "                 \"gender\":\"Male\",\n" +
                "                 \"phone\":5555555555\n" +
                "             }";

        given().
                log().all().
                pathParam("id", "5").
                contentType(ContentType.JSON).
                body(updatedBody).
                when().
                put("/spartans/{id}").

                then().
                log().all().
                statusCode(equalTo(204));

        //homework : make another get request to check its actually updated


    }

    @Test
    public void testPartialUpdate() {

/*
         -Patch /spartans/{id}
         -content type is json
         -body is
             {
                 "name":"PartialUpdate",

             }

        -expect status 204
 */

        String updatedBody = "{\"name\":\"Updated Name\"}";

        given().
                log().all().
                pathParam("id", "5").
                contentType(    ContentType.JSON    ).
                body(updatedBody).
        when().
                patch("/spartans/{id}").

        then().
                log().all().
                statusCode(equalTo(204))
        ;

        //homework : make another get request to check its actually updated

    }

    @Test
    public void testDelete(){

    /*
        DELETE/spartans/{id}
     */

        // NOTE : we can only run this request one, because once the item is deleted it no more exist.
        //so we better run GET request at the end to make sure the DELETE happened

        //now if want to delete the last index and delete it.
        Response response =  get("/spartans");
        int lastId2 = response.path("id[-1]"); // this is groovy language

        //if we dont want to assign a variable to use it elsewhere, we can have it in one shot as below:
        int lastId = get("/spartans").path("id[-1]");

        given().
                log().all().
                pathParam("id", lastId).
        when().
                delete("/spartans/{id}").
        then().
                log().all().
                statusCode(204)

    //testing if it worked
    // we can additionally send another get request to this id and expect 204

        ;


    }
}
