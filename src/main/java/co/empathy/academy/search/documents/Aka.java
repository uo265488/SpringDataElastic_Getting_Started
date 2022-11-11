package co.empathy.academy.search.documents;

import lombok.*;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
public class Aka {

    private String tconst;
    private String title;
    private String region;
    private String language;
}
