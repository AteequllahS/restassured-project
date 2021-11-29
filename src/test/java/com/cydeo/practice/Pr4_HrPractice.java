package com.cydeo.practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Pr4_HrPractice {

    @BeforeAll
    public static void setup(){
        baseURI = "http://54.174.254.49:1000";
        basePath = "/ords/hr";

    }

    @AfterAll
    public static void teardown(){
        RestAssured.reset();
    }

    @Test
    public void getAllJobs(){
        Response response = get("/jobs")
                            //.prettyPeek()
        ;

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType() );

        int countValue = response.path("count");
        assertEquals(19, countValue);

        String jobId = response.path("items[2].job_id");
        System.out.println("jobId = " + jobId);

        int fifthMinSalary = response.path("items[4].min_salary");
        System.out.println("fifthMinSalary = " + fifthMinSalary);

    }

    /*
        4. Create a test called testJobsWithQueryParam
           1. Send request to `GET /jobs?limit=5`
           2. log everything about the request
           3. verify `count` value is `5`
           4. verify the value of last `job_id` is `AD_VP`

 */
    @Test
    public void jobWithQueryParam(){

        Response response =
                            given()
                                    .log().all()
                                    .queryParam("limit", 5).
                            when()
                                    .get("/jobs")
                                    .prettyPeek();

        int count = response.path("count");
        assertEquals(5, count);

        String lastID = response.path("items[-1].job_id");
        assertEquals("AD_VP", lastID);
    }

//    5. create a test called testSingleJobWithPathParam
//      1. Send request to `GET /jobs/AD_VP`
//      2. log everything about the request
//      3. verify response is json and `job_title` is `Administration Vice President`

    @Test
    public void testSingleJobWithPathParam(){

        Response response =
                given()
                        .log().all()
                        .pathParam("jobId", "AD_VP").
                when()
                        .get("/jobs/{jobId}")
                        .prettyPeek();

        assertEquals(ContentType.JSON.toString(), response.contentType());

        assertEquals("Administration Vice President", response.path("job_title"));
    }


}
