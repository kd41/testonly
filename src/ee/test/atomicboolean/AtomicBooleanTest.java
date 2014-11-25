package ee.test.atomicboolean;

import java.util.concurrent.atomic.AtomicBoolean;
import static java.lang.System.*;

public class AtomicBooleanTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        AtomicBoolean b = new AtomicBoolean(true);
        out.println(b.get());
        
        b.set(false);
        out.println(b.get());
        
        b.set(true);
        out.println(b.get());
        

    }

}
