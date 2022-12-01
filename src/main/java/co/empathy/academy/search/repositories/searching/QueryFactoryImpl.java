package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryFactoryImpl implements QueryFactory {

    @Override
    public Query getFilterQuery(String fieldName, String value) {
        return MatchQuery.of(m -> m
                .field(fieldName)
                .query(value)
        )._toQuery();
    }

    @Override
    public Query getRangeQuery(String fieldName, double min, double max) {
        return RangeQuery.of(r -> r
                .field(fieldName)
                .lte(JsonData.of(max))
                .gte(JsonData.of(min))
        )._toQuery();
    }

    @Override
    public Query getBoolQuery(List<Query> queries) {
        return BoolQuery.of(b -> b.filter(queries))._toQuery();
    }


}
