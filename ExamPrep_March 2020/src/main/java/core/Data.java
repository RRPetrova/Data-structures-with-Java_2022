package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class Data implements Repository {

    private Entity root;
    private Queue<Entity> entityQueue;


    public Data() {
        this.entityQueue = new PriorityQueue<>();//(f,s) -> s.getId()-f.getId());
        this.root = null;
    }

    public Data(Data other) {
        this.root = other.root;
        this.entityQueue = new PriorityQueue<>(other.entityQueue);
    }


    @Override
    public void add(Entity entity) {
        if (this.root == null) {
            this.root = entity;
        } else {
            Entity parent = this.getById(entity.getParentId());
            parent.addChild(entity);
        }
        this.entityQueue.offer(entity);

    }

    @Override
    public Entity getById(int id) {
//        return this.entityQueue
//                .stream()
//                .filter(a -> a.getId() == id)
//                .findFirst()
//                .orElse(null);


        if (this.root == null) {
            return null;
        }

        Deque<Entity> queue = new ArrayDeque<>();

        queue.offer(this.root);
        while (!queue.isEmpty()) {
            Entity current = queue.poll();
            if (current.getId() == id) {
                return current;
            }

            for (Entity child : current.getChildren()) {

                queue.offer(child);
            }

        }
        return null;

    }

    @Override
    public List<Entity> getByParentId(int id) {
        Entity parent = this.getById(id);

        return parent == null ? new ArrayList<>() : parent.getChildren();
//        return this.entityQueue
//                .stream()
//                .filter(e -> e.getParentId() == id)
//                .collect(Collectors.toList());


    }

    @Override
    public List<Entity> getAll() {
//        List<Entity> result = new ArrayList<>();
//
//        Deque<Entity> queue = new ArrayDeque<>();
//        queue.offer(this.root);
//        while (!queue.isEmpty()) {
//            Entity current = queue.poll();
//            result.add(current);
//            for (Entity child : current.getChildren()) {
//                queue.offer(child);
//            }
//        }
//        return result;
        return new ArrayList<>(this.entityQueue);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {

        if (!type.equals("Invoice") && !type.equals("StoreClient") && !type.equals("User") ) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }

        return this.entityQueue
                .stream()
                .filter(a -> a.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());

    }

    @Override
    public Entity peekMostRecent() {
        if (size() == 0) {
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.entityQueue
                .stream().max(Comparator.comparing(Entity::getId))
                .get();
    }

    @Override
    public Entity pollMostRecent() {
        if (size() == 0) {
            throw new IllegalStateException("Operation on empty Data");
        }

        Entity entity = this.entityQueue
                .stream().max(Comparator.comparing(Entity::getId))
                .get();
        this.entityQueue.remove(entity);
        return entity;
    }

    @Override
    public int size() {
        return this.entityQueue.size();
    }
}
