package com.java.core;

import com.java.models.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoovItImpl implements MoovIt {

    private Map<String, Route> routesById;


    public MoovItImpl() {
        this.routesById = new HashMap<>();
    }

    @Override
    public void addRoute(Route route) {
        if (routesById.containsKey(route.getId())) {
            throw new IllegalArgumentException();
        }
        this.routesById.put(route.getId(), route);
    }

    @Override
    public void removeRoute(String routeId) {
        if (!this.routesById.containsKey(routeId)) {
            throw new IllegalArgumentException();
        }
        Route route = this.routesById.get(routeId);
        this.routesById.remove(route);
    }

    @Override
    public boolean contains(Route route) {
       /* if (this.routesById.containsKey(route.getId())) {
            return true;
        }

        Route route1 = this.routesById
                .values()
                .stream()
                .filter(a -> Objects.equals(a.getDistance(), route.getDistance()) &&
                        a.getIsFavorite() == route.getIsFavorite() &&
                        Objects.equals(a.getPopularity(), route.getPopularity()))
                .findFirst()
                .orElse(null);
        if (route1 != null) {
            return true;
        }
*/
        return this.routesById.containsKey(route.getId());
    }

    @Override
    public int size() {
        return this.routesById.size();
    }

    @Override
    public Route getRoute(String routeId) {
        if (!this.routesById.containsKey(routeId)) {
            throw new IllegalArgumentException();
        }
        return this.routesById.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        if (!this.routesById.containsKey(routeId)) {
            throw new IllegalArgumentException();
        }
        Route route = this.routesById.get(routeId);
        route.setPopularity(route.getPopularity() + 1);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> filtered = this.routesById
                .values()
                .stream()
                .filter(a -> a.getLocationPoints().contains(startPoint)
                        && a.getLocationPoints().contains(endPoint))
                .collect(Collectors.toList());

        Map<Route, Integer> distancePoints = new LinkedHashMap<>();

        int distance = 0;
        for (Route route : filtered) {
            for (int i = 0; i < route.getLocationPoints().size(); i++) {

                if (route.getLocationPoints().get(i) == startPoint) {
                    distance = i;
                    // sp = 3
                }
                if (route.getLocationPoints().get(i) == endPoint) {
                    //end = 5 => distance = 2 points
                    distance = i - distance;
                }
            }
            distancePoints.put(route, distance);
        }


        LinkedHashMap<Route, Integer> collect = distancePoints
                .entrySet()
                .stream()
                .sorted((a, b) -> {
                    int cmp = Boolean.compare(a.getKey().getIsFavorite(), b.getKey().getIsFavorite());
                    if (cmp == 0) {
                        cmp = Integer.compare(a.getValue() , b.getValue());
                        if (cmp == 0) {
                            return Integer.compare(b.getKey().getPopularity() , a.getKey().getPopularity());
                        }
                    }
                    return cmp;
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return collect.keySet();
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return this.routesById
                .values()
                .stream()
                .filter(a -> a.getIsFavorite()
                        && a.getLocationPoints().contains(destinationPoint)
                       && !a.getLocationPoints().get(0).equals(destinationPoint))
                // .sorted(Comparator.comparingDouble(Route::getDistance)
                //          .thenComparing(Route::getPopularity).reversed())
                .sorted((a, b) -> {
                    int cmp = Double.compare(a.getDistance(), b.getDistance());
                    if (cmp == 0) {
                        return Integer.compare(b.getPopularity() , a.getPopularity());
                    }
                    return cmp;
                })
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Route> getTop5RoutesByPopularityThenByDistanceThenByCountOfLocationPoints() {
        return this.routesById
                .values()
                .stream()
                .sorted((a, b) -> {
                            int cmp = Integer.compare(b.getPopularity() , a.getPopularity());
                            if (cmp == 0) {
                                cmp = Double.compare(a.getDistance(), b.getDistance());
                                if (cmp == 0) {
                                    return Integer.compare(a.getLocationPoints().size() , b.getLocationPoints().size());
                                }
                            }
                            return cmp;
                        }
                )
                .limit(5)
                .collect(Collectors.toList());
    }
}
