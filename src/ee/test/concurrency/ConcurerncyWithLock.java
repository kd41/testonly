package ee.test.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurerncyWithLock {
    private static Map<String, List<String>> gameResults = new HashMap<String, List<String>>();
    private static Random random = new Random();
    private static Lock lock = new ReentrantLock();

    public static boolean add(String casinoName, String gameResult) throws Exception {
        lock.lock();
        try {
            if (!gameResults.containsKey(casinoName)) {
                List<String> results = new ArrayList<String>();
                results.add(gameResult);
                gameResults.put(casinoName, results);
                return true;
            } else {
                return gameResults.get(casinoName).add(gameResult);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        throw new Exception("Error on add!!!!!!!");
    }

    public static Map<String, List<String>> getAndClear() {
        Map<String, List<String>> results = new HashMap<String, List<String>>();
        lock.lock();
        try {
            if (!gameResults.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : gameResults.entrySet()) {
                    results.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
                }
                gameResults.clear();
            }
        } finally {
            lock.unlock();
        }
        return results;
    }

    public static boolean isEmpty() {
        return gameResults.isEmpty();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Started...");
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    try {
                        add("europa", "ttt" + random.nextInt(10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Done add-thread");
            }
        });
        addThread.setName("add-thread");
        Thread clearThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    getAndClear();
                }
                System.out.println("Done clear-thread");
            }
        });
        clearThread.setName("clear-thread");

        addThread.start();
        clearThread.start();
    }

}
