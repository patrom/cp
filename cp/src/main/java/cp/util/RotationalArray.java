package cp.util;

import java.util.ArrayList;
import java.util.List;

public class RotationalArray {

    private int[][] rotationalArray;

    public RotationalArray(int[] pitchClasses){
//        List<Integer> set = new ArrayList<>(row);
//        setFirstPcToZero(set);
        int[] generatingSet = pitchClasses;
        this.rotationalArray = new int[pitchClasses.length][pitchClasses.length];
        for (int i = 0; i < pitchClasses.length; i++) {
            for (int j = 0; j < rotationalArray.length; j++){
                rotationalArray[i][j] = (pitchClasses[j] - generatingSet[i] + 12) % 12;
            }
            pitchClasses = Util.rotateArray(pitchClasses, 1);
        }
    }

    public void show() {
        for (int i = 0; i < rotationalArray[0].length; i++) {
            for (int j = 0; j < rotationalArray.length; j++){
                int pc = rotationalArray[i][j];
                System.out.print(pc + "," + "\t");
            }
            System.out.println();
        }
    }

    public int[] getRow(int n){
        int[] row = new int[rotationalArray.length];
        for (int i = 0; i < rotationalArray[0].length; i++) {
            row[i] = rotationalArray[n][i];
        }
        return row;
    }

    public int[] getColumn(int n){
        int[] column = new int[rotationalArray.length];
        for (int i = 0; i < rotationalArray[0].length; i++) {
            column[i] = rotationalArray[i][n];
        }
        return column;
    }

}
