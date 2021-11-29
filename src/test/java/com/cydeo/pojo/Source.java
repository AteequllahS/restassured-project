package com.cydeo.pojo;


/* this pojo class represent the source from Article (pojo inside pojo):

                "id": null,
                "name": "KMBC Kansas City"
 */

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Source {

    private String id;
    private String name;
}
