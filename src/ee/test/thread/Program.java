package ee.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * return nothing if time is reached otherwise return value.  
 */
public class Program {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        MyCallable myCallable = new MyCallable();
        Future<String> futureResult = service.submit(myCallable);
        String result = null;
        try{
            result = futureResult.get(1000, TimeUnit.MILLISECONDS);
        }catch(TimeoutException e){
            System.out.println("No response after one second");
            futureResult.cancel(false);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        service.shutdown();
        System.out.println("result: " + result);
    }

    private static final class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            StringBuilder builder = new StringBuilder();
            try{
                for(int i=0;i<10;++i){
                    builder.append(i+" ");
                    Thread.sleep(100);
                }
            }catch(InterruptedException e){
                System.out.println("Thread was interrupted");
            }
            return builder.toString();
        }
    }
}
