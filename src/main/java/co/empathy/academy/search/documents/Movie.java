package co.empathy.academy.search.documents;


import co.empathy.academy.search.helpers.dto.RatingsDto;
import lombok.*;

import javax.annotation.Nullable;
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

    private final List<String> genres;
    private final double averageRating;
    private final int numVotes;

    String[] akas_tconst;
    String[] akas_title;
    String[] akas_region;
    String[] akas_language;

    String[] directors_nconst;

    String[] starring_name_nconst;
    String[] starring_characters;

    public String getId() {
        return this.id;
    }


    public Movie withRatings(RatingsDto ratings) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                ratings.averageRating, ratings.numVotes, this.akas_tconst, akas_title, akas_region, akas_language,
                directors_nconst,  starring_name_nconst, starring_characters);
    }

    public Movie setAkas(String[] akas_tconst, String[] akas_title, String[] akas_region, String[] akas_language) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, akas_tconst, akas_title, akas_region, akas_language,
                directors_nconst,  starring_name_nconst, starring_characters);
    }

    public Movie setDirectors(String[] directors_nconst) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes,  this.akas_tconst, akas_title, akas_region, akas_language,
                directors_nconst,  starring_name_nconst, starring_characters);
    }

    public Movie setStarring(String[] starring_name_nconst, String[] starring_characters) {
        return new Movie(this.id, this.tconst, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, this.akas_tconst, akas_title, akas_region, akas_language,
                directors_nconst, starring_name_nconst, starring_characters);
    }

    public Movie setTconst(String s) {
        return new Movie(s, s, this.titleType, this.primaryTitle, this.originalTitle,
                this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres,
                this.averageRating, this.numVotes, this.akas_tconst, akas_title, akas_region, akas_language,
                directors_nconst, starring_name_nconst, starring_characters);
    }
}
