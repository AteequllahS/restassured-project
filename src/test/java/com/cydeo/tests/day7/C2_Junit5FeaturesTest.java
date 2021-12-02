package com.cydeo.tests.day7;


/*
        a class to explore Junit 5 features
        docs : https://junit.org/junit5/docs/current/user-guide/#writing-test
        Junit 5 have many annotations to either help to make test result simpler
        or to affect the way that test run

        these annotations can be both at test level or class level
        can have message with opening parentheses

        @DisplayName :
        Declares a custom display name for the test class or test method. Such annotations are not inherited.

        @Disable:
        Used to disable a test class or test method; analogous to JUnit 4’s @Ignore. Such annotations are not inherited.


    JUNIT5 ASSERTION FUNCTIONALITY:
     - assertAll() and provide multiple assertion  in ()-> assertEquals(..)

     * all assertion methods has overloaded version that accept String message
     * to provide addition information in the console when something fail.

 * @ParametrizedTest Denotes that a method is a parameterized test
 * a test that annotated with @ParametrizedTest instead of @Test
 * will need to provide where to provide the data known as source
 * there are 4 type of sources supported by JUnit 5
 *  - @valueSource
 *  - @CsvSource
 *  - @CsvFileSource
 *  - @MethodSource
 */



import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("smoke")
@DisplayName("Calculator") // display name at class level
public class C2_Junit5FeaturesTest {

    @DisplayName("Addition") // it is to name each test optionally, can be placed at @Test level or Class level
    @Test
    public void testAdd(){

        assertEquals(10, 5+5);

        //providing additional error message when assertion fail
        //assertEquals(expected: "    ", "     ",  "message) // 3 parameter assertion
        assertEquals(10, 5 + 7, "Hey!! it was not 10 !!!!!"); //custom message assertion
        // the message gives more info when the assertion fails, so if pass, no message pops up.
    }

    @DisplayName("Subtraction")
    @Test
    public void testSubtract(){

        assertEquals(10, 15-5);
    }


    @Disabled("ignored until the defect 103 is fixed") // disables a test and can have a message too (optional)
    @DisplayName("Subtraction2")
    @Test
    public void testSubtract2(){

        assertEquals(10, 15-5);
    }


    @DisplayName("Assert all together then one result")
    @Test
    public void testMultipleAssertions(){


        // in this way , if second assertion fail, it will just stop the test at each line it fails
        //and we will not know the result of the rest
//        assertEquals(7,  9 - 2);
//        assertEquals(10, 5 + 5);
//        assertEquals(11, 5 + 6);
//        assertEquals(25, 5 * 5);
//        assertEquals(10, 100 / 10);
//
        // but, what if want to assert all of them and see the final result (fail/pass)?
        // we can use:

        //assertAll
        //it accepts one or more Executable
            // Executable is functional interface
            //it can be easily created using lambda expression
            // it has single method that accept no parameter and return nothing
            //so it can be in this way ()->

        //this way we can have softAssertion because it wont stop if the first assertion fails.
        assertAll(
                    ()-> assertEquals(71,  9 - 2) ,
                    ()-> assertEquals(10, 5 + 5),
                    ()-> assertEquals(250, 5 * 5),
                    ()-> assertEquals(11, 5 + 6)

        );


    }


    //import org.junit.jupiter.params.ParameterizedTest;
    //import org.junit.jupiter.params.provider.ValueSource;
    @ParameterizedTest // mark this method as parametrized test
    @ValueSource(ints = {11, 44, 33, 5, 6, 88} )        // provide value for each iteration of test
    public void testNumberGreaterThan10( int num ){

        // assuming we got these number from
        // some complicated previous code here 11, 44, 33,15, 16, 88
        // assert all these numbers are more than 10

        System.out.println("Number in this iteration " + num);

        assertTrue(num> 10 ," hey it was not more than 10");
    }

}
