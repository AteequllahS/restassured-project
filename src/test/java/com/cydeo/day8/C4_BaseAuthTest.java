package com.cydeo.day8;

import com.cydeo.utility.SpartanAuthTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static io.restassured.RestAssured.*;


public class C4_BaseAuthTest extends SpartanAuthTestBase {

    // Spartan App that run port 7000 require basic auth to make authorized request
    // 3 roles with different authority admin/admin editor/editor user/user
    // we learned how to provide api key or token
    // in query param or header according to the doc
    // in order to provide basic auth we use auth().basic("username","PASSWORD")
    // in given section


    /*
     * Create a test method testPublicUser
     * send a request GET/spartans without any authentication
     * expect 401
     */

    @Test
    public void testPublicUser(){

        given()
                .log().uri().
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(401);

    }
     /*
     * Create a test method testAuthenticatedUser
     * send a request GET/spartans without any authentication
     * expect 200
     */
     @Test
     public void testAuthenticatedUser(){

         given()
                 .log().all()
                 .auth().basic("admin", "admin"). //user, user || admin, admin ||
         when()
                 .get("/spartans").
         then()
                 //.log().all()
                 .log().ifValidationFails() //this will only show if something fails
                 .statusCode(200);

     }

    /*
     * Role Based Access Control Test, also knows as RBAC
     * is a type of testing to ensure authenticated user can
     * perform everything they have authorized to
     * in the meantime cannot perform anything that they are not authorized to perform
     *
     * admin    : create, read, update, delete
     * editor   : create, read, update
     * user     : read


     * Given editor credentials are provided
     * when editor try to delete any data
     * then the response should be 403 forbidden
     */

    @DisplayName("Editor should not be able to delete")
    @Test
    public void testEditorDeleteData(){

        given()
                .auth().basic("editor", "editor")
                .pathParam("id", 34). //any number will be fine as the editor is already blocked to delete items
        when()
                .delete("/spartans/{id}").
        then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    /*
     * write a parameterizedTest for getting all data GET/spartans
     * with different user credentials
     * use @ValueSource in this particular example
     * since username and passwords are the same, we can just provide
     */

    @ParameterizedTest
    @ValueSource(strings = {"admin1", "editor", "user"})
    public void testAllUser_GetAllSpartans( String role){

        //in this particular api, username and password have same value as role name
        // that's why we are just using one parameter
        given()
                .auth().basic(role, role).
        when()
                .get("/spartans").
                //prettyPeek().
        then()
                .log().ifValidationFails()
                .statusCode(200) ;
    }

    /*
     * HOMEWORK
     * Creat 3 separate class AdminAuthTest, EditorAuthTest, UserAuthTest
     * write separate tests for below requirement to do Role Base access control test
     *      * admin    : create, read, update, delete
     *      * editor   : create, read, update
     *      * user     : read
     */

}
