package com.cydeo.utility;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;


public class LibraryAPI_BaseTest {

    @BeforeAll
    public static void setup(){
        baseURI= "https://library2.cybertekschool.com";
        basePath= "/rest/v1";

        //calling our created connection method in this class
        createLibraryConnection();

    }


    @AfterAll
    public static void tearDown() {
        reset();
        DB_Util.destroy();
    }


    //connection method for library
    public static void createLibraryConnection() {

        String url = ConfigReader.read("library2.database.url");
        String username = ConfigReader.read("library2.database.username");
        String password = ConfigReader.read("library2.database.password");
        DB_Util.createConnection(url, username, password);

    }

}
