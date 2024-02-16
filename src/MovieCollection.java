import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private LinkedHashMap<String, ArrayList<Movie>> actorMovieHashMap;
    private static Scanner scanner = new Scanner(System.in);

    public MovieCollection(String dataFilePath) {
        movies = new ArrayList<>();
        ArrayList<String> actors = new ArrayList<>();
        actorMovieHashMap = new LinkedHashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFilePath))) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null) {
                Movie movie = new Movie(line);
                movies.add(movie);
                for (String actor : movie.getCast()) {
                    if (!actors.contains(actor)) {
                        actors.add(actor);
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        sort(movies, (m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        sort(actors, String::compareToIgnoreCase);
        for (String actor : actors) {
            ArrayList<Movie> actorMovies = new ArrayList<>();
            actorMovieHashMap.put(actor, actorMovies);
            for (Movie movie : movies) {
                if (movie.containsActor(actor)) {
                    actorMovies.add(movie);
                }
            }
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
        String titleToSearch = scanner.nextLine().trim();
        ArrayList<Movie> filteredMovies = filterTitles(titleToSearch);
        if (!filteredMovies.isEmpty()) {
            viewMovieInfo(filteredMovies);
        } else {
            System.out.println("No movie titles match that search term!");
        }

    }

    private void searchCast() {
        String nameToSearch = scanner.nextLine().trim().toLowerCase();
        ArrayList<String> filteredActors = filterActors(nameToSearch);
        if (!filteredActors.isEmpty()) {
            for (int i = 0; i < filteredActors.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, filteredActors.get(i));
            }

            int actorIdx = scannerGetInt(1, filteredActors.size()) - 1;
            viewMovieInfo(actorMovieHashMap.get(filteredActors.get(actorIdx)));
        } else {
            System.out.println("no");
        }

    }

    private void viewMovieInfo(ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, movies.get(i).getTitle());
        }

        System.out.print("number ");
        int movieIdx = scannerGetInt(1, movies.size()) - 1;
        System.out.println(movies.get(movieIdx));
    }

    private ArrayList<Movie> filterTitles(String title) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    private ArrayList<String> filterActors(String otherActor) {
        ArrayList<String> filteredActors = new ArrayList<>();
        for (String actor : actorMovieHashMap.keySet()) {
            if (actor.toLowerCase().contains(otherActor.toLowerCase())) {
                filteredActors.add(actor);
            }
        }

        return filteredActors;
    }

    private <T> ArrayList<T> sort(ArrayList<T> mainSide, Comparator<T> comparator) {
        if (mainSide.size() <= 1) {
            return mainSide;
        }

        int mid = mainSide.size() / 2;
        ArrayList<T> leftSide = sort(new ArrayList<>(mainSide.subList(0, mid)), comparator);
        ArrayList<T> rightSide = sort(new ArrayList<>(mainSide.subList(mid, mainSide.size())), comparator);

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

    private int scannerGetInt(int low, int max) {
        int integer;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Enter an integer: ");
                scanner.nextLine();
            }
            integer = scanner.nextInt();
            scanner.nextLine();
            if (integer < low || integer > max) {
                System.out.printf("Enter a integer between %d - %d inclusive: ", low, max);
            }
        } while (integer < low || integer > max);
        return integer;
    }
}
