package com.cydeo.tests.day8;


import java.util.Arrays;
import java.util.List;

/**
 * The only purpose of this class is
 * to demonstrate providing the MethodSource
 * for a ParameterizedTest and the method exist outside the class
 */
public class C1_MethodSourceStaticMethod_Test {

    // create a STATIC method that return list of names
    //so we can use the returned value as data source for our ParameterizedTest

    public static List<String> getSomeNames() {

        return Arrays.asList("Furkan", "Abbos", "Yevheniia", "Shazia", "Tugba", "Mohamed", "Kimberley");

    }
}
