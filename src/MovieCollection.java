import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;

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
        menu();
    }

    private void menu() {
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
        for (Movie movie : findAndSortTitles(titleToSearch)) {
            System.out.println(movie.getTitle());
        }
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

        return sortMovies(relevantMovies, (m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
    }

    private ArrayList<Movie> sortMovies(ArrayList<Movie> mainSide, Comparator<Movie> comparator) {
        if (mainSide.size() == 1) {
            return mainSide;
        }

        int mid = mainSide.size() / 2;
        ArrayList<Movie> leftSide = sortMovies(new ArrayList<>(mainSide.subList(0, mid)), comparator);
        ArrayList<Movie> rightSide = sortMovies(new ArrayList<>(mainSide.subList(mid, mainSide.size())), comparator);

        int leftIdx = 0;
        int rightIdx = 0;
        int mainIdx = 0;

        while (leftIdx < leftSide.size() && rightIdx < rightSide.size()) {
            if (comparator.compare(leftSide.get(leftIdx), rightSide.get(rightIdx)) < 0) {
                mainSide.set(mainIdx, leftSide.get(leftIdx));
                leftIdx++;
            } else {
                mainSide.set(mainIdx, rightSide.get(rightIdx));
                rightIdx++;
            }
            mainIdx++;
        }

        while (leftIdx < leftSide.size()) {
            mainSide.set(mainIdx, leftSide.get(leftIdx));
            leftIdx++;
            mainIdx++;
        }

        while (rightIdx < rightSide.size()) {
            mainSide.set(mainIdx, rightSide.get(rightIdx));
            rightIdx++;
            mainIdx++;
        }

        return mainSide;

    }
}
