package com.cydeo.pojo;

/*
    Another common way of representing json data is using special purpose Java Object created from A class that
    have filed matched to json keys and have:
            -setters and
            -getters

    This type of Object, sole purpose is to represent data, known  POJO known as POJO class
    it's used for creating POJO, just like you create any other object

    for example : in order to represent a json data with 3 keys ( name, gender, phone )
    we can create a java class with 3 instance fields with same name

    the only required part of POJO class is:
        encapsulated fields :
        -private instance variables
        -public getters and setters
    no any constructors
 */


public class Spartan {

    private String name;
    private String gender;
    private long phone;

    public Spartan(){
        //just have it here, no body required
    }


    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }


    //Alt+Insert (shortcut for insertion of getters and setters)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public long getPhone() {
        return phone;
    }


    public void setPhone(long phone) {
        this.phone = phone;
    }


    //generating toString
    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}
