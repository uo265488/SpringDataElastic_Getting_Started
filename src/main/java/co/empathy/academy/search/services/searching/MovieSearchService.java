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
    public ResponseModel filterQuery(
            String fieldName, String value, int size, String sortOrder, String orderBy) {

        return new ResponseModel(repository.executeQuery(
                factory.getFilterQuery(fieldName, value), size, sortOrder, orderBy));
    }

    /**
     * Calls the searchRepository to obtain the hits of the filterQuery
     * @param fieldName name of the field
     * @param values values of the field
     * @return ResponseModel
     */
    public ResponseModel filterQuery(
            String fieldName, String[] values, int size, String sortOrder, String orderBy) {
        ResponseModel responseModel = new ResponseModel(repository.executeQuery(
                factory.getFilterQuery(fieldName, values[0]), size, sortOrder, orderBy));
        for(int i = 1; i < values.length; i++) {
            responseModel.addHits(repository.executeQuery(
                            factory.getFilterQuery(fieldName, values[i]), size));
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
    public ResponseModel rangeQuery(String fieldName, double min, double max, int size, String ordering,
                                    String orderBy) {

        return new ResponseModel(repository.executeQuery(
                factory.getRangeQuery(fieldName, min, max), size, ordering, orderBy));
    }

}
