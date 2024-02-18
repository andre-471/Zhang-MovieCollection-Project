public class Movie {
    private String title;
    private String[] cast;
    private String castString;
    private String director;
    private String overview;
    private int runtime;
    private double userRating;

    public Movie(String csvLine){
        String[] data = csvLine.split(",");
        title = data[0];
        cast = data[1].split("\\|");
        castString = data[1].replaceAll("\\|", ", ");
        director = data[2].replaceAll("\\|", ", ");
        overview = data[3];
        runtime = Integer.parseInt(data[4]);
        userRating = Double.parseDouble(data[5]);
    }

    public String getTitle() {
        return title;
    }

    public String[] getCast() {
        return cast;
    }

    public boolean containsActor(String actor) {
        for (String castMember : cast) {
            if (castMember.equals(actor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Cast: " + castString + "\n" +
                "Director: " + director + "\n" +
                "Overview: " + overview + "\n" +
                "Runtime: " + runtime + "\n" +
                "User Rating: " + userRating;
    }
}
