package core;

import models.Doodle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoodleSearchImpl implements DoodleSearch {

    private Map<String, Doodle> doodleByIds;
    private  Map<String, Doodle> byTitle;

    public DoodleSearchImpl() {
        this.doodleByIds = new LinkedHashMap<>();
        this.byTitle = new HashMap<>();
    }

    @Override
    public void addDoodle(Doodle doodle) {
        this.doodleByIds.put(doodle.getId(), doodle);
        //todo put if absent -= 10 points judge
       this.byTitle.put(doodle.getTitle(), doodle);
    }

    @Override
    public void removeDoodle(String doodleId) {
        Doodle doodle = this.doodleByIds.remove(doodleId);
        if (doodle == null) {
            throw new IllegalArgumentException();
        }
        this.byTitle.remove(doodle.getTitle());
    }

    @Override
    public int size() {
        return this.doodleByIds.size();
    }

    @Override
    public boolean contains(Doodle doodle) {
        return this.doodleByIds.containsKey(doodle.getId());
    }

    @Override
    public Doodle getDoodle(String id) {
        Doodle doodle = this.doodleByIds.get(id);
        if (doodle == null) {
            throw new IllegalArgumentException();
        }
        return doodle;
    }

    @Override
    public double getTotalRevenueFromDoodleAds() {

        return this.doodleByIds
                .values()
                .stream()
                .filter(Doodle::getIsAd)
                .mapToDouble(a -> a.getVisits() * a.getRevenue())
                .sum();
    }

    @Override
    public void visitDoodle(String title) {
//        Doodle doodle = this.doodleByIds.values()
//                .stream()
//                .filter(a -> a.getTitle().equals(title))
//                .findFirst()
//                .orElse(null);
//
//        if (doodle == null) {
//            throw new IllegalArgumentException();
//        }
//        doodle.setVisits(doodle.getVisits() + 1);
        Doodle doodle = this.byTitle.get(title);
        if (doodle == null) throw new IllegalArgumentException();
        doodle.setVisits(doodle.getVisits()+1);
    }

    @Override
    public Iterable<Doodle> searchDoodles(String searchQuery) {
        return this.doodleByIds
                .values()
                .stream()
                .filter(a -> a.getTitle().contains(searchQuery))
                .sorted((a, b) -> {
                    int compare = Boolean.compare(b.getIsAd(), a.getIsAd());
                    if (compare == 0) {
                        int compare2 = Integer.compare(a.getTitle().indexOf(searchQuery), b.getTitle().indexOf(searchQuery));
                        if (compare2 == 0) {
                            return Integer.compare(b.getVisits(), a.getVisits());
                        }
                        return compare2;
                    }
                    return compare;
                })
                .collect(Collectors.toList());
    }



    @Override
    public Iterable<Doodle> getDoodleAds() {
        return this.doodleByIds
                .values()
                .stream()
                .filter(Doodle::getIsAd)
                 //.sorted(Comparator.comparingDouble(Doodle::getRevenue).reversed()
                 //        .thenComparingInt(Doodle::getVisits).reversed())
                .sorted((a, b) -> {
                    int cmp = Double.compare(b.getRevenue(), a.getRevenue());
                    if (cmp == 0) {
                        return  Integer.compare(b.getVisits() , a.getVisits());
                    }
                    return cmp;
                })
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Doodle> getTop3DoodlesByRevenueThenByVisits() {
        return this.doodleByIds
                .values()
                .stream()
                .sorted((a, b) -> {
                    int cmp = Double.compare(b.getRevenue(), a.getRevenue());
                    if (cmp == 0) {
                        cmp = Integer.compare(b.getVisits(), a.getVisits());
                    }
                    return cmp;
                })
                .limit(3)
                .collect(Collectors.toList());
    }

}
