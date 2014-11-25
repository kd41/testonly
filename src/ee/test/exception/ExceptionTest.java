package ee.test.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTest {
    public static void main(String... args) throws Exception {
//        try {
//            int a = 2;
//            if (a - 2 != 0) {
//                System.out.println("if");
//            } else {
//                System.out.println("else");
//                throw new Exception("hui vsem");
//            }
//        } catch (Exception e) {
//            System.out.println("catch");
//            throw e;
//        } finally {
//            System.out.println("finally");
//        }
        
        StringWriter sw = new StringWriter();
        new Throwable().printStackTrace(new PrintWriter(sw));
        System.out.println("Stack trace is:\n\t" + sw.toString());
    }
}
