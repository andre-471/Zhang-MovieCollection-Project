import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private static Scanner scanner = new Scanner(System.in);
    public MovieCollection(String dataFilePath) {
        movies = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFilePath))) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null) {
                movies.add(new Movie(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    private void Menu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void searchTitles() {
        String titleToSearch = scanner.nextLine().trim().toLowerCase();
    }

    private void searchCast() {

    }

    private ArrayList<Movie> findAndSortTitles(String title) {
        ArrayList<Movie> relevantMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title)) {
                relevantMovies.add(movie);
            }
        }


    }

    private ArrayList<Movie> sortTitles(ArrayList<Movie> unsortedMovies, Comparator<Movie> comparator) {
        if (unsortedMovies.size() == 1) {
            return unsortedMovies;
        }

        int mid = unsortedMovies.size() / 2;
        ArrayList<Movie> leftSide = sortTitles(new ArrayList<>(unsortedMovies.subList(0, mid)), comparator);
        ArrayList<Movie> rightSize = sortTitles(new ArrayList<>(unsortedMovies.subList(mid, unsortedMovies.size())), comparator);
    }
}
