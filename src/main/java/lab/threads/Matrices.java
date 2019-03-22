package lab.threads;

import java.util.ArrayList;
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

    public int[][] multiplyThreads(int threadsCount) {
        int height = a.length,
            width = b[0].length,
            passageLength,
            beginHeight = 0,
            endHeight,
            step;
        int[][] a = this.a, b = this.b, result = new int[height][width];
        ArrayList<HelperThread> hThreadsList = new ArrayList<>();
        HelperThread hThread;

        if (a[0].length != b.length) {
            return null;
        }
        if (threadsCount == 0) {
            return result;
        }
        passageLength = b.length;

        step = height / threadsCount;
        endHeight = beginHeight + step;
        while (endHeight <= height - step) {
            System.out.println(beginHeight+" - "+endHeight);
            hThread = new HelperThread(beginHeight, endHeight, passageLength, width, a, b, result);
            hThreadsList.add(hThread);
            hThread.start();
            beginHeight = endHeight;
            endHeight = beginHeight + step;
        }

        System.out.println("count hThreads: "+hThreadsList.size());

        for (int i = beginHeight; i < height; i++) {
            for (int j = width; j-- > 0;) {
                for (int k = passageLength; k-- > 0;) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        for (HelperThread item : hThreadsList) {
            try {
                item.join();
            } catch(InterruptedException e){
                System.out.println(e.toString());
            }
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

    class HelperThread extends Thread {
        private int beginRow, endRow, passageLength, width;
        private int[][] a, b, result;

        public HelperThread(int beginRow, int endRow, int passageLength, int width, int[][] a, int[][] b, int[][] result) {
            this.beginRow = beginRow;
            this.endRow = endRow;
            this.passageLength = passageLength;
            this.width = width;
            this.a = a;
            this.b = b;
            this.result = result;
        }

        @Override
        public void run() {
            for (int i = beginRow; i < endRow; i++) {
                for (int j = width; j-- > 0;) {
                    for (int k = passageLength; k-- > 0;) {
                        result[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
    }

}
