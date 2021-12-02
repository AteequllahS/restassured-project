package com.cydeo.day6;

import static io.restassured.RestAssured.*;

import com.cydeo.pojo.ErgastDriver;
import org.junit.jupiter.api.Test;
import java.util.List;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class C04_FormulaOneApiTest_HomeWorkTest {

    /*

    Here is the import link for whole collection
    https://www.getpostman.com/collections/4ea3cf2262b1b19a6d29

    it is for historical formula one race information
    in this particular api, it decided to give you xml by default for response type
    and in this particular api, it decided to give you json if you add.json at the end of url

    for example:
    http://ergast.com/api/f1/drivers  --> return xml
    http://ergast.com/api/f1/drivers.json --> return json

    our objective is to practice json path to find the values in json result
    also practice de-serialization by converting single driver json to POJO
    practice converting driver json array into List<Driver>

    - print the name of anyone with nationality italian
    -
     */

    @BeforeAll
    public static void setup(){

        baseURI = "http://ergast.com";
        basePath = "/api/f1";

    }

    @AfterAll
    public static void teardown(){
        reset();
    }


    @Test
    public void testDriverDeserialization(){

        JsonPath jp =
                get("/drivers.json")
                        //.prettyPeek()
                        .jsonPath() ;


        //first driver json path : "MRData.DriverTable.Drivers[0]"

        ErgastDriver d1 = jp.getObject("MRData.DriverTable.Drivers[0]", ErgastDriver.class);
        System.out.println("d1 = " + d1);

        //all drivers json array into a List<ErgastDriver>
        List<ErgastDriver> allDriver = jp.getList("MRData.DriverTable.Drivers", ErgastDriver.class);
        System.out.println("allDriver = " + allDriver);

        //find all driver name with nationality of italian
        for (ErgastDriver eachDriver : allDriver) {
            if (eachDriver.getNationality().contains("Italian")) {
                System.out.println(eachDriver.getDriverId() + " | " + eachDriver.getNationality());
            }
        }



    }

}
