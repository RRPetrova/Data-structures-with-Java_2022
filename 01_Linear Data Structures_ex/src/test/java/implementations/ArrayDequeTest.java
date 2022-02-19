package implementations;

import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void test1() {
        ArrayDeque<Integer> deq = new ArrayDeque<>();
        deq.offer(13);
        deq.offer(14);
        deq.offer(15);
        deq.offer(16);

      deq.insert(3, 88);
    }

}