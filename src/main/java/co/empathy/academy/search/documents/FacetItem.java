package co.empathy.academy.search.documents;

import co.elastic.clients.elasticsearch._types.aggregations.HistogramAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@AllArgsConstructor
public class FacetItem {

    private String id;
    private String value;
    private long count;
    private String filter;
    @Nullable private List<FacetItem> children;

    public FacetItem(HistogramBucket b) {
        count = b.docCount();
        value = String.valueOf(b.key());
        id = value;
        filter = "bla";
        children = null;
    }

    public FacetItem(StringTermsBucket b, String name) {
        count = b.docCount();
        value = b.key().stringValue();
        id = b.key().stringValue();
        filter = name + ":" + b.key().stringValue();
        children = null;
    }
}
