package co.empathy.academy.search.helpers.parser;

import co.empathy.academy.search.documents.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StarringParser {
        private final BufferedReader bufferedReader;

        private List<Star> actualStarring = new ArrayList<>();

        private Star nextStar;
        public StarringParser(MultipartFile participantsFile) {
            try {
                this.bufferedReader = new BufferedReader(new InputStreamReader(participantsFile.getInputStream()));
                this.bufferedReader.readLine(); //to avoid first line
                loadStarring();
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
        protected void loadStarring() {
                Star star = lookForStar();
                while(actualStarring.isEmpty() || star.getTconst().equals(actualStarring.get(0).getTconst())) {
                    actualStarring.add(star);
                    star = lookForStar();
                }
                nextStar = star;
        }

    /**
     * Looks for the next star
     * @return
     */
    public Star lookForStar() {
            Star star;
            try {
                star = handleLine(bufferedReader.readLine());
                while(star == null) {
                    star = handleLine(bufferedReader.readLine());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return star;
    }

        /**
         * Parses one line to a star
         *
         * @param
         * @return
         */
        private Star handleLine(String line) {
            if (line == null) return null;
            String[] fields = line.split("\t");
            if (fields[3].equals("actor")) {
                return new Star(
                        fields[0],
                        new Name(fields[2]),
                        fields[5]

                );
            }
            return null;
        }

    /**
     * Returns the starring corresponding to the id
     * @param movie
     * @return
     */
    protected Movie getStarring(Movie movie) {
        Movie result = movie;
        if( movie != null && !actualStarring.isEmpty()) {
            if (actualStarring.get(0).getTconst().equals(movie.getId())) {
                result = movie.setStarring(actualStarring);
                this.actualStarring = new ArrayList<>();
                actualStarring.add(nextStar);
                loadStarring();
                return result;
            }
        }
        return result;
    }
}
