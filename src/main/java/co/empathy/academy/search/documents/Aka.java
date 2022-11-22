package co.empathy.academy.search.documents;

import lombok.*;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
public class Aka {

    String tconst;
    String title;
    String region;
    String language;
}
