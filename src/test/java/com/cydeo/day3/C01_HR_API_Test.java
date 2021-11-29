package com.cydeo.day3;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


public class C01_HR_API_Test {

    @BeforeAll
    public static void setup(){
        baseURI = "http://54.174.254.49:1000";
        basePath = "/ords/hr";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @Test
    public void getAllJobs(){

        Response response = given().
                                //.log().all().

                            when().get("/jobs");

        response.prettyPrint();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(ContentType.JSON.toString(), response.contentType());


        int countValue = response.path("count"); // comes from Json result at postman
        Assertions.assertEquals(19, countValue);

        //second Job id
        String secondJobId = response.path("items[1].job_id");
        System.out.println("secondJobId = " + secondJobId);

        //the 4th min salary
        int fourthMinSalary = response.path("items[3].min_salary");
        System.out.println("fourthMinSalary = " + fourthMinSalary);

        List<String> allJobsTitle = response.path("items.job_title");
        System.out.println("allJobsTitle = " + allJobsTitle);


    }

    @Test
    public void getJobWithQueryParam(){

/*
        4. Create a test called testJobsWithQueryParam
           1. Send request to `GET /jobs?limit=5`
           2. log everything about the request
           3. verify `count` value is `5`
           4. verify the value of last `job_id` is `AD_VP`

 */
        Response response = given().log().all().
                                queryParam("limit", 5).
                            when().
                                get("/jobs");
        response.prettyPrint();

        int actualCount = response.path("count");
        Assertions.assertEquals(5, actualCount);

        //RestAssured use a groovy language when working with jsonpath
        // so any valid groovy syntax will work
        // in groovy, collection index can use minus to start from the back
        // so -1 will be the last item, -1 will be the second from the last

        String lastJob = response.path("items[4].job_id");
        System.out.println("last Job id = " + lastJob);
        // or
        String lastJob2 = response.path("items[-1].job_id"); // -1 last index in groovy

        Assertions.assertEquals("AD_VP", lastJob2);

    }

//    5. create a test called testSingleJobWithPathParam
//      1. Send request to `GET /jobs/AD_VP`
//      2. log everything about the request
//      3. verify response is json and `job_title` is `Administration Vice President`
    @Test
    public void testSingleJobWithPathParam(){

        Response response = given().log().all().
                                pathParam("job_id", "AD_VP").
                            when().
                                get("/jobs/{job_id}"). // it takes whatever we put as path parameter
                            prettyPeek(); // it prints the response as Response, returns Response (unlike prettyPrint)

        //response.prettyPrint();

        String actualJobTitle = response.path("job_title");
        Assertions.assertEquals("Administration Vice President",actualJobTitle);

        //there is one more way to print your response using prettyPeek.
        //prettyPeek, just like prettyPrint , will print the result
        //  unlike prettyPrint, it will return same Response object
        //  so you can keep chaining  the methods
        //response.prettyPeek();

    }


}
