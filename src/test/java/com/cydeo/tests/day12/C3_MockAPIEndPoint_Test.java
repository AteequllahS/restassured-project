package com.cydeo.tests.day12;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class C3_MockAPIEndPoint_Test {

    @BeforeAll
    public static void setUp(){
        baseURI = "https://08417b48-ad54-410b-824a-01aa16f7aa91.mock.pstmn.io";
        //we did not add anything after baseURI in mock API endpoint so we dont have base path here

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @Test
    public void testNemo(){

        //get("/nemo").prettyPeek();

        given()
                .log().all().
        when()
                .get("/nemo").
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("home", is("cydeo"))
                ;

    }

    /**
     * When sending larger amount of data to the server it's common to
     *  use the multipart form data technique. Rest Assured provide methods
     *  called multiPart that allows you to specify a file, byte-array, input stream
     *  or text to upload
     */

    @Test
    public void testMultiPartFormDataFileUpload(){

        given()
                .log().all()
                .multiPart( new File("src/test/java/com/cydeo/tests/day12/Gherkin.png") ).
        when()
                .post("upload").
        then()
                .statusCode(200)
        ;
    }

}
