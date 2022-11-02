package co.empathy.academy.search.documents;


import co.empathy.academy.search.helper.Indices;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

@Document(indexName = Indices.MOVIE_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Movie {
    @Id
    @Field(type = FieldType.Keyword)
    private final UUID id;

    @Field(type = FieldType.Integer)
    private final int ordering;

    @Field(type = FieldType.Text)
    private final String title;

    @Field(type = FieldType.Text)
    private final String region;

    @Field(type = FieldType.Text)
    private final String language;

    @Field(type = FieldType.Text)
    private final String types;

    @Field(type = FieldType.Text)
    private final String attributes;

    @Field(type = FieldType.Text)
    private final boolean isOriginalTitle;

    public Movie(UUID id, int ordering, String title, String region,
                 String language, String types, String attributes, boolean isOriginalTitle) {
        this.id = id;
        this.ordering = ordering;
        this.title = title;
        this.region = region;
        this.language = language;
        this.types = types;
        this.attributes = attributes;
        this.isOriginalTitle = isOriginalTitle;

    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public Movie withId(UUID uuid) {
        return new Movie(uuid, this.ordering,
                this.title, this.region, this.language, this.types, this.attributes, this.isOriginalTitle);
    }
}
