package ee.test.ooo;

import java.text.NumberFormat;
import java.util.Set;

public class OOOTest {

    private static final int MB = 1024 * 1024;

    /**
     * @param args
     */
    public static void main(String[] args) {
        int i = 0;
        Runtime runtime = Runtime.getRuntime();
        while (true) {
            i++;

            if (i % 1000 == 0) {
                printThreads();
            }

            StringBuilder sb = new StringBuilder();
            long maxMemory = runtime.maxMemory();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();

            sb.append("Free Memory:\t").append(freeMemory / MB).append("\n");
            sb.append("Total Memory:\t").append(totalMemory / MB).append("\n");
            sb.append("Max Memory:\t").append(maxMemory / MB).append("\n");
            sb.append("Used Memory:\t").append((totalMemory - freeMemory) / MB).append("\n");
            sb.append("Total threads count: " + Thread.getAllStackTraces().size() + "\n");

            System.out.println("I=" + i);
            System.out.println(sb.toString());

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                    }
                }
            }, "new-thread-" + i).start();
        }

    }

    private static void printThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Threads count=").append(threadSet.size()).append("\n");
        sb.append("Threads=[");
        int count = 0;
        for (Thread thread : threadSet) {
            count++;
            sb.append(count).append(":").append(thread.getName()).append(", ");
        }
        if (count > 0) {
            sb.deleteCharAt(sb.length() - 2);
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}
