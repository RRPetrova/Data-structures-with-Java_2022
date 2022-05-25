import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Integer> wordByCount = new HashMap<>();
    private static Map<Integer, TreeSet<String>> tree = new HashMap<>();
    private static String target;
    private static List<String> buff = new ArrayList<>();
    private static List<String> outRes = new ArrayList<>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> cmd = Arrays.stream(scanner.nextLine().split(", ")).collect(Collectors.toList());
        target = scanner.nextLine();

        cmd = cmd.stream().filter(s -> target.contains(s)).collect(Collectors.toList());

        for (String s : cmd) {
            wordByCount.putIfAbsent(s, 0);
            wordByCount.put(s, wordByCount.get(s) + 1);

            int index = target.indexOf(s);
            while (index != -1) {
                if (!tree.containsKey(index)) {
                    tree.put(index, new TreeSet<>());
                }
                tree.get(index).add(s);
                index = target.indexOf(s, index + 1);
            }
        }

        dfsTraversal(0);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < outRes.size(); i++) {
            sb.append(outRes.get(i)).append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }

    private static void dfsTraversal(int index) {
        if (index >= target.length()) {
            String result = String.join("", buff);
            if (result.equals(target)) {
                outRes.add(String.join(" ", buff));
            }
        } else {
            if (!tree.containsKey(index)) {
                return;
            }
            for (String str : tree.get(index)) {
                if (wordByCount.get(str) > 0) {
                    buff.add(str);
                    wordByCount.put(str, wordByCount.get(str) - 1);
                    dfsTraversal(index + str.length());
                    wordByCount.put(str, wordByCount.get(str) + 1);
                    buff.remove(buff.size() - 1);
                }
            }
        }
    }
}
