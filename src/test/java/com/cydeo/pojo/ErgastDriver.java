package com.cydeo.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
/*
        {
                "driverId": "abate",
                "url": "http://en.wikipedia.org/wiki/Carlo_Mario_Abate",
               "givenName": "Carlo",
               "familyName": "Abate",
               "dateOfBirth": "1932-07-10",
               "nationality": "Italian"
        }
 */
@Getter
@Setter
@NoArgsConstructor
@ToString


@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastDriver {

    //@JsonProperty to instruct jackson is not needed
    //since the name are all matching java naming convention

    private String driverId;
   // private String url;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nationality;
}
