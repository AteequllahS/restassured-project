package com.cydeo.tests.day9;

import com.cydeo.utility.DB_Util;
import com.cydeo.utility.HrORDSTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class API_DB_Test extends HrORDSTestBase {

    @Tag("db")
    @DisplayName("Test Region Count")
    @Test
    public void testRegion() {


        DB_Util.runQuery("Select * from regions");
        //find out the row count
        System.out.println("DB_Util.getRowCount() = " + DB_Util.getRowCount());
        DB_Util.displayAllData();

    }

    /*
     * send API request to http://54.174.254.49:1000/ords/hr/regions
     * Get your response
     * Get the expected result count from Database
     * Assert the API request response has same count as database row count
     */

    @Test
    public void testRegionsCount() {

        //prepare expected result here
        DB_Util.runQuery("Select * from regions");
        int expectedCount = DB_Util.getRowCount();

        //get actual result

        given()
                .log().all().
        when()
                .get("/regions")
                .prettyPeek().
        then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("count", equalTo(expectedCount))// assertions
                .body("items", hasSize(expectedCount)) // another way of assertions
        ;
    }

    @Test
    public void testSingleRegion(){

        /*
         * send a request to GET /regions/{region_id}
         * prepare expected result from the database by running
         * SELECT * FROM REGIONS WHERE REGION_ID=1
         * Save the result of query as MAP
         * then verify the region_id and regions_name match between api and db response
         */

        DB_Util.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = 1");
        Map<String, String> dbResultMap = DB_Util.getRowMap(1);
        System.out.println("dbResultMap = " + dbResultMap);

        int expectedRegionId = Integer.parseInt(dbResultMap.get("REGION_ID")); // to have int result need to be converted
        String expectedRegionName = dbResultMap.get("REGION_NAME");

        given()
                .pathParam("region_id", 1)
                .log().uri().
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .statusCode(200)
                .body("region_id" , equalTo( expectedRegionId)   )
                .body("region_name", is(expectedRegionName) );
    }

    /*
     HOME WORK
     * Write a Parameterized Test to test all regions instead of one above
     * try couple different way
     * 1. @ValueSource  to provide all 4 regions id
     * 2. @MethodSource
     *       -- get all id s from api response GET /regions and return List<Integer>
     * 3. @MethodSource
     *       -- get all id s from SELECT * FROM REGIONS query and return List<String>
     *           and use it as a source
     */

}
