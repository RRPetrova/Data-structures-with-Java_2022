import core.RePlayer;
import core.RePlayerImpl;
import models.Track;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RePlayer rePlayer = new RePlayerImpl();

        Track track = new Track("asd", "bsd", "csd", 4000, 400);
        Track track2 = new Track("dsd", "esd", "fsd", 5000, 400);
        Track track7 = new Track("dsd", "esd", "fsd", 5000, 400);
        Track track3 = new Track("hsd", "isd", "jsd", 5000, 500);
        Track track4 = new Track("ksd", "lsd", "msd", 5000, 600);
        Track track5 = new Track("nsd", "osd", "psd", 6000, 100);
        Track track6 = new Track("nsd2", "osd2", "psd2", 65000, 109);

        rePlayer.addTrack(track, "randomAlbum");
        rePlayer.addTrack(track2, "bandomAlbum");
        rePlayer.addTrack(track3, "aandomAlbum2");
        rePlayer.addTrack(track4, "aandomAlbum2");
        rePlayer.addTrack(track5, "aandomAlbum2");
        rePlayer.addTrack(track7, "zandomAlbum");

        //  Track asdsad = rePlayer.getTrack("esd","bandomAlbum");sdsadsadasdaas = {ArrayList@831}  size = 5
        Iterable<Track> ord1 = rePlayer.getTracksOrderedByAlbumNameThenByPlaysDescendingThenByDurationDescending();
       Map<String, List<Track>> fsd = rePlayer.getDiscography("fsd");
        rePlayer.addToQueue("lsd", "aandomAlbum2");
        rePlayer.addToQueue("osd", "aandomAlbum2");
        rePlayer.addToQueue("esd", "bandomAlbum");
        int c = track4.getPlays();
        rePlayer.play();
        c = track4.getPlays();
        rePlayer.play();
        System.out.println();
    }
}
