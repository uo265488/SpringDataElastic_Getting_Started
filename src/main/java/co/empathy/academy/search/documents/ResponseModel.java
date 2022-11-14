package co.empathy.academy.search.documents;

import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ResponseModel {

    List<Movie> hits;

    public ResponseModel(List<Hit<Movie>> hits) {
        this.hits = new ArrayList<>();
        hits.forEach(h -> this.hits.add(h.source()));
    }

}
