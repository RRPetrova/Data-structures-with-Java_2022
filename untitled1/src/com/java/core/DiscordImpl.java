package com.java.core;

import com.java.models.Message;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordImpl implements Discord {

    private Map<String, Message> messagesById;
    private Map<String, List<Message>> messagesByChannel;
private List<Message> messagesList;

    public DiscordImpl() {
        this.messagesById = new HashMap<>();
        this.messagesByChannel = new HashMap<>();
        this.messagesList = new ArrayList<>();
    }

    @Override
    public void sendMessage(Message message) {
        this.messagesById.put(message.getId(), message);
        this.messagesList.add(message);
        List<Message> m = this.messagesByChannel.get(message.getChannel());
        if (!this.messagesByChannel.containsKey(message.getChannel())) {
           m= new ArrayList<>();
        }
        m.add(message);
        this.messagesByChannel.put(message.getChannel(),m);
    }

    @Override
    public boolean contains(Message message) {
        return this.messagesById.containsKey(message.getId());
    }

    @Override
    public int size() {
        return this.messagesById.size();
    }

    @Override
    public Message getMessage(String messageId) {
        if (!this.messagesById.containsKey(messageId)) {
            throw new IllegalArgumentException();
        }
        return this.messagesById.get(messageId);
    }

    @Override
    public void deleteMessage(String messageId) {
        Message message = this.messagesById.get(messageId);
        if (message == null) {
            throw new IllegalArgumentException();
        }
        this.messagesByChannel.get(message.getChannel()).remove(message);
        this.messagesById.remove(messageId);
        this.messagesList.remove(message);
    }

    @Override
    public void reactToMessage(String messageId, String reaction) {
        if (!this.messagesById.containsKey(messageId)) {
            throw new IllegalArgumentException();
        }
        this.messagesById.get(messageId).getReactions().add(reaction);
    }

    @Override
    public Iterable<Message> getChannelMessages(String channel) {
        if (!this.messagesByChannel.containsKey(channel)) {
            throw new IllegalArgumentException();
        }

        List<Message> res = this.messagesByChannel.get(channel);
        if (res.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return res;
    }

    @Override
    public Iterable<Message> getMessagesByReactions(List<String> reactions) {
        List<Message> res = new ArrayList<>();
        for (Message value : this.messagesById.values()) {
            int count = 0;

            for (String reaction : reactions) {
                if (value.getReactions().contains(reaction)) {
                    count++;
                }
            }
            if (count == reactions.size()) {
                res.add(value);
            }
        }

        return res.
                stream()
                .sorted((a, b) ->
                {
                    int cmp = b.getReactions().size() - a.getReactions().size();
                    if (cmp == 0) {
                        return a.getTimestamp() - b.getTimestamp();
                    }
                    return cmp;
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getMessageInTimeRange(Integer lowerBound, Integer upperBound) {
        List<Message> filtered = this.messagesById
                .values()
                .stream()
                .filter(a -> a.getTimestamp() >= lowerBound && a.getTimestamp() <= upperBound)
                .collect(Collectors.toList());


        Map<String, List<Message>> messagesByChannel = new HashMap<>();

        for (Message message : filtered) {
            messagesByChannel.computeIfAbsent(message.getChannel(), k -> new ArrayList<>()).add(message);
        }
        List<List<Message>> collect = messagesByChannel.values().stream().sorted((a, b) -> b.size() - a.size()).collect(Collectors.toList());
        List<Message> res = new ArrayList<>();
        for (List<Message> messages : collect) {
            res.addAll(messages);
        }
        return res;

    }

    @Override
    public Iterable<Message> getTop3MostReactedMessages() {
        return this.messagesById
                .values()
                .stream()
                .sorted((a, b) -> b.getReactions().size() - a.getReactions().size())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getAllMessagesOrderedByCountOfReactionsThenByTimestampThenByLengthOfContent() {
        return this.messagesById
                .values()
                .stream()
                .sorted((a, b) -> {
                    int cmp = b.getReactions().size() - a.getReactions().size();
                    if (cmp == 0) {
                        cmp = a.getTimestamp() - b.getTimestamp();
                        if (cmp == 0) {
                            return a.getContent().length() - b.getContent().length();
                        }
                    }
                    return cmp;
                }).collect(Collectors.toList());

    }
}
