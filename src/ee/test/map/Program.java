package ee.test.map;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private Map<String, Long> errorMap = new HashMap<String, Long>();
    public static void main(String[] args) {
        Program p = new Program();

        p.errorMap.put("test", 132L);
        p.errorMap.put("test", 13456L);
        long lastWriteDate = p.errorMap.get("test");

        p.errorMap.put("test11", 134156L);
        
        System.out.println(lastWriteDate);
        System.out.println(p.errorMap);
    }

}
