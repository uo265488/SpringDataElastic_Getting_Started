package co.empathy.academy.search.documents;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import lombok.ToString;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@ToString
public class Facet {

    private String facet;
    private String type;
    private List<FacetItem> values = new ArrayList<>();

    public Facet(String name, Aggregate aggregate) {
        String type1 = null;
        facet = name;
        if(aggregate.isHistogram()) {
            type1 = "histogram";
            aggregate.histogram().buckets().array().forEach(
                    b -> values.add(new FacetItem(b))
            );
        }
        if(aggregate.isSterms()) {
            type1 = "value";
            aggregate.sterms().buckets().array().forEach(
                    b -> values.add(new FacetItem(b, name.substring(5)))
            );
        }
        this.type = type1;
        System.out.println(aggregate._get());
    }
}
