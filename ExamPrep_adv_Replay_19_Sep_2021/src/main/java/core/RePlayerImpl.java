package core;

import models.Track;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RePlayerImpl implements RePlayer {

    private Map<String, List<Track>> albumsWithListOfTracks;
    private Set<Track> tracks;
    private Map<String, Map<String, Track>> albumsWithTrackTitle;
    private Map<String, Map<String, List<Track>>> artistsWithTracksByAlbumName; //artist - album - track list
    private Map<String, LinkedList<String>> artistNameWithListOfTrackIds;
    private Queue<Track> listeningQueue;

    public RePlayerImpl() {
        this.albumsWithListOfTracks = new TreeMap<>();
        this.tracks = new HashSet<>();
        this.albumsWithTrackTitle = new HashMap<>();
        this.artistsWithTracksByAlbumName = new HashMap<>();
        this.artistNameWithListOfTrackIds = new HashMap<>();

        this.listeningQueue = new ArrayDeque<>();
    }

    @Override
    public void addTrack(Track track, String album) {

          this.albumsWithListOfTracks
                  .computeIfAbsent(album, k -> new ArrayList<>())
                  .add(track);

        this.tracks.add(track);
        this.albumsWithTrackTitle
                .computeIfAbsent(album, k -> new HashMap<>())
                .put(track.getTitle(), track);
        //map.computeIfAbsent(ppName, k -> new HashMap<>()).computeIfAbsent(ppVariant, b -> new ArrayList<>()).add(object);
        this.artistsWithTracksByAlbumName
                .computeIfAbsent(track.getArtist(), k -> new HashMap<>())
                .computeIfAbsent(album, k -> new ArrayList<>())
                .add(track);
        this.artistNameWithListOfTrackIds
                .computeIfAbsent(track.getArtist(), k -> new LinkedList<>())
                .add(track.getId());
    }

    @Override
    public boolean contains(Track track) {
        return this.tracks.contains(track);
    }

    @Override
    public int size() {
        return tracks.size();
    }

    @Override
    public Track getTrack(String title, String albumName) {

        if (!this.albumsWithTrackTitle.containsKey(albumName)) {
            throw new IllegalArgumentException();
        }

        return this.albumsWithTrackTitle
                .get(albumName)
                .get(title);
    }

    @Override
    public Iterable<Track> getAlbum(String albumName) {

        List<Track> res = this.albumsWithListOfTracks.get(albumName)
                .stream()
                .sorted(Comparator.comparingInt(Track::getPlays).reversed())
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return res;
    }

    @Override
    public void addToQueue(String trackName, String albumName) {
        Track track = this.getTrack(trackName, albumName);
        this.listeningQueue.add(track);
    }

    @Override
    public Track play() {
        if (this.listeningQueue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Track removed = this.listeningQueue.remove();
        if (removed == null) {
            throw new IllegalArgumentException();
        }
        removed.setPlays(removed.getPlays() + 1);
        return removed;
    }

    @Override
    public void removeTrack(String trackTitle, String albumName) {
        if (!this.albumsWithListOfTracks.containsKey(albumName) ||
                !this.albumsWithTrackTitle.get(albumName).containsKey(trackTitle)) {
            throw new IllegalArgumentException();
        }
        Track track = this.getTrack(trackTitle, albumName);

        this.albumsWithTrackTitle.get(albumName).remove(trackTitle);
        this.tracks.remove(track);
        this.albumsWithListOfTracks.get(albumName).remove(track);
        this.listeningQueue.remove(track);
        this.artistNameWithListOfTrackIds.remove(track.getId());
    }

    @Override
    public Iterable<Track> getTracksInDurationRangeOrderedByDurationThenByPlaysDescending(int lowerBound, int upperBound) {
        return this.tracks.stream()
                .filter(a -> a.getDurationInSeconds() >= lowerBound && a.getDurationInSeconds() <= upperBound)
                .sorted((a, b) -> {
                    int cmp = a.getDurationInSeconds() - b.getDurationInSeconds();
                    if (cmp == 0) {
                        return b.getPlays() - a.getPlays();
                    }
                    return cmp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Track> getTracksOrderedByAlbumNameThenByPlaysDescendingThenByDurationDescending() {

        List<Track> res = new ArrayList<>();
        this.albumsWithListOfTracks.entrySet()
                .stream()
            //    .sorted(Entry.comparingByKey())
                .forEach(entry -> {
                    List<Track> collect = entry.getValue()
                            .stream()
                            .sorted((a, b) -> {
                                int cmp = Integer.compare(b.getPlays() , a.getPlays());
                                if (cmp == 0) {
                                    return Integer.compare(b.getDurationInSeconds(), a.getDurationInSeconds());
                                }
                                return cmp;
                            }).collect(Collectors.toList());
                    res.addAll(collect);
                });


        return res;
    }

    @Override
    public Map<String, List<Track>> getDiscography(String artistName) {
        //  private Map<String, Map<String, List<Track>>> artistsWithTracksByAlbumName; //artist - album - track list
      /*  Map<String, List<Track>> res = new HashMap<>();
        List<Track> tracksWithArtist = null;

        for (Entry<String, List<Track>> entry : this.albumsWithListOfTracks.entrySet()) {
            for (Track track : entry.getValue()) {

                if (track.getArtist().equals(artistName)) {
                    if (!res.containsKey(entry.getKey())) {
                        tracksWithArtist = new ArrayList<>();
                    } else {
                        tracksWithArtist = res.get(entry.getKey());
                    }
                    tracksWithArtist.add(track);
                    res.put(entry.getKey(), tracksWithArtist);
                }
            }
        }
        if (tracksWithArtist == null) {
            throw new IllegalArgumentException();
        }
        return res;*/

        if (!this.artistsWithTracksByAlbumName.containsKey(artistName) || this.artistNameWithListOfTrackIds.get(artistName).isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<String, List<Track>> stringListMap = this.artistsWithTracksByAlbumName.get(artistName);
        if (stringListMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return stringListMap;
    }
}
