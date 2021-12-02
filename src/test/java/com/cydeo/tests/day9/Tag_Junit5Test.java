package com.cydeo.tests.day9;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smokeAll")
public class Tag_Junit5Test {

    @Tag("smoke1")
    @Test
    public void test1(){

        System.out.println("Test 1 is running");
    }

    @Tag("smoke2")
    @Test
    public void test2(){

        System.out.println("Test 2 is running");
    }

    @Tag("smoke3")
    @Test
    public void test3(){

        System.out.println("Test 3 is running");
    }
}
