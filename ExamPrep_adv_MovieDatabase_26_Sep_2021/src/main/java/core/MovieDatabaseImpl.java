package core;

import models.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {

    private Map<String, Movie> idMovies;
    private Map<String, Integer> byActorsPlay;
//id, actor, count

    public MovieDatabaseImpl() {
        this.idMovies = new LinkedHashMap<>();
        this.byActorsPlay = new HashMap<>();
    }

    @Override
    public void addMovie(Movie movie) {
        this.idMovies.putIfAbsent(movie.getId(), movie);


        movie.getActors().forEach(a -> {
            this.byActorsPlay.putIfAbsent(a, 0);
            this.byActorsPlay.put(a, this.byActorsPlay.get(a) + 1);
        });

        //  System.out.println();
    }

    @Override
    public void removeMovie(String movieId) {
        Movie movie = this.idMovies.remove(movieId);
        if (movie == null) {
            throw new IllegalArgumentException();
        }

        movie.getActors()
                .forEach(a -> this.byActorsPlay.put(a, this.byActorsPlay.get(a) - 1));
    }

    @Override
    public int size() {
        return this.idMovies.size();
    }

    @Override
    public boolean contains(Movie movie) {
        return this.idMovies.containsKey(movie.getId());
    }

//    @Override
//    public Iterable<Movie> getMoviesByActor(String actorName) {
//        List<Movie> res = this.idMovies
//                .values()
//                .stream()
//                .filter(a -> a.getActors().contains(actorName))
//                .sorted(Comparator.comparingDouble(Movie::getRating).reversed()
//                        .thenComparingInt(Movie::getReleaseYear).reversed())
//                .collect(Collectors.toList());
//        if (res.isEmpty()) {
//            throw new IllegalArgumentException();
//        }
//        return res;
//    }

    @Override
    public Iterable<Movie> getMoviesByActor(String actorName) {
        List<Movie> movies = this.idMovies.values()
                .stream()
                .filter(m -> m.getActors().contains(actorName))
                .sorted((m1, m2) -> {
                    int ratingCompare = Double.compare(m2.getRating(), m1.getRating());
                    if (ratingCompare == 0) {
                        return Integer.compare(m2.getReleaseYear(), m1.getReleaseYear());
                    }
                    return ratingCompare;
                })
                .collect(Collectors.toList());

        if (movies.isEmpty()) throw new IllegalArgumentException();
        return movies;
    }

    @Override
    public Iterable<Movie> getMoviesByActors(List<String> actors) {
        List<Movie> res = this.idMovies
                .values()
                .stream()
                .filter(a -> a.getActors().containsAll(actors))
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed()
                        .thenComparing(Movie::getReleaseYear).reversed())
                .collect(Collectors.toList());
        if (res.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return res;
    }

//    @Override
//    public Iterable<Movie> getMoviesByActors(List<String> actors) {
//        List<Movie> movies = this.idMovies.values()
//                .stream()
//                .filter(m -> m.getActors().containsAll(actors))
//                .sorted((m1, m2) -> {
//                    int ratingCompare = Double.compare(m2.getRating(), m1.getRating());
//                    if (ratingCompare == 0) {
//                        return Integer.compare(m2.getReleaseYear(), m1.getReleaseYear());
//                    }
//
//                    return ratingCompare;
//                })
//                .collect(Collectors.toList());
//
//        if (movies.isEmpty()) throw new IllegalArgumentException();
//        return movies;
//    }

    @Override
    public Iterable<Movie> getMoviesByYear(Integer releaseYear) {
        return this.idMovies
                .values()
                .stream()
                .filter(a -> a.getReleaseYear() == releaseYear)
               .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<Movie> getMoviesInRatingRange(double lowerBound, double upperBound) {
        return this.idMovies
                .values()
                .stream()
                .filter(a -> a.getRating() >= lowerBound && a.getRating() <= upperBound)
               .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<Movie> getAllMoviesOrderedByActorPopularityThenByRatingThenByYear() {
        return this.idMovies
                .values()
                .stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getActors().stream().mapToInt(c -> this.byActorsPlay.get(c)).sum(),
                            a.getActors().stream().mapToInt(c -> this.byActorsPlay.get(c)).sum());

                    if (cmp == 0) {
                        cmp = Double.compare(b.getRating(), a.getRating());
                    }
                    if (cmp == 0) {
                       return Integer.compare( b.getReleaseYear() , a.getReleaseYear());
                    }
                    return cmp;
                }).collect(Collectors.toList());
    }
}