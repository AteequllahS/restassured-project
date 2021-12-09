package com.cydeo.tests.day12;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class C2_XMLPracticeTest {


    @Test
    public void testMovieXML(){

    // if we do not have many test to share the baseURI and basePath
        // we can directly provide baseURI and basePath in given section

        //GET http://www.omdbapi.com/?apikey=25d8fdf1&s=The Mandalorian&r=xml
        // verify root element attribute  totalResults="11"
        // store all the titles on List<String> and print.
        // verify the size of list match the attribute totalResults="11"

        given()
                .baseUri("http://www.omdbapi.com")
                .queryParam("apiKey","25d8fdf1")
                .queryParam("s","The Mandalorian")
                .queryParam("r","xml")
                .log().uri().
        when()
                .get().
        then()
                .log().all()
                .contentType(ContentType.XML)
                .body("root.@totalResults", is("11") )
                // count how many item showed up on this page as result
                .body("root.result.@title" , hasSize(10) )  // because of pagination we only get 10 result per page this will fail
        ;
    }

    // in separate test , send a request to same endpoint store the title
    // in list of string
    // if you have more pages , keep sending more requests and store the title
    // into the same list

    // first we need to decide how many pages all movie information will be
    // if we have less than or equal to 10 we always have 1 page
    // if we have more than 10 then we need to divided it by 10 to find out
    // how many pages we will have
    //  if the result count can be divided by 10 without remainder
            // then pageCount = result/10 for example 80/10=8 pages
    //  else
            //   pageCount = result/10 + 1 for example 81/10=8 ,8+1 pages

    // in order to navigate through pages ,according to the doc
    // we can use page query parameter and provide page number

    @Test
    public void testGettingAllMoviesInAllPages(){

        List<String> allMovies = new ArrayList<>();

        // send first request to find out how many result
        // and save page one result into the list
        // We are going to send multiple request if there is more pages so it's better to create a method
        Response response =  getMovieResponse("Iron Man" , 1 ) ;
        // the result is coming as String so we want to parse it into the int
        int totalCount = Integer.parseInt( response.path("totalResults") ) ;
        // this is the total result count (not on this page but in all pages)
        System.out.println("totalCount = " + totalCount);

        List<String> page1MovieLst =  response.path("Search.Title")  ;
        System.out.println("page1MovieLst = " + page1MovieLst);

        // add first page movies into allMovie List
        allMovies.addAll(page1MovieLst) ;

        // we need find out how many pages we have and how many additional request
        // we need to send to get all the movies in all pages
        int totalPageCount =  (totalCount%10==0) ? totalCount/10  :  totalCount/10+1 ;
        // we already send request to page 1 and already stored all movie names
        // so we can just start from page 2
        // if we only had 1 page in response currentPage<= totalPageCount will return false
        // and we will not even enter the loop so it will be just ignored as expected
        // if we have more than 1 pages then it will go inside the loop until last page
        // if you want to start from the first page it will also work
        // the only difference will be you will not have to add first page before the loop
        // and you will make additional request to the first page which you already did
        for(int currentPage=2 ; currentPage<= totalPageCount ;  currentPage++    ) {
            // sending request to get next page
            response = getMovieResponse("Iron Man", currentPage);
            List<String> currentPageMovieList = response.path("Search.Title");
            allMovies.addAll(currentPageMovieList); //adding all movie in current page into list
        }



        System.out.println(" allMovies.size() = " + allMovies.size() );


    }


    @Test
    public void testGettingAllMoviesInAllPages_anotherway(){

        List<String> allMovies = new ArrayList<>();

        Response response =  getMovieResponse("Iron Man" , 1 ) ;
        // the result is coming as String so we want to parse it into the int
        int totalCount = Integer.parseInt( response.path("totalResults") ) ;
        // this is the total result count (not on this page but in all pages)
        System.out.println("totalCount = " + totalCount);


        int totalPageCount =  (totalCount%10==0) ? totalCount/10  :  totalCount/10+1 ;

        for(int currentPage=1 ; currentPage<= totalPageCount ;  currentPage++    ) {
            // sending request to get next page
            response = getMovieResponse("Iron Man", currentPage);
            List<String> currentPageMovieList = response.path("Search.Title");
            allMovies.addAll(currentPageMovieList); //adding all movie in current page into list
        }

        System.out.println(" allMovies.size() = " + allMovies.size() );


    }






    /**
     * Create a method that accept movie name and page number
     * and return Response object
     */
     public static Response getMovieResponse(String movieName, int currentPage) {

        return given()
                 .baseUri("http://www.omdbapi.com")
                 .queryParam("apiKey","25d8fdf1")
                 .queryParam("s",movieName)
                 .queryParam("page",currentPage).
        when()
                    .get() ;

     }



}
