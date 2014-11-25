package ee.test.treemap;

public class TestObject {

    private int test;

    public TestObject(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestObject other = (TestObject) obj;
        if (test != other.test)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TestObject [test=");
        builder.append(test);
        builder.append("]");
        return builder.toString();
    }

}
