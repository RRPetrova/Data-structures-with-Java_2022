import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RoyaleArena implements IArena {

    private Map<Integer, Battlecard> bcards;
    private Map<CardType, Set<Battlecard>> cardsByTypes;

    public RoyaleArena() {
        this.bcards = new LinkedHashMap<>();
        this.cardsByTypes = new LinkedHashMap<>();
    }

    @Override
    public void add(Battlecard card) {
        this.bcards.putIfAbsent(card.getId(), card);
        this.cardsByTypes.putIfAbsent(card.getType(), new TreeSet<>(Battlecard::compareTo));
        this.cardsByTypes.get(card.getType()).add(card);
    }

    @Override
    public boolean contains(Battlecard card) {
        return this.bcards.containsKey(card.getId());
    }

    @Override
    public int count() {
        return this.bcards.size();
    }

    @Override
    public void changeCardType(int id, CardType type) {
        Battlecard battlecard = this.bcards.get(id);
        if (battlecard == null) {
            throw new IllegalArgumentException();
        }

        battlecard.setType(type);

    }

    @Override
    public Battlecard getById(int id) {
        Battlecard battlecard = this.bcards.get(id);
        if (battlecard == null) {
            throw new UnsupportedOperationException();
        }
        return battlecard;
    }

    @Override
    public void removeById(int id) {
        Battlecard battlecard = this.bcards.remove(id);
        if (battlecard == null) {
            throw new UnsupportedOperationException();
        }

        this.cardsByTypes.get(battlecard.getType()).remove(battlecard);

    }

    @Override
    public Iterable<Battlecard> getByCardType(CardType type) {
        Set<Battlecard> battlecards = this.cardsByTypes.get(type);
        if (battlecards == null || battlecards.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return battlecards;
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        Set<Battlecard> battlecards = this.cardsByTypes.get(type);

        if (battlecards == null || battlecards.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        List<Battlecard> res = battlecards
                .stream()
                .filter(c -> c.getDamage() > lo && c.getDamage() < hi)
                .sorted(Battlecard::compareTo)
                .collect(Collectors.toList());
        // TODO: nqma proverka
        if (res.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return res;
    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        Set<Battlecard> battlecards = this.cardsByTypes.get(type);

        if (battlecards == null) {
            throw new UnsupportedOperationException();
        }

        List<Battlecard> res = battlecards
                .stream()
                .filter(c -> c.getDamage() <= damage)
                .sorted(Battlecard::compareTo)
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return res;
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {
        List<Battlecard> byName = new ArrayList<>();
        for (Battlecard card : this.bcards.values()) {
            if (card.getName().equals(name)) {
                byName.add(card);
            }
        }

        if (byName.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        byName
                .sort(Comparator.comparingDouble(Battlecard::getSwag).reversed()
                        .thenComparing(Battlecard::getId))
        ;
        return byName;
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        List<Battlecard> byName = new ArrayList<>();
        for (Battlecard card : this.bcards.values()) {
            if (card.getName().equals(name)) {
                byName.add(card);
            }
        }

        if (byName.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        List<Battlecard> res = byName
                .stream()
                .filter(c -> c.getSwag() >= lo && c.getSwag() < hi)
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        res.sort(Comparator.comparingDouble(Battlecard::getSwag).reversed()
                .thenComparing(Battlecard::getId));
        return res;

    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        Map<String, Battlecard> byName = new LinkedHashMap<>();

        for (Battlecard card : this.bcards.values()) {
            if (!byName.containsKey(card.getName())) {
                byName.put(card.getName(), card);
            } else {
                if (card.getSwag() > byName.get(card.getName()).getSwag()) {
                    byName.put(card.getName(), card);
                }
            }
        }
        return byName.values();
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {
        if (n > this.count()) {
            throw new UnsupportedOperationException();
        }

        List<Battlecard> collect = this.bcards
                .values()
                .stream()
                .sorted(Comparator.comparingDouble(Battlecard::getSwag))
                .collect(Collectors.toList());

        return collect
                .stream()
                .limit(n)
                .sorted(Comparator.comparingDouble(Battlecard::getSwag).thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {

        List<Battlecard> collect = this.bcards.values()
                .stream()
                .filter(c -> c.getSwag() >= lo && c.getSwag() <= hi)
                .collect(Collectors.toList());

        return collect
                .stream()
                .sorted(Comparator.comparingDouble(Battlecard::getSwag))
                .collect(Collectors.toList());

    }

    @Override
    public Iterator<Battlecard> iterator() {
        return this.bcards
                .values()

                .iterator();
    }
}
