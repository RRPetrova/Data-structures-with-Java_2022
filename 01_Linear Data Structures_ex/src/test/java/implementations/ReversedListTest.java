package implementations;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversedListTest {
    @Test
    public void test1() {
        ReversedList<Integer> reversedList = new ReversedList<>();
        reversedList.add(12);
        reversedList.add(15);
        reversedList.add(88);


        reversedList.removeAt(0);
        Assert.assertEquals(15, (int) reversedList.get(0));
        // null 88 15 12
        //remove 1 => remove 12


    }
}