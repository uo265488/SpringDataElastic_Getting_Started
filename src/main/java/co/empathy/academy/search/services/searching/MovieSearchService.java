package co.empathy.academy.search.services.searching;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.repositories.searching.MovieSearchRepository;
import co.empathy.academy.search.repositories.searching.QueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieSearchService {

    @Autowired
    private MovieSearchRepository repository;

    @Autowired
    private QueryFactory factory;

    /**
     * Calls the searchRepository to obtain the hits of the filterQuery
     * @param fieldName name of the field
     * @param value value of the field
     * @return ResponseModel
     */
    public ResponseModel filterQuery(String fieldName, String value) {
        return new ResponseModel(repository.executeQuery(
                factory.getFilterQuery(fieldName, value), 100));
    }

    /**
     * Calls the searchRepository to obtain the hits of the filterQuery
     * @param fieldName name of the field
     * @param values values of the field
     * @return ResponseModel
     */
    public ResponseModel filterQuery(String fieldName, String[] values) {
        ResponseModel responseModel = new ResponseModel(new ArrayList<>());
        for(int i = 0; i < values.length; i++) {
            responseModel.addHits(repository.executeQuery(
                            factory.getFilterQuery(fieldName, values[i]), 100));
        }
        return responseModel;
    }

    /**
     * Calls the searchRepository to obtain the hits of a rangeQuery
     * @param fieldName name of the field
     * @param min min value
     * @param max max value
     * @return
     */
    public ResponseModel rangeQuery(String fieldName, int min, int max) {
        return new ResponseModel(repository.executeQuery(
                factory.getRangeQuery(fieldName, min, max), 100));
    }

}
