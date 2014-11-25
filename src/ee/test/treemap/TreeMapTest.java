package ee.test.treemap;

import java.util.TreeMap;
import static java.lang.System.*;

public class TreeMapTest {

    private static final TreeMap<Integer, TestObject> treeMap = new TreeMap<Integer, TestObject>();
    public static void main(String[] args) {
        TestObject o1 = new TestObject(1);
        TestObject o2 = new TestObject(2);

        treeMap.put(1, o1);
        treeMap.put(1, o2);
        out.println(treeMap);
        
        out.println(treeMap.get(1));
    }

}
