package com.cydeo.tests.day3;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("smoke")
public class C02_HamcrestMatcher_Test {

   /*
   Hamcrest is a framework for writing matcher objects allowing ‘match’ rules to be defined declaratively.
   it is an assertion library that can be used additionally to make assertion
   readable and it comes with a lot of pre-written matchers to make it easier to write and read

   the method chain of RestAssured then section use the hamcrest matcher library
   extensively to assert the response components in one chain
   restAssured dependency already contains Hamcrest Assertion Library
   so no separate dependency needed
   All we need is to add static imports and start using it's matchers
   to make the assertions great again

   import static org.hamcrest.MatcherAssert.*;

    */

    @Test
    public void testNumbers(){

        assertEquals(9, 3+6);

        //Hamcrest, it can be written this way
        //import static org.hamcrest.MatcherAssert.assertThat;
        //import static org.hamcrest.Matchers.*;

        //equalTo
        assertThat( 3+6, equalTo(9));

        //is
        assertThat(4+6, is(10));

        //greaterThan
        assertThat(5+6, greaterThan(10));

        //greaterThanOrEqualTo
        assertThat(5+6, greaterThanOrEqualTo(10));

        assertThat("car", endsWith("r"));
    }

    @Test
    public void testString(){

        String msg = "B23 is Excellent!";
        assertThat(msg,  is("B23 is Excellent!")    ) ;

        assertThat(msg,  equalTo("B23 is Excellent!")    ) ;
        assertThat(msg,  equalToIgnoringCase("B23 is Excellent!")    ) ;

        assertThat(msg,  startsWith("B23")    ) ;
        assertThat(msg,  startsWithIgnoringCase("b23")    ) ;

        assertThat(msg,  endsWith("Excellent")    ) ;
        assertThat(msg,  endsWithIgnoringCase("Excellent")    ) ;

        assertThat(msg,  containsString("is")    ) ;
        assertThat(msg,  containsStringIgnoringCase("Is")    ) ;

        assertThat(msg,  not("B24 is Excellent!")    ) ;
        assertThat(msg,  is (not("B24 is Excellent!") )   ) ;

        //check if String is not blank
        assertThat(msg, not(blankString()));

    }

    @Test
    public void testCollections(){

        List<Integer> numList = Arrays.asList(3, 5, 1, 77, 44, 76);

        //check this list has size of 6
        assertThat(numList, hasSize(6));

        // check this list has item 77
        assertThat(numList, hasItem(77) );

        //check this list has item5, 44
        assertThat(numList, hasItems(5,44 ));

        //check this list every item is greatThan 0
        assertThat(numList, hasItems(greaterThan(0)));

        //checks all items are greater than 0
        assertThat(numList, everyItem(greaterThan(0)));

        //check this list has items greater than 50
        assertThat(numList, hasItems(greaterThan(50)));

    }
}
