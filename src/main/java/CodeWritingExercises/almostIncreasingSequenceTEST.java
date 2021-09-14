package CodeWritingExercises;

public class almostIncreasingSequenceTEST {
    public static void main(String[] args) {
        int[] integers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10};
        System.out.println(almostIncreasingSequence(integers));
    }

    static boolean almostIncreasingSequence(int[] sequence) {
        boolean sequenceBroken = true;
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                if (sequenceBroken) {
                    if (i != 0 && i != sequence.length - 2) {
                        if (sequence[i + 1] <= sequence[i - 1]) {
                            if (sequence[i + 2] <= sequence[i]) {
                                return false;
                            }
                            i++;
                        }
                    }
                    sequenceBroken = false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}