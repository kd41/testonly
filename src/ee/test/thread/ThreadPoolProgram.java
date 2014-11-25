package ee.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProgram {
    private static final int MAX_WORKERS = 10;
    private static final int TIMEOUT = 1;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    private final ExecutorService service;

    public ThreadPoolProgram() {
        service = Executors.newFixedThreadPool(MAX_WORKERS);
    }

    private String getResponse(String request) throws Exception {
        WebapiCall call = new WebapiCall(request);
        Future<String> result = service.submit(call);
        try {
            return result.get(TIMEOUT, TIMEOUT_UNIT);
        } catch (Exception e) {
            System.out.println("some error" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        final ThreadPoolProgram program = new ThreadPoolProgram();
        for (int i = 0; i < 10; i++) {
            final int iii = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        String response = program.getResponse("request_" + iii);
                        System.out.println(Thread.currentThread().getName() + response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.setName("name " + i);
            thread.start();
        }
    }

    private class WebapiCall implements Callable<String> {
        private String request;

        public WebapiCall(String request) {
            this.request = request;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " some response for request=" + request;
        }
    }
}
