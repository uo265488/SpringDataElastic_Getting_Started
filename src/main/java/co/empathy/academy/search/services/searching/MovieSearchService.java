package co.empathy.academy.search.services.searching;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.empathy.academy.search.documents.FieldAttr;
import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.repositories.searching.MovieSearchRepository;
import co.empathy.academy.search.repositories.searching.QueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                            factory.getFilterQuery(fieldName, values[i]), size, sortOrder, orderBy));
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

    /**
     * Performs the filter query
     *
     * @param type
     * @param genres
     * @param minScore
     * @param maxScore
     * @param minYear
     * @param maxYear
     * @param minMinutes
     * @param maxMinutes
     * @param size
     * @param sortOrder
     * @param sortBy
     * @param sortRating
     * @return
     */
    public ResponseModel filterQuery(Optional<String> type,
                                     Optional<String> genres,
                                     double minScore,
                                     double maxScore,
                                     int minYear,
                                     Optional<Integer> maxYear,
                                     int minMinutes,
                                     Optional<Integer> maxMinutes,
                                     int size,
                                     Optional<String> sortOrder,
                                     Optional<String> sortBy,
                                     Optional<String> sortRating) {

        if(sortRating.isPresent())
            return new ResponseModel(
                    repository.executeQuery(
                            factory.getBoolQuery(
                                    getQueriesList(type, genres, minMinutes, minScore,
                                            minYear, maxMinutes, maxScore, maxYear)),
                            size,
                            FieldAttr.Movie.RATING_FIELD,
                            sortRating.get()));


        return new ResponseModel(
                repository.executeQuery(
                factory.getBoolQuery(
                        getQueriesList(type, genres, minMinutes, minScore,
                                minYear, maxMinutes, maxScore, maxYear)),
                size,
                sortOrder.isPresent() ? sortOrder.get() : "",
                sortBy.isPresent() ? sortBy.get() : ""));
    }

    private List<Query> getQueriesList(Optional<String> type,
                                       Optional<String> genres,
                                       int minMinutes,
                                       double minScore,
                                       int minYear,
                                       Optional<Integer> maxMinutes,
                                       double maxScore,
                                       Optional<Integer> maxYear) {
        List<Query> queries = new ArrayList<>();
        if(type.isPresent()) queries.add(factory.getFilterQuery(FieldAttr.Movie.TITLE_TYPE_FIELD, type.get()));
        if(genres.isPresent())
            Arrays.stream(genres.get().split(",")).forEach(g -> queries.add(factory.getFilterQuery(FieldAttr.Movie.GENRES_FIELD, g)));
        queries.add(
                factory.getRangeQuery(FieldAttr.Movie.MINUTES_FIELD, minMinutes,
                        maxMinutes.isPresent()
                        ? maxMinutes.get()
                        : Integer.MAX_VALUE ));
        queries.add(factory.getRangeQuery(FieldAttr.Movie.RATING_FIELD, minScore, maxScore));
        queries.add(factory.getRangeQuery(FieldAttr.Movie.START_YEAR_FIELD, minYear, maxYear.isPresent() ? maxYear.get() : Integer.MAX_VALUE));

        return queries;
    }
}
