package com.cydeo.tests.day11;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public class C3_XMLResponseTest {

    @BeforeAll
    public static void setup(){

        baseURI = "http://ergast.com";
        basePath = "/api/f1";

    }

    @AfterAll
    public static void tearDown(){

        RestAssured.reset();
    }


    //http://ergast.com/api/f1/drivers/{driverId}    driverId =? -> abate
    @DisplayName("Test GET / drivers/{driverId} xml response")
    @Test
    public void testSingleDriverXMLResponse(){

        given()
                .pathParam("driverId", "abate")
                .log().uri().
        when()
                .get("/drivers/{driverId}").
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("MRData.DriverTable.Driver.GivenName", is("Carlo") ) // xml path is followed by period.
                .body("MrData.DriverTable.Driver.Nationality",  is ("Italian"))
                ;

    }

    //http://ergast.com/api/f1/drivers/{driverId}    driverId =? -> abate
    @DisplayName("Test GET / drivers/{driverId} xml response")
    @Test
    public void testSingleDriverXMLResponse2(){

        // same as JsonPath, we have method for xml to store into an String that later we can manipulate.
        Response response = given()
                                .pathParam("driverId", "abate")
                                .log().uri().
                            when()
                                .get("/drivers/{driverId}")

        ;

        //just like jsonPath class, there is specialized class fro xml --> XMLPath
        XmlPath xp = response.xmlPath();

        //save the FirstName field into String variable
        String firstName = xp.getString("MRData.DriverTable.Driver.GivenName");
        System.out.println("firstName = " + firstName);

        String nationality = xp.getString("MRData.DriverTable.Driver.Nationality");
        System.out.println("nationality = " + nationality);

        //================================================================================================================
        //now, getting the attribute of an element (only available in XML)
        //if you element tag is <Driver> and attribute is gender/>  Driver.@gender
        //TagName.@attributeName syntax

        //get the value of MRData.DriverTable.@driverId=abate
        String driverIdAttribute = xp.getString("MRData.DriverTable.@driverId");
        System.out.println("driverIdAttribute = " + driverIdAttribute);


        String urlAttribute = xp.getString("MRData.DriverTable.Driver.@url");
        System.out.println("urlAttribute = " + urlAttribute);

    }

    @DisplayName("Test GET/drivers xml response")
    @Test
    public void getAllDriverXMLResponse() {

        // as it does not have any parameter, we can directly assign it to xmlPath without given() section.

        XmlPath xp = get("/drivers").xmlPath();
        String firstDriver = xp.getString("MRData.DriverTable.Driver[0].GivenName"); //first Driver name
        System.out.println("firstDriver = " + firstDriver);

        //get all the GivenNames of all drivers
        List<String> allGivenNames = xp.getList("MRData.DriverTable.Driver.GivenName", String.class); // giving generic is optional
        System.out.println("allGivenNames = " + allGivenNames);

        //getting all driverId attributes
        List<String> allDriverIdAttributes = xp.getList("MRData.DriverTable.Driver.@driverId", String.class);
        System.out.println("allDriverIdAttributes = " + allDriverIdAttributes);


// Homework , Create a method to get RaNdom valid driverId
        // so you can pass to the test /drivers/{driverId}  instead of guessing valid driver id

        // Explore the rest of the Collection
        // for example :
        // http://ergast.com/api/f1/2021/drivers
        // practice getting xml element text value , xml element attributes
        // same goes for the rest of the collection.

        // Homework 2 :
        // Send this request in Movie API get xml response by using query param r=xml
        // GET http://www.omdbapi.com/?t=The Mandalorian&r=xml?apiKey=YourKeyGoesHere

        // print out all the movie attributes

        // Send request GET http://www.omdbapi.com/?s=The Mandalorian&r=xml?apiKey=YourKeyGoesHere
        // verify root element attribute  totalResults="11"
        // store all the titles on List<String> and print.
        // verify the size of list match the attribute totalResults="11"


        // Homework 3 :
        // Send request to  GET /spartans provide accept header to get XML response
        // practice getting the value of xml elements for single elements
        // or list of elements


        // Homework 4 :
          /*
            Send GET Request to https://vpic.nhtsa.dot.gov/api/vehicles/GetMakeForManufacturer/988?format=xml
            and verify the
            count element text is 2
            message Results returned successfully
            first Make_ID is  474 , Make_Name Honda
            */

    }
}
