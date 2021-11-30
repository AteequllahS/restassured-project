package com.cydeo.day8;

import com.cydeo.utility.LibraryAPI_BaseTest;
import com.cydeo.utility.LibraryAPI_Util;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class C5_libraryLoginTest extends LibraryAPI_BaseTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/library_credentials.csv", numLinesToSkip = 1)
    public void testLogin(String username, String password){

        System.out.println("username = " + username +" || password = +" + password);

        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParams("email", username)
                .formParam("password", password).
        when()
                .post("/login").
        then()
                .log().all()
                .statusCode(200)
                // why 200? in this particular api it decided to give you 200 since you are new
                //however in most common scenario, it is 201
                ;
    }

    @DisplayName("Librarian Should be able to add book")
    @Test
    public void testAddBook(){

/*
        //if you have many form parameter as body
        //you can use formParams method and pass Map Object instead

        String libraryToken =
                                given()
                                        .log().all()
                                        .contentType(ContentType.URLENC)
                                        .formParams("email", "librarian47@library")
                                        .formParam("password", "Sdet2022*").
                                when()
                                        .post("/login")
                                        .path("token");

        System.out.println("libraryToken = " + libraryToken);


        String libraryToken = LibraryAPI_Util.getToken();

        // first get library token by sending /login request
        //and save it (eventually make a method out of it)
        //optionally create a utility to generate random book map object


        Faker faker = new Faker();

        Map<String,Object> bookMap = new LinkedHashMap<>();

        bookMap.put("name",faker.book().title());
        bookMap.put("isbn",faker.code().isbn10());
        bookMap.put("year",faker.number().numberBetween(1900, 2021));
        bookMap.put("author",faker.book().author());
        bookMap.put("book_category_id",faker.number().numberBetween(1,20));
        bookMap.put("description",faker.chuckNorris().fact());

        System.out.println("bookMap = " + bookMap);


        //send request to POST/add_book and verify the body
        // "message": "The book has been created.",
*/
        String libraryToken = LibraryAPI_Util.getToken();


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
