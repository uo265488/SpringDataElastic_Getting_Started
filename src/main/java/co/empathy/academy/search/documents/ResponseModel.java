package co.empathy.academy.search.documents;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
public class ResponseModel {

    List<Object> hits;
    List<Facet> facets = new ArrayList<>();

    public ResponseModel(SearchResponse<Movie> response) {
        //Hits
        this.hits = new ArrayList<>();
        response.hits().hits().forEach(h -> this.hits.add(h.source()));
        //Facets
        setFacets(response.aggregations());
    }

    /**
     * Adds the facets in correct format to the response model
     * @param aggregations
     */
    private void setFacets(Map<String, Aggregate> aggregations) {
        aggregations.forEach(
                (name, agg) -> facets.add(new Facet(name, agg))
        );
    }

    /**
     * Adds the List to the hits
     * @param  response
     */
    public void addHits(SearchResponse<Movie> response) {
        response.hits().hits().forEach(h ->
        {
            if(!this.hits.contains(h)) hits.add(h);
        });
    }

}
