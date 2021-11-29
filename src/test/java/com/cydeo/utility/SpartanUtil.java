package com.cydeo.utility;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Map<String, Object> getRandomSpartanMapBody(){

    Faker faker = new Faker();

    Map<String, Object> bodyMap  = new LinkedHashMap<>();

        bodyMap.put("name", faker.name().firstName()    ); //name only accept 2 to 15 characters
        bodyMap.put("gender", faker.demographic().sex() ); // male or female
        bodyMap.put("phone", faker.number().numberBetween(5000000000L, 9999999999L));
        // we need to numerify to match the number format or between 500,000,000 - 999,999,999

        return bodyMap;
    }
}
