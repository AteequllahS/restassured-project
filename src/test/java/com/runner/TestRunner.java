package com.runner;


import org.junit.platform.suite.api.*;

@Suite
@SuiteDisplayName("Smoke Test") //name the test
//@SelectPackages("com.cydeo.day8") // just to run day8 package
//@SelectPackages(  {"com.cydeo.day8", "come.cydeo.day1" }  ) // runs multiple packages
//@SelectClasses(C4_BaseAuthTest.class) //runs a class -> it takes className.class (without quotations)
//@SelectPackages("com.cydeo.day9")
// if we want to run specific tag from a Test, we need to specify the package and then @includeTags("tag1")
//@IncludeTags({"smoke1", "smoke2"})
@SelectPackages("com.cydeo") // for smoke/regression test purpose we give the path for all classes
@IncludeTags("smokeAll") // this is running the class we gave the tag at class level
@ExcludeTags("smoke2") // if we want to exclude a tag
public class TestRunner {


}
