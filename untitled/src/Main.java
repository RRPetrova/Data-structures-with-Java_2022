import vehicles.Motorcycle;
import vehicles.RaceMotorcycle;
import vehicles.SportCar;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RaceMotorcycle s = new RaceMotorcycle(100,200);
        System.out.println(s.getFuel());
        s.drive(10);
        System.out.println(s.getFuel());

    }
}


