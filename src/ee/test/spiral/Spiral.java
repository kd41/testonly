package ee.test.spiral;

public class Spiral {

    private int[][] a;

    private enum Direction {
        Right, Left, Down, Up
    }

    public Spiral(int size) {
        this(size, size);
    }
    
    public Spiral(int i, int j) {
        a = new int[i][j];
    }

    public void fillSpiral() {
        Direction d = Direction.Right;
        int count = a.length * a[0].length;
        int i = 0;
        int j = 0;
        for (int k = 1; k <= count; k++) {
            a[i][j] = k;
            if (d == Direction.Right) {
                if (j + 1 < a[i].length && a[i][j + 1] == 0) {
                    j++;
                } else {
                    d = Direction.Down;
                    i++;
                }
            } else if (d == Direction.Down) {
                if (i + 1 < a.length && a[i + 1][j] == 0) {
                    i++;
                } else {
                    d = Direction.Left;
                    j--;
                }
            } else if (d == Direction.Left) {
                if (j != 0 && a[i][j - 1] == 0) {
                    j--;
                } else {
                    d = Direction.Up;
                    i--;
                }
            } else if (d == Direction.Up) {
                if (i != 0 && a[i - 1][j] == 0) {
                    i--;
                } else {
                    d = Direction.Right;
                    j++;
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int size = 10;
        Spiral s = new Spiral(size);
//        s.fillSpiral();
//        s.print();
        
        int i = 3;
        int j = 22;
        s = new Spiral(i ,j);
        s.fillSpiral();
        s.print();
        
    }

    public void print() {
        for (int k = 0; k < a.length; k++) {
            for (int l = 0; l < a[0].length; l++) {
                System.out.print(a[k][l] + "\t");
            }
            System.out.println("");
        }
    }

}
