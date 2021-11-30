package com.cydeo.day8;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C3_MethodSource_Practice {

    // create a method that return List <Map <String, Object> >
    //Map object should have key : name, gender, phone
    public static List<Map<String, Object>> getAllStudentInfo(){

        List<Map<String, Object>> allInfoMapList = new ArrayList<>();
        //add 3 items to this list

        // add 3 items to this list
        Map<String,Object> studentMap1 =  new HashMap<>() ;
        studentMap1.put("name", "Mousa") ;
        studentMap1.put("gender", "Male") ;
        studentMap1.put("phone", 1231231231L) ;

        Map<String,Object> studentMap2 =  new HashMap<>() ;
        studentMap2.put("name", "Mucahit") ;
        studentMap2.put("gender", "Male") ;
        studentMap2.put("phone", 1231231131L) ;

        Map<String,Object> studentMap3 =  new HashMap<>() ;
        studentMap3.put("name", "Nazli") ;
        studentMap3.put("gender", "Female") ;
        studentMap3.put("phone", 33223123122L) ;

        Map<String,Object> studentMap4 =  new HashMap<>() ;
        studentMap4.put("name", "Sophie") ;
        studentMap4.put("gender", "Female") ;
        studentMap4.put("phone", 332323323122L) ;

        allInfoMapList.add(studentMap1);
        allInfoMapList.add(studentMap2);
        allInfoMapList.add(studentMap3);
        allInfoMapList.add(studentMap4);

        return allInfoMapList;
    }

    //what if we want to provide more than one value
    //for each test in parameterizedTest
    //List<String> will only store 1 string per element
    // in order to have more than one value
    //we can use List< Map<String, Object> >


    @ParameterizedTest
    @MethodSource("getAllStudentInfo")
    public void testStudentDDT( Map<String, Object> studentInfo ){

        System.out.println("studentInfo = " + studentInfo);

    }


}
