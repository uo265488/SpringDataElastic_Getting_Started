package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Director;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.documents.Name;
import co.empathy.academy.search.documents.Star;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DirectorParser {
    private final BufferedReader bufferedReader;

    private List<Director> actualDirectors = new ArrayList<>();
    private Director nextDirector;

    public DirectorParser(MultipartFile participantsFile) {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(participantsFile.getInputStream()));
            this.bufferedReader.readLine(); //to avoid first line
            loadDirectors();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets the next List of Akas
     *
     * @param
     * @return
     */
    protected void loadDirectors() {
        Director director = lookForDirector();
        while(actualDirectors.isEmpty() || director.getTconst().equals(actualDirectors.get(0).getTconst())) {
            actualDirectors.add(director);
            director = lookForDirector();
        }
        nextDirector = director;
    }

    /**
     * Looks for the next star
     * @return
     */
    public Director lookForDirector() {
        Director director;
        try {
            director = handleLine(bufferedReader.readLine());
            while(director == null) {
                director = handleLine(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return director;
    }

    /**
     * Returns the starring corresponding to the id
     * @param movie
     * @return
     */
    protected Movie getDirectors(Movie movie) {
        Movie result = movie;
        if( movie != null && !actualDirectors.isEmpty()) {
            int tconst1Size = actualDirectors.get(0).getTconst().length();
            int tconst2Size = movie.getTconst().length();
            while(tconst2Size < tconst1Size) {
                result = movie.setTconst(
                        movie.getTconst().substring(0,2) +
                                "0"  +
                        movie.getTconst().substring(2)
                );
                tconst2Size++;
            }
            if (actualDirectors.get(0).getTconst().equals(result.getTconst())) {
                result = movie.setDirectors(
                        actualDirectors.stream().map(a -> a.getNconst())
                                .toArray(o -> new String[actualDirectors.size()]));
                this.actualDirectors = new ArrayList<>();
                actualDirectors.add(nextDirector);
                loadDirectors();
                return result;
            }
        }
        return result;
    }

    /**
     * Parses one line to a director
     *
     * @param
     * @return
     */
    private Director handleLine(String line) {
        if (line == null) return null;
        String[] fields = line.split("\t");
        if (fields[3].equals("director")) {
            return new Director(
                    fields[2],
                    fields[0]
            );
        }
        return null;
    }
}
