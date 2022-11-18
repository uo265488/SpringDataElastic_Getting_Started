package co.empathy.academy.search.documents;

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

    }
}
