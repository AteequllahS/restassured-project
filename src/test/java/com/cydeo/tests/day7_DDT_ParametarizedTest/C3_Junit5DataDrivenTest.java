package com.cydeo.tests.day7_DDT_ParametarizedTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("smoke")
public class C3_Junit5DataDrivenTest {

    // Given these String data :
    // "Furkan", "Abbos", "yevheniaa", "Shazia", "Tugba", "Mohamed", "Kimberley"
    // write a data driven test
    //to check the length of these names all more than 5 character

    @ParameterizedTest
    @ValueSource(strings = {"Furkan", "Abbos A", "yevheniaa", "Shazia", "Tugba S", "Mohamed", "Kimberley"} )
    public void testNameLength( String name ){

        System.out.println("name = " + name);

        //assertion
        assertTrue(name.length() > 5);

    }
/*
        In @Parameterized test using @ValueSource
        you can only provide one data per iteration to the test. and sometime we want to externilize the data we used to external file.

        Junit5 have one data source known as @CsvFileSource where you can provide the path of external csv file that exists in src/test/resources folder and get the data from there.

        CSV File is a file that contains comma separated value

        It can be opened by Excel reading software, however it's not excel file and it's much ligher because it's plain text.
        It can be easily consumed by many library.
        In Junit 5, all we have to do to data drive the test according to the rows , we can point the
        @CSVFileSource to the file under src/test/resources folder for easy recogniztion.

        It looks as below.
        Optionally First row can be seen as column name , the rest of the row can be seen as actual data

        name, gender , phone
        Jon , Male , 123123123
        Mary, Female , 123123333
        Bla , Male , 735273545


        Create a resources folder under src/test package if you do not already have it.

        Right click and select
        new -> file - > give a name  people.csv
        and add above content and save

 */
    @ParameterizedTest
    //read data from csv file and provide the path
    //as the first line of csv file is the header we need to skip it to avoid the exception
    //we use numLinesToSkip=1 inside parameter
    @CsvFileSource(resources = "/people.csv", numLinesToSkip = 1 )
    public void testPerson(String nameParam, String genderParam, long phoneParam){

        System.out.println("nameParam = " + nameParam);
        System.out.println("genderParam = " + genderParam);
        System.out.println("phoneParam = " + phoneParam);

        //as the first line of csv file is the header we need to skip it to avoid the exception


    }
}
