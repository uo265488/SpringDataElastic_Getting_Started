package co.empathy.academy.search.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class Star {

    private String tconst;
    private Name name;
    private String characters;
}


