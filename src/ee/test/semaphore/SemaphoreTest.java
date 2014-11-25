package ee.test.semaphore;

import java.util.concurrent.Semaphore;
import static java.lang.System.*;

public class SemaphoreTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Semaphore semaphore  = new Semaphore(1);

        semaphore.release();
        out.println(semaphore.tryAcquire());
        out.println(semaphore.tryAcquire());
        out.println(semaphore.tryAcquire());

        

        
        
//        new Thread()
//        {
//            public void run() {
//                out.println(semaphore.tryAcquire());
//                System.out.println("blah1");
//            }
//        }.start();
//        
//        new Thread()
//        {
//            public void run() {
//                out.println(semaphore.tryAcquire());
//                System.out.println("blah2");
//            }
//        }.start();

//        semaphore.release();
//        semaphore.release();
//        semaphore.release();
//        semaphore.release();
//        semaphore.release();
    }

}
