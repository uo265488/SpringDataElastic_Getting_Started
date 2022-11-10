package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Movie;
import org.springframework.web.multipart.MultipartFile;

public class MovieParser extends BaseParser {

    public MovieParser(MultipartFile titleBasicsFile, MultipartFile ratingsFile) {
        super(titleBasicsFile, ratingsFile);
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
                    !(fields[5].equals("\\N")) ? Integer.parseInt(fields[5]) : Movie.UNDEFINED,
                    !(fields[6].equals("\\N")) ? Integer.parseInt(fields[6]) : Movie.UNDEFINED,
                    !(fields[7].equals("\\N")) ? Integer.parseInt(fields[7]) : Movie.UNDEFINED,
                    fields[8],
                    0,
                    0);

        }
        return movie;
    }

}
