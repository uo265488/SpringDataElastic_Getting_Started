package co.empathy.academy.search.documents;


import co.empathy.academy.search.helpers.dto.RatingsDto;
import lombok.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
public class Movie {
    private String id;
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private final Boolean isAdult;
    @Nullable
    private final int startYear;
    @Nullable
    private final int endYear;
    @Nullable
    private final int runtimeMinutes;
    private final String[] genres;
    private final double averageRating;
    private final int numVotes;
    private List<Aka> akas;

    private List<Director> directors;

    private List<Star> starring;

    public String getId() {
        return this.id;
    }


    public Movie withRatings(RatingsDto ratings) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                ratings.averageRating, ratings.numVotes, this.akas, this.directors, this.starring);
    }

    public Movie setAkas(List akas) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, akas, this.directors, this.starring);
    }

    public Movie setDirectors(List<Director> directors) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, this.akas, new ArrayList<>(directors), this.starring);
    }

    public Movie setStarring(List<Star> starring) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, this.akas, this.directors, new ArrayList<>(starring));
    }
}
