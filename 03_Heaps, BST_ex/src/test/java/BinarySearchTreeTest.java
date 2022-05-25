import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BinarySearchTreeTest extends TestCase {
    private BinarySearchTree<Integer> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(1);
        bst.insert(17);
    }


    @Test
    public void testRoot() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }

    @Test
    public void testInsert() {
        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());
    }

    @Test
    public void testEachOrder() {
        List<Integer> elements = new ArrayList<>();
        bst.eachInOrder(elements::add);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 3, 5, 6, 7, 17));

        assertEquals(expected.size(), elements.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), elements.get(i));
        }
    }

    @Test
    public void testContains() {
        assertTrue(bst.contains(7));
    }

    @Test
    public void testDoesntContains() {
        assertFalse(bst.contains(88));
    }

    @Test
    public void testSearch() {
        BinarySearchTree<Integer> search = bst.search(7);
        assertEquals(Integer.valueOf(5), search.getRoot().getValue());
        assertEquals(Integer.valueOf(6), search.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(17), search.getRoot().getRight().getValue());
    }

    @Test
    public void testSearchNotPresent() {

        assertNull(bst.search(88));
    }


    @Test
    public void testRabge() {
        List<Integer> elements = bst.range(3, 7);
        List<Integer> expected = Arrays.asList(3, 5, 7, 6);
        assertEquals(4, elements.size());

        for (Integer element : elements) {
            assertTrue(expected.contains(element));
        }
    }


    @Test
    public void testDelMin() {
        assertTrue(bst.contains(1));
        bst.deleteMin();
        assertFalse(bst.contains(1));
    }


    @Test
    public void testDelMax() {
        assertTrue(bst.contains(17));
        bst.deleteMax();
        assertFalse(bst.contains(17));
    }

    @Test
    public void testCount() {
        assertEquals(6, bst.count());
    }

    @Test
    public void testCount2() {
        bst.insert(11);
        assertEquals(7, bst.count());
    }

    @Test
    public void testCount3() {
        bst.deleteMin();
        assertEquals(5, bst.count());
    }

    @Test
    public void testCount4() {
        bst.deleteMax();
        assertEquals(5, bst.count());
    }

    @Test
    public void testRank() {
        assertEquals(4, bst.rank(7));
    }

    @Test
    public void testRank2() {
        assertEquals(0, bst.rank(-1));
    }

    @Test
    public void testFloor() {
        assertEquals(Integer.valueOf(6), bst.floor(7));
    }

    @Test
    public void testFloor2() {
        assertNull(bst.floor(-1));
    }
}

























