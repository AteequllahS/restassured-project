package com.cydeo.practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class Pr1_GetPractice {

    @BeforeAll
    public static void setup(){

        baseURI = "http://54.174.254.49:8000";
        basePath = "api";
    }

    @AfterAll
    public static void reset(){
        RestAssured.reset();
    }

    @Test
    public void getAllEmployees(){

        Response response = get("/spartans");
        //response.prettyPrint();
//
//        List<String> allEmployees = response.path("name");
//        System.out.println(allEmployees);
//
//        List<Integer> all_ids = response.path("id");
//        System.out.println(all_ids);


        List<Integer> ids = response.path("id");
        List<String> names = response.path("name");

        Map<Integer, String> idNames = new LinkedHashMap<>();

        for (int i = 0; i < ids.size(); i++) {

            idNames.put(ids.get(i), names.get(i));

        }


        System.out.println("idNames = " + idNames);


    }

    @Test
    public void getOneEmployee(){

        Response response = get("/employees/102");
        response.prettyPrint();
        System.out.println("response.path(\"email\") = " + response.path("email"));
    }
}
