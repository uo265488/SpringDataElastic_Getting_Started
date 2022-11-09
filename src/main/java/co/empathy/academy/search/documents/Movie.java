package co.empathy.academy.search.documents;


import co.empathy.academy.search.helpers.Indices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Movie{
    public static final int UNDEFINED = -1;

    private String id;
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private final Boolean isAdult;
    private final int startYear;
    @Nullable
    private final int endYear;
    private final int runtimeMinutes;
    private final String genres; //needs fix
    private final double averageRating;
    private final int numVotes;

    public String getId() {
        return this.id;
    }


}
