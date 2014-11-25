package ee.test.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        latch.countDown();
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("finished");
    }

}
