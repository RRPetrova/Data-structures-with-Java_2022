package vehicles;

public class Vehicle {
    final static double DEFAULT_FUEL_CONSUMPTION = 1.25;
    private double fuelConsumption;
    private double fuel;
    private int horsePower;

    public Vehicle(double fuel, int horsePower) {
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.setFuelConsumption(DEFAULT_FUEL_CONSUMPTION);
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public Vehicle setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public double getFuel() {
        return fuel;
    }

    public Vehicle setFuel(double fuel) {
        this.fuel = fuel;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public Vehicle setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public void drive(double kilometers) {
        double cost = this.fuelConsumption * kilometers;
        if (this.fuel >= cost) {
            this.fuel -= cost;
        }
    }
}
