package ee.test.random;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Program {

    static Random foo = new Random();

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int[] a = {1,2,3,4,5};
        int[] b = Arrays.copyOf(a, a.length);
        printArray(a);
        printArray(ShuffleArray(a));
        printArray(shuffle(a));
        printArray(b);
        
        for(int i = 0; i< 10; i++){
//            System.out.println(getRandomNumberFrom(1, 2));
            System.out.println(getRandomNumber(5));
        }
        
        
    }

    public static int getRandomNumberFrom(int min, int max) {
        int randomNumber = foo.nextInt((max + 1) - min) + min;

        return randomNumber;

    }

    public static int getRandomNumber(int range) {
        return foo.nextInt(range);
    }

    private static int[] ShuffleArray(int[] array) {
        int index;
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
        return array;
    }

    private static int[] shuffle(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
        return array;
    }

    private static void printArray(int[] array) {
        for (int a : array) {
            System.out.print(" " + a);
        }
        System.out.println("");
    }
}
