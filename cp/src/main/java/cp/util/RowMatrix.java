package cp.util;

import cp.model.note.Scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class RowMatrix {

    private int[][] rowMatrix;

    public static void main(String[] args) {

//        Integer[] s = {11,0, 6, 4, 10, 9, 1, 8, 3, 7, 5, 2};

        List<Integer> set = IntStream.of(Scale.SET_6_27.getPitchClasses()).boxed().collect(Collectors.toList());
        Stream<Integer> firstHexaChord = IntStream.of(Scale.ALL_COMBINATORIAL_HEXAHCORD_C.getPitchClasses()).boxed();
        Stream<Integer> secondHexachord = IntStream.of(Scale.ALL_COMBINATORIAL_HEXAHCORD_C_COMPLEMENT.getPitchClasses()).boxed();

//        List<Integer> set = Stream.concat(firstHexaChord, secondHexachord).collect(Collectors.toList());
//		Collections.shuffle(set);
//        System.out.println(set);
//        System.out.println(RowMatrix.multiply(set));
//        System.out.println(RowMatrix.multiplyInverse(set));
//        System.out.println(RowMatrix.rotate(set));

        RowMatrix rowMatrix = new RowMatrix(set.size(), set);
        rowMatrix.show();

        for (int i = 0; i < set.size(); i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
            System.out.println(Arrays.toString(row));
            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
            System.out.println(Arrays.toString(column));
        }

//        int[] row = rowMatrix.getRow(s.length - 1);
//        Arrays.sort(row);
//        System.out.println(Arrays.toString(row));
//
//        int[] column = rowMatrix.getColumn(0);
//        Arrays.sort(column);
//        System.out.println(Arrays.toString(column));

//        List<Integer> inversionSet = inversion(set);

//        System.out.println(inversionSet);
//        RowMatrix rowMatrixInversion = new RowMatrix(inversionSet.size(), inversionSet);
//        rowMatrixInversion.show();

//        System.out.println(Arrays.toString(rowMatrix.getRow(0)));
//        System.out.println(Arrays.toString(rowMatrix.getColumn(2)));

//
//        System.out.println(rowMatrix.transposeSet(4));
//        System.out.println(rowMatrix.retrogradeTransposeSet(10));
//        System.out.println(rowMatrix.transposeInverseSet(4));
//        System.out.println(rowMatrix.retrogradeTransposeInverseSet(10));
//
//        System.out.println(rowMatrix.transposeInverseSet(5));

//        System.out.println(RowMatrix.multiply(set));
    }

    private static void setFirstPcToZero(List<Integer> s) {
        if (s.get(0) != 0) {
            Integer first = s.get(0);
            for (int i = 0; i < s.size(); i++) {
                Integer value = (s.get(i) - first + 12) % 12;
                s.set(i, value);
            }
        }
    }

    public RowMatrix(int size, List<Integer> row) {
        super();
        List<Integer> set = new ArrayList<>(row);
        setFirstPcToZero(set);
        this.rowMatrix = new int[size][size];
        List<Integer> inversionSet = inversion(set);
        for (int i = 0; i < size; i++) {
            rowMatrix[0][i] = set.get(i);
            rowMatrix[i][0] = inversionSet.get(i);
        }
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                rowMatrix[i][j] = (inversionSet.get(i) + set.get(j)) % 12;
            }
        }
    }

    public void show() {
        for (int i = 0; i < rowMatrix[0].length; i++) {
            for (int j = 0; j < rowMatrix.length; j++){
                int pc = rowMatrix[i][j];
                System.out.print(pc + "," + "\t");
            }
            System.out.println();
        }
    }

    public int[] getRow(int n){
        int[] row = new int[rowMatrix.length];
        for (int i = 0; i < rowMatrix[0].length; i++) {
            row[i] = rowMatrix[n][i];
        }
        return row;
    }

    public int[] getColumn(int n){
        int[] column = new int[rowMatrix.length];
        for (int i = 0; i < rowMatrix[0].length; i++) {
            column[i] = rowMatrix[i][n];
        }
        return column;
    }

    /**
     * 	The TnS rows are listed on matrix rows from left to right.
     * @param n transpose by n
     */
    public List<Integer> transposeSet(int n) {
        int row = 0;
        int t = (rowMatrix[0][0] + n) % 12;
        for (int i = 0; i < rowMatrix[0].length; i++) {
            if(rowMatrix[i][0] == t){
                row = i;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < rowMatrix[0].length; i++) {
            list.add(rowMatrix[row][i]);
        }
        return list;
    }

    /**
     * 	The RTnS rows are listed on matrix rows from right to left.
     * @param n
     * @return
     */
    public List<Integer> retrogradeTransposeSet(int n) {
        int row = 0;
        int t = (rowMatrix[0][0] + n) % 12;
        for (int i = 0; i < rowMatrix[0].length; i++) {
            if(rowMatrix[i][rowMatrix[0].length - 1] == t){
                row = i;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = rowMatrix[0].length - 1; i >= 0; i--) {
            list.add(rowMatrix[row][i]);
        }
        return list;
    }

    /**
     * 	The TnIS rows are listed on matrix columns from top to bottom.
     * @param n
     * @return
     */
    public List<Integer> transposeInverseSet(int n) {
        int column = 0;
        int t = (rowMatrix[0][0] + n) % 12;
        for (int i = 0; i < rowMatrix[0].length; i++) {
            if(rowMatrix[0][i] == t){
                column = i;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < rowMatrix.length; i++) {
            list.add(rowMatrix[i][column]);
        }
        return list;
    }

    /**
     * 	The RTnIS rows are listed on matrix columns from bottom to top.
     * @param n
     * @return
     */
    public List<Integer> retrogradeTransposeInverseSet(int n) {
        int column = 0;
        int t = (rowMatrix[0][0] + n) % 12;
        for (int i = 0; i < rowMatrix.length; i++) {
            if(rowMatrix[rowMatrix.length - 1][i] == t){
                column = i;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = rowMatrix.length - 1; i >= 0; i--) {
            list.add(rowMatrix[i][column]);
        }
        return list;
    }

    /**
     * M operator
     * @param set
     * @return
     */
    public static List<Integer> multiply (List<Integer> set){
        List<Integer> list = new ArrayList<>();
        for (Integer integer : set) {
            list.add((integer * 5) % 12);
        }
        return list;
    }


    /**
     * MI operator
     * @param set
     * @return
     */
    public static List<Integer> multiplyInverse (List<Integer> set){
        List<Integer> list = new ArrayList<>();
        for (Integer integer : set) {
            list.add((integer * 7) % 12);
        }
        return list;
    }

    /**
     * Rotate set 1 position
     * @param set
     * @return
     */
    public static List<Integer> rotate (List<Integer> set){
        List<Integer> list = new ArrayList<>(set);
//		setFirstPcToZero(list);
        Integer first = list.get(0);
        Integer rotateValue = list.get(1);
        list.remove(0);
        list.add(first);

        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext()) {
            Integer integer = it.next();
            it.set((12 + integer - rotateValue) % 12);
        }
        return list;
    }

    public static List<Integer> inversion(List<Integer> set){
        List<Integer> inversion = new ArrayList<>();
        Integer[] arr = new Integer[set.size()];
        arr = set.toArray(arr);
        Integer pc = set.get(0) % 12;
        inversion.add(pc);
        for (int i = 0; i < arr.length - 1; i++) {
            Integer interval = (arr[i+1] - arr[i]);
            pc = (pc - interval) % 12;
            if (pc < 0) {
                pc = 12 + pc;
            }
            inversion.add(pc);
        }
        return inversion;
    }

}
