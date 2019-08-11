package cp.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Permutations {

    private Permutations() {
    }

    public static long factorial(int n) {
        if (n > 100 || n < 0) throw new IllegalArgumentException(n + " is out of range");
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
    }

    public static <T> List<T> permutation(long no, List<T> items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new ArrayList<>());
    }

    private static <T> List<T> permutationHelper(long no, LinkedList<T> in, List<T> out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper((int) (no % subFactorial), in, out);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<Stream<T>> of(T... items) {
        List<T> itemList = Arrays.asList(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList).stream());
    }

    public static <T> Stream<Stream<T>> of(List<T> itemList) {
        return LongStream.range(0, factorial(itemList.size()))
                .mapToObj(no -> permutation(no, itemList).stream());
    }


    private static void getSubsets(List<Integer> superSet, int k, int idx, List<Integer> current,List<List<Integer>> solution) {
        //successful stop clause
        if (current.size() == k) {
            solution.add(new ArrayList<>(current));
            return;
        }
        //unseccessful stop clause
        if (idx == superSet.size()) return;
        Integer x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        getSubsets(superSet, k, idx+1, current, solution);
        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx+1, current, solution);
    }

    public static List<List<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<List<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new ArrayList<>(), res);
        return res;
    }

    /* arr[] ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index ---> Current index in data[]
    r ---> Size of a combination to be printed */
    public static void combinationUtil(int arr[], int n, int r,
                                int index, int data[], int i) {
        // Current combination is ready to be printed,
        // print it
        if (index == r) {
            for (int j = 0; j < r; j++){
                System.out.print(data[j] + " ");
            }
            System.out.println("");
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n){
            return;
        }

        // current is included, put next at next
        // location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index + 1,
                data, i + 1);

        // current is excluded, replace it with
        // next (Note that i+1 is passed, but
        // index is not changed)
        combinationUtil(arr, n, r, index, data, i + 1);
    }

    // The main function that prints all combinations
    // of size r in arr[] of size n. This function
    // mainly uses combinationUtil()
    public static void printCombination(int arr[],  int r) {
        // A temporary array to store all combination
        // one by one
        int n = arr.length;
        int data[] = new int[r];

        // Print all combination using temprary
        // array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0);
    }


}