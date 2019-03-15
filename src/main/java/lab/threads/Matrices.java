package lab.threads;

import java.util.Random;

public class Matrices {

    private int[][] a, b;

    public Matrices(int[][] a, int[][] b) {
        this.a = a;
        this.b = b;
    }

    public int[][] multiply() {
        int height = a.length, width = b[0].length, passageLength;
        int[][] a = this.a, b = this.b, result = new int[height][width];
        if (a[0].length != b.length) {
            return null;
        }
        passageLength = b.length;
        for (int i = height; i-- > 0;) {
            for (int j = width; j-- > 0;) {
                for (int k = passageLength; k-- > 0;) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public int[][] multiply2Threads() {
        int height = a.length, width = b[0].length, passageLength, halfHeight;
        int[][] a = this.a, b = this.b, result = new int[height][width];

        if (a[0].length != b.length) {
            return null;
        }
        passageLength = b.length;
        halfHeight = height / 2;

        class HelperThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < halfHeight; i++) {
                    for (int j = width; j-- > 0;) {
                        for (int k = passageLength; k-- > 0;) {
                            result[i][j] += a[i][k] * b[k][j];
                        }
                    }
                }
            }
        }

        HelperThread hThread = new HelperThread();
        hThread.start();

        for (int i = height; i-- > halfHeight;) {
            for (int j = width; j-- > 0;) {
                for (int k = passageLength; k-- > 0;) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        try {
            hThread.join();
        } catch(InterruptedException e){
            System.out.println(e.toString());
        }

        return result;
    }

    public static int[][] generateMatrix(int h, int w) {
        if (h == 0 || w == 0) {
            return null;
        }
        int[][] result = new int[h][w];
        Random random = new Random();
        for (int i = h; i-- > 0;) {
            for (int j = w; j-- > 0;) {
                result[i][j] = random.nextInt(100);
            }
        }
        return result;
    }

    public static void printMatrix(int[][] a, int l) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(String.format("%0"+l+"d", a[i][j])+" ");
            }
            System.out.println();
        }
    }

}
