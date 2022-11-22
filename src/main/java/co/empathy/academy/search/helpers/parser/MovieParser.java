package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Aka;
import co.empathy.academy.search.documents.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MovieParser extends BaseParser {

    public MovieParser(MultipartFile titleBasicsFile, MultipartFile ratingsFile, MultipartFile akasFile,
                       MultipartFile principalsFile) {
        super(titleBasicsFile, ratingsFile, akasFile, principalsFile);
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
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4] == "0" ? true : false,
                    !(fields[5].equals("\\N")) ? Integer.parseInt(fields[5]) : -1,
                    !(fields[6].equals("\\N")) ? Integer.parseInt(fields[6]) : -1,
                    !(fields[7].equals("\\N")) ? Integer.parseInt(fields[7]) : -1,
                    getGenres(fields[8]),
                    0,
                    0,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

        }
        return movie;
    }

    private List<String> getGenres(String field) {
        List<String> genres = new ArrayList<>();
        if(!field.equals("\\N")) {
            String[] fields = field.split(",");
            for(int i = 0; i < fields.length; i++) {
                genres.add(fields[i]);
            }
        }
        return genres;
    }

}
