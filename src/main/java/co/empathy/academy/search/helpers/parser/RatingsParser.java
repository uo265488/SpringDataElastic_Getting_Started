package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.dto.RatingsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RatingsParser {

    private final BufferedReader bufferedReader;
    private RatingsDto actualLine;

    public RatingsParser(MultipartFile ratingsFile) {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(ratingsFile.getInputStream()));

            this.bufferedReader.readLine(); //to avoid first line
            this.actualLine = handleLine((this.bufferedReader.readLine()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns the Ratings correponding to the next line
     * @return
     */
    public Movie readLine(Movie movie) {
        if(movie == null) return null;
        if(actualLine.getId().equals(movie.getId())) {
            try {
                movie = movie.withRatings(this.actualLine);
                this.actualLine = handleLine(bufferedReader.readLine());
            } catch(IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return movie;
    }

    /**
     * Parsing lien to ratings of the movie
     * @param line
     * @return
     */
    protected RatingsDto handleLine(String line) {
        if(line == null) return null;
        String[] fields = line.split("\t");
        return new RatingsDto(
                    fields[0],
                    Double.valueOf(fields[1]),
                    Integer.valueOf(fields[2]));
    }
}
