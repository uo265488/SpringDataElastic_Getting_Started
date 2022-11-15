package co.empathy.academy.search.documents;

import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
public class ResponseModel {

    List<Movie> hits;

    public ResponseModel(List<Hit<Movie>> hits) {
        this.hits = new ArrayList<>();
        hits.forEach(h -> this.hits.add(h.source()));
    }

    /**
     * Adds the List to the hits
     * @param hits
     */
    public void addHits(List<Hit<Movie>> hits) {
        hits.forEach(h ->
        {
            if(!this.hits.contains(h)) hits.add(h);
        });
    }

}
