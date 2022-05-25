package core;

import models.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleRepositoryImpl implements VehicleRepository {

    private Map<String, List<Vehicle>> vehicleBySeller;
    private List<String> sellers;
    private Map<String, Vehicle> vehicleById;
    private Map<String, List<Vehicle> >vehicleByBrand;


    public VehicleRepositoryImpl() {
        this.vehicleBySeller = new LinkedHashMap<>();
        this.sellers = new ArrayList<>();
        this.vehicleById = new TreeMap<>();
        this.vehicleByBrand = new TreeMap<>();
    }

    @Override
    public void addVehicleForSale(Vehicle vehicle, String sellerName) {
        this.vehicleBySeller.computeIfAbsent(sellerName, k -> new ArrayList<>()).add(vehicle);
        if (!this.sellers.contains(sellerName)) {
            this.sellers.add(sellerName);
        }
        this.vehicleById.put(vehicle.getId(), vehicle);
        this.vehicleByBrand.computeIfAbsent(sellerName, k -> new ArrayList<>()).add(vehicle);
    }

    @Override
    public void removeVehicle(String vehicleId) {

        Vehicle vehicle = this.vehicleById.remove(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException();
        }
        this.vehicleByBrand.values().forEach(e->e.remove(vehicle));

        this.vehicleBySeller.values()
                .forEach(a -> a.remove(vehicle));

    }

    @Override
    public int size() {
        return this.vehicleById.size();
    }

    @Override
    public boolean contains(Vehicle vehicle) {
        return this.vehicleById.containsValue(vehicle);
    }

    @Override
    public Iterable<Vehicle> getVehicles(List<String> keywords) {
        List<Vehicle> collect = this.vehicleById
                .values()
                .stream()
                .filter(a -> keywords.contains(a.getBrand()) || keywords.contains(a.getColor()) ||
                        keywords.contains(a.getModel()) || keywords.contains(a.getLocation()))
                .sorted((a, b) -> {
                    int cmp = Boolean.compare(a.getIsVIP(), b.getIsVIP());
                    if (cmp == 0) {
                        return Double.compare(a.getPrice(), b.getPrice());
                    }
                    return cmp;
                }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Iterable<Vehicle> getVehiclesBySeller(String sellerName) {
        if (!this.sellers.contains(sellerName)) {
            throw new IllegalArgumentException();
        }
        return this.vehicleBySeller.get(sellerName);
    }

    @Override
    public Iterable<Vehicle> getVehiclesInPriceRange(double lowerBound, double upperBound) {
        List<Vehicle> collect = this.vehicleById
                .values()
                .stream()
                .filter(a -> a.getPrice() >= lowerBound && a.getPrice() <= upperBound)
                .sorted(Comparator.comparingInt(Vehicle::getHorsepower).reversed())
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Map<String, List<Vehicle>> getAllVehiclesGroupedByBrand() {
       if (this.vehicleByBrand.isEmpty()) {
           throw new IllegalArgumentException();
       }
// private Map<String, List<Vehicle> >vehicleByBrand;
        return this.vehicleByBrand
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (entry) -> entry.getValue()
                                .stream()
                                .sorted(Comparator.comparingDouble(Vehicle::getPrice))
                                .collect(Collectors.toList())
                ));

    }

    @Override
    public Iterable<Vehicle> getAllVehiclesOrderedByHorsepowerDescendingThenByPriceThenBySellerName() {
        return null;
    }

    @Override
    public Vehicle buyCheapestFromSeller(String sellerName) {
        return null;
    }
}
