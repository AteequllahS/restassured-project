package com.cydeo.tests.day12;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class C1_XMLPracticeTest {

    @Test
    public void testMovieXML(){

        // if we do not have many test to share the baseURI and basePath
        // we can directly provide baseURI and basePath in given section

        //GET http://www.omdbapi.com/?apikey=25d8fdf1&s=The Mandalorian&r=xml
        // verify root element attribute  totalResults="11"
        // store all the titles on List<String> and print.
        // verify the size of list match the attribute totalResults="11"

        given()
                .baseUri("http://www.omdbapi.com") // baseURI in methodChain
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

        //The purpose of this Test is to get all list however they show limited in each page
        // so the total for each page will be limit to 10 while the total result is 11
        //so if we assert, it will fail because we will only be able to get the number shown on first page
        // to solve this issue we do the following pagination method:

        List<String> allMovies = new ArrayList<>();

        // send first request to find out how many result
        // and save page one result into the list
        //we are going to send multiple request if there is more pages so it is better

        String movieToSearch = "Iron Man";

        Response response =  getMovieResponse(movieToSearch , 1 ) ;

        //the result is coming as String so we need to convert it to int
        int totalCount = Integer.parseInt( response.path("totalResults") ) ;

        System.out.println("totalCount = " + totalCount);

        List<String> page1MovieLst =  response.path("Search.Title")  ;
        System.out.println("page1MovieLst = " + page1MovieLst);

        // add first page movies into allMovie List
        allMovies.addAll(page1MovieLst) ;

        // we need find out how many pages we have and how many additional request
        // we need to send to get all the movies in all pages
        //the ternary to set if the count is more than 10, so add 1
        int totalPageCount =  (totalCount%10==0) ? totalCount/10  :  totalCount/10+1 ;


        //we start from second page as we already got the first page
        for(int currentPage=2 ; currentPage<= totalPageCount ;  currentPage++    ) {
            // sending request to get next page
            response = getMovieResponse(movieToSearch, currentPage);
            List<String> currentPageMovieList = response.path("Search.Title");
            allMovies.addAll(currentPageMovieList); //adding all movie in current page into list
        }

        System.out.println(" allMovies.size() = " + allMovies.size() );


    }

    /*
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
