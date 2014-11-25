package ee.test.iterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Program {
    static Set<String> strings = new HashSet<String>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");
        strings.add("f");
        System.out.println(strings);

        Set<String> stringsCopy = new HashSet<String>(strings);
        for (Iterator<String> iter = stringsCopy.iterator(); iter.hasNext();) {
            final String player = iter.next();
            String element = "b";
            if (player.equals(element)) {
                iter.remove();
            }
        }

        System.out.println(strings);

        strings.clear();

        System.out.println(strings);

        strings.remove("b");
    }

    private static void removeFromSet(String element) {
        strings.remove(element);
    }

}
