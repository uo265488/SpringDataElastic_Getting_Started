package co.empathy.academy.search.documents;

import java.util.Optional;

public final class FieldAttr {

    private FieldAttr() {
    }

    public static class Movie {

        private Movie() {
        }

        public static final String PRIMARY_TITLE_FIELD = "primaryTitle";
        public static final String ORIGINAL_TITLE_FIELD = "originalTitle";
        public static final String IS_ADULT_FIELD = "isAdult";
        public static final String TITLE_TYPE_FIELD = "titleType";
        public static final String GENRES_FIELD = "genres";
        public static final String START_YEAR_FIELD = "startYear";
        public static final String END_YEAR_FIELD = "endYear";
        public static final String MINUTES_FIELD = "runtimeMinutes";
        public static final String RATING_FIELD = "averageRating";
        public static final String VOTES_FIELD = "numVotes";

        public static boolean isField(String orderBy) {
            return orderBy.equals(PRIMARY_TITLE_FIELD) ||
                    orderBy.equals(ORIGINAL_TITLE_FIELD) ||
                    orderBy.equals(IS_ADULT_FIELD) ||
                    orderBy.equals(TITLE_TYPE_FIELD) ||
                    orderBy.equals(GENRES_FIELD) ||
                    orderBy.equals(START_YEAR_FIELD) ||
                    orderBy.equals(END_YEAR_FIELD) ||
                    orderBy.equals(MINUTES_FIELD) ||
                    orderBy.equals(RATING_FIELD) ||
                    orderBy.equals(VOTES_FIELD);

        }
    }
}
