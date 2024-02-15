import java.util.Arrays;

public class Movie {
    private String title;
    private String[] cast;
    private String director;
    private String overview;
    private int runtime;
    private double userRating;
    public Movie(String csvLine){
        String[] data = csvLine.split(",");
        title = data[0];
        cast = data[1].split("\\|");
        director = data[2];
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

    public String getDirector() {
        return director;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getUserRating() {
        return userRating;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Cast: " + Arrays.toString(cast) + "\n" +
                "Director " + director + "\n" +
                "Overview: " + overview + "\n" +
                "Runtime: " + runtime + "\n" +
                "User Rating: " + userRating;
    }


}
