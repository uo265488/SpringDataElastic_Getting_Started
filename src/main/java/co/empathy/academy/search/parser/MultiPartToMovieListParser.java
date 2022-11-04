package co.empathy.academy.search.parser;

import co.empathy.academy.search.documents.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MultiPartToMovieListParser {

    List<Movie> movies = new ArrayList<>();

    public List<Movie> toMovieList(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .forEach(this::handleLine);

        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }

        return movies;
    }

    public void handleLine(String line) {
        String[] fields = line.split("\t");
        if(!fields[0].equals("tconst")) { //NO COMMENTS ON THIS...
            Movie movie = new Movie(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4] == "0" ? true : false,
                    !(fields[5].equals("\\N")) ? Integer.parseInt(fields[5]) : Movie.UNDEFINED,
                    !(fields[6].equals("\\N")) ? Integer.parseInt(fields[6]) : Movie.UNDEFINED,
                    !(fields[7].equals("\\N")) ? Integer.parseInt(fields[7]) : Movie.UNDEFINED,
                    fields[8]);
            movies.add(movie);
        }

    }

}
