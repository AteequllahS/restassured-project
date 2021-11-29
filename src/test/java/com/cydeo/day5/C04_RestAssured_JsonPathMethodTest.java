package com.cydeo.day5;

import com.cydeo.pojo.SpartanWithID;
import com.cydeo.utility.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class C04_RestAssured_JsonPathMethodTest extends SpartanTestBase {

    /*
      there are two ways to get the response and extract json data

      * path("your jsonpath goes here") return type is T(generic)
        and decided by your variable data type you store

            int myId = response.path("id")

     *  There is a type(class) in RestAssured : JsonPath that have lots of methods to extract jsonBody from the response
        like:
        getInt, getString, getDouble, getObject, getList....

        in order to get JsonPath object out of response,
        we call a method called JsonPath() from the response

        for example:

            JsonPath jp = response.jsonPath("your actual path goes here")

            jp.getInt()
            jp.getString()

      The meaning of the word jsonPath mean when we use in different places
      - json path: => when inside the ""  means the actual path to get the value (like xpath)
      - JsonPath : => the RestAssured class that have lots of methods
      - JsonPath : => the method of Response object to obtain JsonPath object out of response
*/

    //send request to GET /spartans/{id}
    //and extract the data id, name, phone

    @Test
    public void testOneSpartan(){

    // lets get first id exist in our system so we do not have to deal with data issue
    // send request to get all data and grab the first one

        int firstId = get("/spartans").path("id[1]");
        System.out.println("firstId = " + firstId);

        //send request to GET/spartans/{id}
        Response response =  given()
                                    .log().uri()
                                    .pathParam("id", firstId).
                        when()
                                    .get("spartans/{id}")
                                    .prettyPeek();

    // save the id from the response
//       int myId = response.path("id") ;

    //Get JsonPath object out of response object
        JsonPath jp = response.jsonPath();

    // using JsonPath can help us to get the variable path directly
        int myId = jp.getInt("id");
        String myName = jp.getString("name");
        String myGender = jp.getString("gender");
        long myPhone = jp.getLong("phone");

        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("myGender = " + myGender);
        System.out.println("myPhone = " + myPhone);

    //store this json result into a Map object
    // the path to get the entire body is "emptyString" because the entire response is json object already!
    // no need for a path to navigate to this json

    //this method will creat a map objet and add all the key of json as "key" and all values as "value"
    //returns that map object
        Map<String, Object> responseBodyAsMap = jp.getMap(""); //empty string means no path get the whole body
        System.out.println("responseBodyAsMap = " + responseBodyAsMap);

    //to get access to phone number field out of this map:
        System.out.println("responseBodyAsMap.get(\"phone\") = " + responseBodyAsMap.get("phone"));

    }

    // Send Request GET /spartans/search?nameContains=Ea&gender=Male
    // get JsonPath object out of response so you can use specialized methods
    // get totalElement field value using getX method
    // get 3rd element phone using getX method
    // get last element name using getX method
    // save first json in json array into Map using getX method
    // remember getX("your path to the element goes here just like xpath")

    @Test
    public void testSearchExtractData(){

        Response response =
                given()
                        .log().uri()
                        .queryParam("nameContains", "Ea").
                         //queryParam("gender", "Male").
                when()
                        .get("spartans/search")
                        .prettyPeek();

        JsonPath jp = response.jsonPath();
        //get the value of totalElement key

        int total = jp.getInt("totalElement");
        System.out.println("total = " + total);

        //third index, phone number
        long thirdPhone = jp.getLong("content[1].phone");
        System.out.println("thirdPhone = " + thirdPhone);

        // last element's name
        String lastIndexName = jp.getString("content[-1].name"); // or [2]
        System.out.println("lastIndexName = " + lastIndexName);

        // save first json in json array into Map using getMap() method : content[0]
        Map<String, Object> firstElement = jp.getMap("content[0]");
        System.out.println("firstElement = " + firstElement);

        //Get the whole names and add it into a list
        //List<String> allNames = jp.getList("content.name");

        //or we can specify it's type inside parameter, if we face error with previous version
        List<String> allNames = jp.getList("content.name", String.class);
        System.out.println("allNames = " + allNames);

        //save all phone number into the list and specify it's data type (to make it obvious/declare)
        List<Long> phones = jp.getList("content.phone", Long.class);
        System.out.println("phones = " + phones);


        // continue from this task : now try to match this json objects into POJO
        // in order to match json result with 4 fields , you need to have POJO class with 4 matching fields
        // create a class called SpartanWithID
        // add encapsulated fields id, name , gender, phone
        // add no arg constructor (no need for 4 args constructor because we don't create object ourselves)
        // optionally add toString method so we can print it worked.
        // now we can use  jp.getObject("the path here" , SpartanWithID.class)
        // to save it into  SpartanWithID object  --> another de-serialization!

        //store first json in the result as SpartanWithId POJO : content[0]
        //getObject method accept jsonPath to the jasonObject to be converted
        // and the class type you want to convert to and return object of that type
        // with all the field value already filled up by matching the key value
        SpartanWithID sp1 = jp.getObject("content[0]", SpartanWithID.class);
        System.out.println("sp1 = " + sp1);

        //get a list of all POJO objects
        //store the entire response json array, into List<SpartanWithID>

        List<SpartanWithID> allResponse = jp.getList("content", SpartanWithID.class);
        System.out.println("allResponse = " + allResponse);

        //to have get the phone number of first index
        System.out.println("allResponse.get(1).getPhone = " + allResponse.get(1).getPhone());

    }




}
