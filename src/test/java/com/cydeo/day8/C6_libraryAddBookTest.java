package com.cydeo.day8;

import com.cydeo.utility.LibraryAPI_BaseTest;
import com.cydeo.utility.LibraryAPI_Util;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class C6_libraryAddBookTest extends LibraryAPI_BaseTest {

       @DisplayName("Librarian Should be able to add book")
    @Test
    public void testAddBook(){

        // first get library token by sending /login request
        String libraryToken = LibraryAPI_Util.getToken();

        //send request to POST/add_book and verify the body
        // "message": "The book has been created.",
        given()
                .log().all()
                .header("x-library-token", libraryToken)
                .contentType(ContentType.URLENC)
                .formParams( LibraryAPI_Util.getRandomBookMap() ).
        when()
                .post("/add_book").
        then()
                .log().all()
                .body("message", is("The book has been created."))
        ;


    }
}
