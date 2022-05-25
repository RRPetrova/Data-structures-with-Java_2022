package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loader implements Buffer {

    private List<Entity> entityList;
    private int size;

    public Loader() {
        this.entityList = new ArrayList<>();
        this.size = 0;
    }


    @Override
    public void add(Entity entity) {
        this.entityList.add(entity);

    }

    @Override
    public Entity extract(int id) {
        Entity entity = null;
//        Entity entity = this.entityList
//                .stream()
//                .filter(t -> t.getId() == id)
//                .findFirst()
//                .orElse(null);

        for (Entity currEntity : this.entityList) {
            if (currEntity.getId() == id) {
                entity = currEntity;
                this.entityList.remove(entity);
                break;
            }
        }

        return entity;
    }

    private boolean indexIsOutOfBounds(int index) {
        if (index < 0 || index >= this.entityList.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Entity find(Entity entity) {
        int index = this.entityList.indexOf(entity);

        return index == -1 ? null : this.entityList.get(index);
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entityList.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.entityList.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        int indToReplace = this.entityList.indexOf(oldEntity);

        if (indToReplace == -1) {
            throw new IllegalStateException("Not present " + oldEntity);
        }
        this.entityList.set(indToReplace, newEntity);
    }


    @Override
    public void swap(Entity first, Entity second) {
        int indexFirstEntity = this.entityList.indexOf(first);
        if (indexFirstEntity == -1) {
            throw new IllegalStateException("Entities not found");
        }

        int indexSecondEntity = this.entityList.indexOf(second);
        if (indexSecondEntity == -1) {
            throw new IllegalStateException("Entities not found");
        }

        // 10 11 12 13 -> swap 0 1 => 11 10 12 13
        this.entityList.set(indexFirstEntity, second);
        this.entityList.set(indexSecondEntity, first);

    }

    @Override
    public void clear() {
        this.entityList.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] arr = new Entity[this.entityList.size()];
//
//        for (int i = 0; i < this.entityList.size(); i++) {
//            arr[i] = this.entityList.get(i);
//        }
        this.entityList.toArray(arr);
        return arr;
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {

        return this.entityList
                .stream()
                .filter(currEntity -> currEntity.getStatus().ordinal() >= lowerBound.ordinal() &&
                        currEntity.getStatus().ordinal() <= upperBound.ordinal())
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entityList);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        List<Entity> entitiesWithGivenStatus = this.entityList
                .stream()
                .filter(e -> e.getStatus().equals(oldStatus))
                .collect(Collectors.toList());

        entitiesWithGivenStatus
                .forEach(e -> e.setStatus(newStatus));
    }

    @Override
    public void removeSold() {
        this.entityList = this.entityList
                .stream()
                .filter(e -> !e.getStatus().equals(BaseEntity.Status.SOLD))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.entityList.iterator();
    }
}
