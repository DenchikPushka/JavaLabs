package lab.threads;

public class Main {
    public static void main(String[] args) {
        int[][] result, a, b;
        long start, finish;

        a = Matrices.generateMatrix(1000,1000);
        b = Matrices.generateMatrix(1000,1000);

        //Matrices.printMatrix(a, 2);
        System.out.println("-------------");
        //Matrices.printMatrix(b, 2);
        System.out.println("-------------");

        start = System.currentTimeMillis();
        result = new Matrices(a, b).multiply();
        finish = System.currentTimeMillis();
        //Matrices.printMatrix(result, 5);
        System.out.println("-------------");
        System.out.println("time multiply: "+(finish - start));
        System.out.println("-------------");


        start = System.currentTimeMillis();
        result = new Matrices(a, b).multiply2Threads();
        finish = System.currentTimeMillis();
        //Matrices.printMatrix(result, 5);
        System.out.println("-------------");
        System.out.println("time multiply2Threads: "+(finish - start));
        System.out.println("-------------");
    }
}
