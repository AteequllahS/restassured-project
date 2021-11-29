package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.List;

/*
    {
        "char_id": 1,
        "name": "Walter White",
        "occupation": [
            "High School Chemistry Teacher",
            "Meth King Pin"
        ],
        "status": "Presumed dead",
        "nickname": "Heisenberg",
        "appearance": [
            1,
            2,
            3,
            4,
            5
        ],
    },
    {
 */
@Getter
@Setter
@NoArgsConstructor //optional if dont have constructor with argument
@ToString


@JsonIgnoreProperties(ignoreUnknown = true)
public class Characters {

    @JsonIgnoreProperties("char_id")
    private int id;

    private String name;
    private List<String> occupation; // list or array either can be selected by our choice.
    //private String[] occupation;
    private String status;
    private String nickname;
    private List<Integer> appearance;

}
