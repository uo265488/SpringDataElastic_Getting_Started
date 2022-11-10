package co.empathy.academy.search.documents;


import co.empathy.academy.search.helpers.Indices;
import co.empathy.academy.search.helpers.dto.RatingsDto;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
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
    private List<Aka> akas;

    public String getId() {
        return this.id;
    }


    public Movie withRatings(RatingsDto ratings) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                ratings.averageRating, ratings.numVotes, this.akas);
    }
}
