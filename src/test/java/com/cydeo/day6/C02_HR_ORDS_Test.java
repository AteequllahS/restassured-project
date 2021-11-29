package com.cydeo.day6;

import com.cydeo.pojo.Job;
import com.cydeo.utility.HrORDSTestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

import java.util.List;


public class C02_HR_ORDS_Test extends HrORDSTestBase {

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

        Job j1 = jp.getObject("items[0]", Job.class); //items come from json body
        System.out.println("j1 = " + j1);

        //save all result in List<Job>

        List<Job> allJobs = jp.getList("items", Job.class);
        System.out.println("allJobs = " + allJobs);

        System.out.println("allJobs.get(2).getJobTitle() = " + allJobs.get(2).getJobTitle());

        //HW -> find all jobs name with min_salary more than 5000

    }

}
