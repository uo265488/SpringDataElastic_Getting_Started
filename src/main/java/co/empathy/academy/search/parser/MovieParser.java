package co.empathy.academy.search.parser;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.indexing.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MovieParser {

    @Autowired
    private IndexRepository indexingRepository;

    InputStream inputStream;
    private final BufferedReader bufferedReader;

    public MovieParser(MultipartFile file)  {
        try {
            this.inputStream = file.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            this.bufferedReader.readLine(); //bruh
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
        try{
            while(numberOfMovies > 0) {
                movies.add(handleLine(bufferedReader.readLine()));
                numberOfMovies--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return movies;
    }

    /**
     * Process lines in batches to prevent an OutOfMemoryError
     * @param lines
     */
    private void handleLines(List<String> lines) {
        int numThreads = 20000;
        for(int i = 0; i < lines.size(); i = i + numThreads) {
            List<Movie> movieList = new ArrayList<>();
            lines.subList(i, i < lines.size() / numThreads ? i + numThreads : lines.size())
                            .stream()
                            .forEach(l -> {
                                Movie movie = handleLine(l);
                                if(movie != null) movieList.add(handleLine(l));
                            });

            indexingRepository.synchronousBulkIndexing(movieList);
        }
    }

    /**
     * Parses a line to Movie
     * @param line
     * @return Movie
     */
    public Movie handleLine(String line) {
        if(line == null) return null;
        String[] fields = line.split("\t");
        Movie movie = null;
        if(!fields[0].equals("tconst")) { //NO COMMENTS ON THIS...
            movie = new Movie(
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4] == "0" ? true : false,
                    !(fields[5].equals("\\N")) ? Integer.parseInt(fields[5]) : Movie.UNDEFINED,
                    !(fields[6].equals("\\N")) ? Integer.parseInt(fields[6]) : Movie.UNDEFINED,
                    !(fields[7].equals("\\N")) ? Integer.parseInt(fields[7]) : Movie.UNDEFINED,
                    fields[8]);

        }
        return movie;
    }

}
