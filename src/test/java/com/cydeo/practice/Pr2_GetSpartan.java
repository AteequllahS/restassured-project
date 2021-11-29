package com.cydeo.practice;

import com.cydeo.utility.SpartanTestBase;
import groovy.util.MapEntry;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Pr2_GetSpartan extends SpartanTestBase {


    @Test
    public void TestOneSpartan(){

        Response r = get("/hello");
        r.body().prettyPrint();
        String text = r.body().asString();
        assertThat(text, equalTo("Hello from Sparta"));
        System.out.println(r.getStatusCode());
        System.out.println(r.contentType());
        System.out.println(r.asString());
        reset();




    }
    @Test
    public void TestOneSpartan2() {

        Response r = get("/spartans");

        //List<String> allNames = r.path("name");
        //System.out.println(allNames);

        Map<String, String> idNames = new LinkedHashMap<>();

        idNames.put(r.path("id").toString(), r.path("name").toString());

        for (Map.Entry<String, String> each : idNames.entrySet()){

            System.out.println(each.getKey()+" | "+each.getValue());
        }

        System.out.println(idNames.getOrDefault("2", "Nels"));
        System.out.println(idNames.get("5"));
    }

    @Test
    public void getResponse(){

        Response r = get("spartans/6");
        given().log().all().
                accept(ContentType.XML).
                then().statusCode(200);
        r.prettyPrint();

        assertThat(r.statusCode(), equalTo(200) );

    }

    @Test
    public void getResponse2(){

        Response r = given().log().all().
                    queryParam("nameContains", "b").
                    queryParam("gender", "Male").

                when().
                    get("spartans/search").
                prettyPeek()
                ;
        assertThat(r.statusCode(), equalTo(200) );
        System.out.println("r.path(\"totalElement\") = " + r.path("totalElement"));

        System.out.println("r.path(\"name.id[5]\") = " + r.path("content.id[5]"));
        System.out.println("r.path(\"content[5].id\") = " + r.path("content[5].id"));
        System.out.println("r.path(\"content.name[4]\") = " + r.path("content.name[4]"));



    }

    @Test
    public void pathParam(){

                given().log().all().
                        pathParam("idNumber", 4).
                        when().get("spartans/{idNumber}").prettyPeek();



    }

}
