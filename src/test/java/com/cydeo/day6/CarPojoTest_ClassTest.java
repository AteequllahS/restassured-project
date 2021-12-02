package com.cydeo.day6;

import com.cydeo.pojo.Car;
import org.junit.jupiter.api.Test;

public class CarPojoTest_ClassTest {

    @Test
    public void testCarPojoClass(){

        Car c1 = new Car("model3", "Tesla", 2020, true);
        System.out.println("c1 = " + c1);

        c1.setModel("CyberTruck");

        System.out.println("c1.getModel() = " + c1.getModel());
    }
}
