import core.DoodleSearch;
import core.DoodleSearchImpl;
import core.MovieDatabase;
import core.MovieDatabaseImpl;
import models.Doodle;
import models.Movie;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

      /*  MovieDatabase movieDatabase =new MovieDatabaseImpl();

        Movie Movie = new Movie("asd", "bsd", 2010, 5000, List.of("Pesho", "Gosho"));
        Movie Movie2 = new Movie("dsd", "esd", 2012, 4000, List.of("Sasho", "Tosho","Pesho"));
        Movie Movie3 = new Movie("hsd", "isd", 2012, 4000, List.of("Sasho", "Tosho"));
        Movie Movie4 = new Movie("ksd", "lsd", 2013, 4500, List.of("Pesho", "Gosho"));
        Movie Movie5 = new Movie("nsd", "osd", 2014, 4500, List.of("Pesho", "Gosho"));

        movieDatabase.addMovie(Movie);
        movieDatabase.addMovie(Movie2);
        movieDatabase.addMovie(Movie3);
        movieDatabase.addMovie(Movie4);
        movieDatabase.addMovie(Movie5);

        movieDatabase.getMoviesByActor("Pesho");*/

        DoodleSearch doodleSearch = new DoodleSearchImpl();

        Doodle Doodle = new Doodle("asd", "bbbsd", 4000, true, 5.5);
        Doodle Doodle2 = new Doodle("nsd", "eesd", 4000, false, 5.6);
        Doodle Doodle3 = new Doodle("dsd", "ddsd", 5000, false, 5.7);
        Doodle Doodle4 = new Doodle("hsd", "zsd", 4000, true, 4.8);
        Doodle Doodle5 = new Doodle("qsd", "qsd", 4001, true, 4.8);
        Doodle Doodle6 = new Doodle("zsd", "ds", 5000, false, 5.7);

       doodleSearch.addDoodle(Doodle);
       doodleSearch.addDoodle(Doodle2);
       doodleSearch.addDoodle(Doodle3);
       doodleSearch.addDoodle(Doodle4);
       doodleSearch.addDoodle(Doodle5);
       doodleSearch.addDoodle(Doodle6);


        System.out.println();
        Iterable<models.Doodle> doodleAds = doodleSearch.getDoodleAds();
        doodleSearch.visitDoodle("ds");
        doodleSearch.visitDoodle("ds");
        System.out.println();
    }
    private static   Doodle getRandomDoodle() {
        return new Doodle(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                (int) Math.min(1, Math.random() * 2_000),
                ((int) Math.min(1, Math.random() * 2_000_000_000) % 2 == 1),
                Math.min(1, Math.random() * 1000));
    }

}

