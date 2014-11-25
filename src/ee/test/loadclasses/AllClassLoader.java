package ee.test.loadclasses;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Vector;

/**
 * Print all loaded classes by current classloader.
 */
public class AllClassLoader {
    public static void main(String[] args) throws Exception {
        AllClassLoader acl = new AllClassLoader();
        acl.printAllClasses();
    }

    private void printAllClasses() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        Class<?> c = this.getClass().getClassLoader().getClass();
        while (c != java.lang.ClassLoader.class) {
            c = c.getSuperclass();
        }
        Field f = c.getDeclaredField("classes");
        f.setAccessible(true);
        Vector<?> classes = (Vector<?>) f.get(this.getClass().getClassLoader());
        Iterator<?> it = classes.iterator();
        while (it.hasNext()) {
            Class<?> cl = (Class<?>) it.next();
            System.out.println("Loaded Class : " + cl);
        }
    }

}
