import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoyaleArenaTest {

    @Test
    public void add() {
        RoyaleArena ra = new RoyaleArena();
        Battlecard b1 = new Battlecard(1, CardType.RANGED, "name", 20.5, 5.5);
        Battlecard b2 = new Battlecard(2, CardType.RANGED, "name", 20.5, 5.5);
        ra.add(b1);
        ra.add(b2);
        assertEquals(2, ra.count());
    }

    @Test
    public void contains() {

        RoyaleArena ra = new RoyaleArena();
        Battlecard b1 = new Battlecard(1, CardType.RANGED, "name", 20.5, 5.5);
        Battlecard b2 = new Battlecard(2, CardType.RANGED, "name", 20.5, 5.5);
        ra.add(b1);
       assertTrue(ra.contains(b1));
        assertFalse(ra.contains(b2));
    }
}