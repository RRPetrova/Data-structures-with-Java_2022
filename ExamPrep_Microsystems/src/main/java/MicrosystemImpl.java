import java.util.*;
import java.util.stream.Collectors;

public class MicrosystemImpl implements Microsystem {

    private Map<Integer, Computer> comps;


    public MicrosystemImpl() {
        this.comps = new HashMap<>();

    }

    @Override
    public void createComputer(Computer computer) {
        if (comps.containsKey(computer.getNumber())) {
            throw new IllegalArgumentException();
        }
        this.comps.put(computer.getNumber(), computer);

    }

    @Override
    public boolean contains(int number) {
        return this.comps.containsKey(number);
    }

    @Override
    public int count() {
        return this.comps.size();
    }

    @Override
    public Computer getComputer(int number) {
        Computer computer = this.comps.get(number);
        if (computer == null) {
            throw new IllegalArgumentException();
        }
        return computer;
    }

    @Override
    public void remove(int number) {

        Computer computer = this.comps.get(number);
        if (computer == null) {
            throw new IllegalArgumentException();
        }
        this.comps.remove(number);

    }

    @Override
    public void removeWithBrand(Brand brand) {
//    boolean removed = false;
//        for (Computer comp : this.comps.values()) {
//            if (comp.getBrand().equals(brand)) {
//               this.comps.remove(comp.getNumber());
//               removed = true;
//            }
//        }
//        if (!removed) {
//            throw new IllegalArgumentException();
//        }
        List<Computer> byBrand = new ArrayList<>();
        for (Computer comp : this.comps.values()) {
            if (comp.getBrand().equals(brand)) {
                byBrand.add(comp);
            }
        }
        if (byBrand.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (Computer computer : byBrand) {
            this.comps.remove(computer.getNumber());
        }

    }

    @Override
    public void upgradeRam(int ram, int number) {
        Computer computer = this.comps.get(number);
        if (computer == null) {
            throw new IllegalArgumentException();
        }
        if (computer.getRAM() < ram) {
            computer.setRAM(ram);
        }
    }

    @Override
    public Iterable<Computer> getAllFromBrand(Brand brand) {
        return this.comps.values()
                .stream()
                .filter(c->c.getBrand().equals(brand))
                .sorted(Comparator.comparingDouble(Computer::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Computer> getAllWithScreenSize(double screenSize) {

        return this.comps.values()
                .stream()
                .filter(c->Double.compare(c.getScreenSize(), screenSize) == 0)
                .sorted(Comparator.comparingInt(Computer::getNumber))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Computer> getAllWithColor(String color) {
        return this.comps.values()
                .stream()
                .filter(c->c.getColor().equals(color))
                .sorted(Comparator.comparingDouble(Computer::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Computer> getInRangePrice(double minPrice, double maxPrice) {
        return this.comps.values()
                .stream()
                .filter(c->c.getPrice() >= minPrice && c.getPrice() <= maxPrice)
                .sorted(Comparator.comparingDouble(Computer::getPrice))
                .collect(Collectors.toList());
    }
}
