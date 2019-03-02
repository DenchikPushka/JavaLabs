package lab.threads;

public class Main {
    public static void main(String[] args) {
        int[][] result, a, b;

        a = Matrices.generateMatrix(5,3);
        b = Matrices.generateMatrix(3,4);

        Matrices.printMatrix(a, 2);
        System.out.println("-------------");
        Matrices.printMatrix(b, 2);
        System.out.println("-------------");

        result = new Matrices(a, b).multiply();

        Matrices.printMatrix(result, 5);
    }
}
