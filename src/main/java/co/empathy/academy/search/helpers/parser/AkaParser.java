package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.Aka;
import co.empathy.academy.search.documents.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AkaParser {

    private final BufferedReader bufferedReader;

    private List<Aka> actualAkas = new ArrayList<>();
    private Aka nextAka;
    private boolean moreAkas;

    public AkaParser(MultipartFile akasFile) {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(akasFile.getInputStream()));
            this.bufferedReader.readLine(); //to avoid first line
            loadAkas();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets the next List of Akas
     * @param
     * @return
     */
    protected void loadAkas() {
        try {
            Aka aka = handleLine(bufferedReader.readLine());
            while(actualAkas.isEmpty() || (aka != null && aka.getTconst().equals(actualAkas.get(0).getTconst()))) {
                actualAkas.add(aka);
                aka = handleLine(bufferedReader.readLine());

            }
            nextAka = aka;
            moreAkas = nextAka != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the list of akas correspondig to the id
     * @param movie
     * @return
     */
    protected Movie getAkas(Movie movie) {
        Movie result = movie;
        if(moreAkas && (movie != null || actualAkas.get(0).getTconst().equals(movie.getId()))) {
            result = movie.setAkas(
                    actualAkas.stream().map(a -> a.getTconst()).toArray(o -> new String[actualAkas.size()]),
                    actualAkas.stream().map(a -> a.getTitle()).toArray(o -> new String[actualAkas.size()]),
                    actualAkas.stream().map(a -> a.getRegion()).toArray(o -> new String[actualAkas.size()]),
                    actualAkas.stream().map(a -> a.getLanguage()).toArray(o -> new String[actualAkas.size()]));
            this.actualAkas = new ArrayList<>();
            actualAkas.add(nextAka);
            loadAkas();
            return result;
        }
        return result;
    }

    /**
     * Parses one line to an object
     * @param
     * @return
     */
    private Aka handleLine(String line) {
        if(line == null) return null;
        String[] fields = line.split("\t");
        return new Aka(
                fields[0],
                fields[2],
                fields[3],
                fields[4]);
    }
}
