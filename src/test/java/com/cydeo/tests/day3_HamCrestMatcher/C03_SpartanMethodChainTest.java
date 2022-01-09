package com.cydeo.tests.day3_HamCrestMatcher;
import com.cydeo.utility.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@Tag("smoke")
public class C03_SpartanMethodChainTest extends SpartanTestBase {

    @Test
    public void testGetOneSpartan(){

        // in one shot,
        // send request to Get / spartans/{id} with id of 2
        // log everything
        // verify status Code is 200
        //contentType is json
        //json body : id is 2,
        //name: Nels
        given().log().all().
                pathParam("id", 4).
                accept(ContentType.JSON).
        when().
                get("/spartans/{id}").prettyPeek().
        then(). // -> this is where assertions start
                //statusCode(200);
                statusCode(is(200)).
                //header("Content-Type", "application/json")
                //header("Content-Type", ContentType.JSON.toString()  )
                contentType( ContentType.JSON).
                body("id", is (4)). // validatable response
                body("name", is ("Paige")  )
        ;

    }

    @Test
    public void testSearch(){

     //http://54.174.254.49:8000/api/spartans/search?nameContains=Ea&gender=Male
        // verify status code is 200
        // content Type is json
        // "totalElement" : 3
        // jsonArray has size of 3
        //all names has item Sean
        // every gender is Male
        // every name should contain ignoring case "ea"

        given().
                log().all(). // -> this is logging everything about request
                queryParam("nameContains", "Ea").
                queryParam("gender", "Male").
        when().
                get("/spartans/search").
        // validate response
        then().
                log().all(). // ->  this is logging everything about response
                statusCode(200).
                contentType(ContentType.JSON).
                body("totalElement", is(2)).
                body("content", hasSize(2)).
                body("content.name", hasItem("Sean")).
                body("content.gender", everyItem(is("Male")) ).
                body("content.name", everyItem( containsStringIgnoringCase("ea")) )

                ;

    }

    @Test
    public void get1Search(){

        given()
                            .log().all()
                            .pathParam("id", 163)
                            .accept(ContentType.JSON).
                when().
                            get("spartans/{id}")

                .then()
                        .statusCode(200)
                        .body("id", is(163))
                ;




    }
}


