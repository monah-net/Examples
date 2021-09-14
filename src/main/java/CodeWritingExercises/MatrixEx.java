package CodeWritingExercises;

public class MatrixEx {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 0,9}, {4, 0, 3,9}, {7, 8, 9,12}};
        System.out.println(matrix.length);
        System.out.println(matrix[0].length);
        System.out.println("=============All elements=============");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.println(matrix[i][j]);
            }
        }
    }
}
