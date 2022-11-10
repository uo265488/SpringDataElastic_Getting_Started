package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.indexing.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseParser {

    @Autowired
    private IndexRepository indexingRepository;

    InputStream inputStream;
    private final BufferedReader bufferedReader;

    private RatingsParser ratingsParser;

    protected BaseParser(MultipartFile titleBasicsFile, MultipartFile ratingsFile)  {
        try {
            this.inputStream = titleBasicsFile.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            this.ratingsParser = new RatingsParser(ratingsFile);

            this.bufferedReader.readLine(); //to avoid first line
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Parses to movies the number of lines specified by parameter
     * @param numberOfMovies
     * @return
     */
    public List<Movie> parseMovies(int numberOfMovies) {
        List<Movie> movies = new ArrayList<>();
        Movie movie;
        try{
            while(numberOfMovies > 0) {
                movie = handleLine(bufferedReader.readLine());
                movies.add(ratingsParser.readLine(movie));

                numberOfMovies--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return movies;
    }

    /**
     * Tamplate method to parse line
     * @param line
     * @return Movie
     */
    protected abstract Movie handleLine(String line);

}