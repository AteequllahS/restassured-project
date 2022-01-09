package com.cydeo.tests.day11;

import com.cydeo.pojo.Spartan;
import com.cydeo.utility.SpartanTestBase;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.Matchers.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class C2_NegativeTest extends SpartanTestBase{
    /**
     * POST request Json payload have below requirements
     *  name : 2-15 characters
     *  gender: Male or Female
     *  phone : 10 - 13 digits
     *
     *  Knowing that we already tested all positive scenario
     *  make sure it generate error with proper json body to reflect the error
     *  according to the negative test data you provided.
     *
     *  for example:
     *  {
     *     "name":"a",
     *     "gender":"Female",
     *     "phone":12325487521
     * }
     *
     *  we should expect 400 response
     *  {
     *     "message": "Invalid Input!",
     *     "errorCount": 1,
     *     "errors": [
     *         {
     *             "errorField": "name",
     *             "rejectedValue": "a",
     *             "reason": "name should be at least 2 character and max 15 character"
     *         }
     *     ]
     * }
     *
     * error count should be 1
     * "message" : "Invalid Input!"
     * "errorField": "name"
     *
     * from the response
     *
     */

    @DisplayName(" POST/spartans payload should be valid")
    @Test
    public void testAdd1DataNegativeScenario(){

        //prepare test body
        // using POJO

        Spartan invalidBody = new Spartan("A", "Male",12346579821L);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body( invalidBody ).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400) // we are expecting 400 for this negative scenario
                .body("message", is("Invalid Input!"))
                .body("errorCount", equalTo(1))
                .body("errors[0].errorField", is ("name"))
                ;
    }

    /*
    Write a ParameterizedTest for negative scenarios for `POST /spartans` payload

    1. prepare your data in csv file with below columns
       1. `name`, `gender`, `phone` , `expectedErrorCount`
       2. provide some invalid data by mixing and matching the errors and provide expected error count for that set of data in each row
       3. in your parameterized test drive the test using csv file along with your assertions
     */

    @DisplayName(" Negative Scenario DDT from CSV File")
    @ParameterizedTest
    @CsvFileSource(resources = "/negativePostData.csv", numLinesToSkip = 1)
    public void testNegativePostData( String nameParam, String genderParam, long phoneParam, int expectedCount){

        System.out.println("nameParam = " + nameParam);
        System.out.println("genderParam = " + genderParam);
        System.out.println("phoneParam = " + phoneParam);
        System.out.println("expectedCount = " + expectedCount);


        Spartan invalidBody = new Spartan(nameParam, genderParam,phoneParam);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body( invalidBody ).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400) // we are expecting 400 for this negative scenario
                .body("message", is("Invalid Input!"))
                .body("errorCount", equalTo(expectedCount))

        ;
    }


    @Tag("Defect110")
    @Disabled("Known issue : Defect110 or any other number based on policy")
    @DisplayName("Test Phone upper boundary in PUT/spartans/{id}")
    @Test
    public void testPutRequest_Negative_Scenario(){

        //from previous test, we found the bug, and now we are creating a test case to make sure to re test it
        //once its fixed. till that time, we can ignore/disable this test to avoid failure while running other tests
        /*
        According to the doc, the valid phone number should be 10-13 digits
        and anything out of that range should return 400 code
        a defect was found : the upper boundary is not working as expected
        it gives 500 code in a range between 15-18 while it should give 400 all the time if out of range
        so write a test to make sure you cover such negative scenario for phone number
         */

        int lastId = get("spartans/").path("id[-1]");
        System.out.println("lastId = " + lastId);

        //prepare body, wrong phone number
        Faker faker =  new Faker();
        long phone15Digit = faker.number().randomNumber(15, true);
        System.out.println("phone15Digit = " + phone15Digit);

        Spartan bodyWithInvalidPhone = new Spartan("Alex", "Male", phone15Digit);
        System.out.println("bodyWithInvalidPhone = " + bodyWithInvalidPhone);

        given()
                .log().uri() // just to see what id is used for logging purpose
                .pathParam("id", lastId)
                .contentType(ContentType.JSON)
                .body( bodyWithInvalidPhone ).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(400)
                .log().ifValidationFails()
                ;
        //the test fails because we expect it to show 400 but it gives 500 code as error

    }
}
