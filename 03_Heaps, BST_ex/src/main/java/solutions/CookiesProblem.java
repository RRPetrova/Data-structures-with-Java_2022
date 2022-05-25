package solutions;

import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {
    public Integer solve(int requiredSweetness, int[] cookiesSweetness) {

        Queue<Integer> cookies = new PriorityQueue<>();


        for (int sweetness : cookiesSweetness) {
            cookies.add(sweetness);
        }

        int currMinSweatness = cookies.peek();
        int steps = 0;
        while (currMinSweatness < requiredSweetness && cookies.size() > 1) {
            int leastSweet = cookies.poll();
            int secondLeastSweetCookie = cookies.poll();

            int combined = leastSweet + 2 * secondLeastSweetCookie;

            cookies.add(combined);
            currMinSweatness = cookies.peek();
            steps++;
        }
        return currMinSweatness > requiredSweetness ? steps : -1;


    }
}
