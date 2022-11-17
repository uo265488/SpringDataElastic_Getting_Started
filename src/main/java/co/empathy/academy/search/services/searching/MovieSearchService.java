package co.empathy.academy.search.services.searching;

import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.repositories.searching.MovieSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MovieSearchService {

    @Autowired
    private MovieSearchRepository repository;

    /**
     * Calls the searchRepository to obtain the hits of the filterQuery
     * @param fieldName name of the field
     * @param value value of the field
     * @return ResponseModel
     */
    public ResponseModel filterQuery(String fieldName, String value) {
        return new ResponseModel(repository.filterQuery(fieldName, value));
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
            responseModel.addHits(repository.filterQuery(fieldName, values[i]));
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
        return new ResponseModel(repository.rangeQuery(fieldName, min, max));
    }
}
