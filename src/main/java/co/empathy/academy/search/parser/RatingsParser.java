package co.empathy.academy.search.parser;

import co.empathy.academy.search.documents.Movie;
import org.springframework.web.multipart.MultipartFile;

public class RatingsParser extends BaseParser {

    protected RatingsParser(MultipartFile file) {
        super(file);
    }

    /**
     * Parsing lien to ratings of the movie
     * @param line
     * @return
     */
    @Override
    protected Movie handleLine(String line) {
        if(line == null) return null;
        String[] fields = line.split("\t");

        return null; //numVotes
    }
}
