package implementations;

import org.junit.Test;

public class ReversedListTest {
    @Test
    public void test1() {
        ReversedList<Integer> reversedList = new ReversedList<>();
        reversedList.add(12);
        reversedList.add(15);
        reversedList.add(88);
        System.out.println(reversedList.get(0));
        System.out.println(reversedList.get(1));
        System.out.println("after remove");
        reversedList.removeAt(0);
        System.out.println(reversedList.get(0));
        System.out.println(reversedList.get(1));
        //Assert.assertEquals(15, (int) reversedList.get(0));
        // null 88 15 12
        //remove 1 => remove 12
        //remove 0 test => 15 12


    }
}