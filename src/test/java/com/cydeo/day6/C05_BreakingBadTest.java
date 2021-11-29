package com.cydeo.day6;

import static io.restassured.RestAssured.*;

import com.cydeo.pojo.Characters;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class C05_BreakingBadTest {

    @BeforeAll
    public static void setup(){
        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";

    }

    @AfterAll
    public static void reset(){
        RestAssured.reset();
    }


    @Test
    public void testCharacters(){

        //https://www.breakingbadapi.com/characters

        JsonPath jp = get("/characters")
                        //.prettyPeek()
                        .jsonPath();

        Characters c1 = jp.getObject("[0]", Characters.class);
        System.out.println("c1 = " + c1);

        //list of characters
        List<Characters> allCharacters = jp.getList("", Characters.class);
        System.out.println("allCharacters = " + allCharacters);

        /*
        Homework
            -find out the character names appearance count is exactly 1
            -find out the name of DEA Agent

            - ORDS API . AS A HOMEWORK :
            -find out all Jobs name with min_salary more than 5000
         */
    }


}
