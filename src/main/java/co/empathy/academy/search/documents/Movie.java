package co.empathy.academy.search.documents;


import co.empathy.academy.search.helpers.Indices;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = Indices.MOVIE_INDEX)
@Setting(settingPath = "static/es-settings.json")
@ToString
public class Movie {
    public static final int UNDEFINED = -1;
    @Id
    @Field(type = FieldType.Keyword)
    private final String id;

    @Field(type = FieldType.Text)
    private final String tconst;

    @Field(type = FieldType.Keyword)
    private final String titleType;

    @Field(type = FieldType.Text)
    private final String primaryTitle;

    @Field(type = FieldType.Text)
    private final String originalTitle;

    @Field(type = FieldType.Boolean)
    private final Boolean isAdult;

    @Field(type = FieldType.Integer)
    private final int startYear;

    @Field(type = FieldType.Integer)
    private final int endYear;

    @Field(type = FieldType.Integer)
    private final int runtimeMinutes;

    @Field(type = FieldType.Text)
    private final String genres;

    @Field(type = FieldType.Double)
    private final double averageRating;

    @Field(type = FieldType.Integer)
    private final int numVotes;

    public Movie(String id, String tconst, String titleType, String primaryTitle, String originalTitle,
                 Boolean isAdult, int startYear, int endYear, int runtimeMinutes, String genres, double averageRating, int numVotes) {
        this.id = id;
        this.tconst = tconst;
        this.titleType = titleType;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.isAdult = isAdult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    public Movie(String id, String tconst, String titleType, String primaryTitle, String originalTitle,
                 Boolean isAdult, int startYear, int endYear, int runtimeMinutes, String genres) {
        this.id = id;
        this.tconst = tconst;
        this.titleType = titleType;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.isAdult = isAdult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
        this.averageRating = 0;
        this.numVotes = 0;
    }

    public Movie withId(String id) {
        return new Movie(id, id, this.titleType, this.primaryTitle, this.originalTitle, this.isAdult, this.startYear,
                this.endYear, this.runtimeMinutes, this.genres, averageRating, numVotes);
    }

    public String getId() {
        return this.id;
    }

    public String getTitleType() {
        return titleType;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getNumVotes() {
        return numVotes;
    }
}
