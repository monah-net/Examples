package CodeWritingExercises;

import java.util.ArrayList;
import java.util.List;

public class MatrixElementSum {
    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 2, 3}, {1, 0, 3, 3}, {2, 2, 2, 2}, {3, 3, 3, 3}};
        System.out.println(matrixElementsSum(matrix));
    }

    private static int matrixElementsSum(int[][] matrix) {
        int sum = 0;
        ArrayList<Integer> hauntedfloors = new ArrayList();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == 0) {
                    if(matrix[i][j] == 0){
                        hauntedfloors.add(j);
                    }
                    sum += matrix[i][j];
                } else if (!hauntedfloors.contains(j)) {
                    sum+=matrix[i][j];
                    if(matrix[i][j] == 0){
                        hauntedfloors.add(j);
                    }
                }
            }
        }
        return sum;
    }
}