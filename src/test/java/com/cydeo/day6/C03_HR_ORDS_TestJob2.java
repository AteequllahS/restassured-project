package com.cydeo.day6;

import com.cydeo.pojo.Job;
import com.cydeo.pojo.Job2;
import com.cydeo.utility.HrORDSTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class C03_HR_ORDS_TestJob2 extends HrORDSTestBase {

        // GET /jobs

    @Test
    public void testJobs(){

        JsonPath jp = given()
                            .log().uri()
                        .when()
                            .get("/jobs")
                            //.prettyPeek()
                            .jsonPath(); // we should end it with JsonPath if we started with JsonPath

        //objective:
        // we want to de-serialize first json object from jason array
        //we want to be able to follow java naming convention
        //we want to ignore the json field we do not care about : link field
        // -> we need to instruct Json data bind that we do not care about link in jobs
        //we are now creating a POJO

        Job2 j1 = jp.getObject("items[0]", Job2.class); //items come from json body
        System.out.println("j1 = " + j1);

        //save all result in List<Job>

        List<Job2> allJobs = jp.getList("items", Job2.class);
        System.out.println("allJobs = " + allJobs);

        System.out.println("allJobs.get(2).getJobTitle() = " + allJobs.get(2).getJobTitle());

        //HW -> find all jobs name with min_salary more than 5000

    }

}
