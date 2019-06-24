package cp.big;



import com.google.common.collect.Collections2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PermutationTest {

    @Test
    public void permutations(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Collection<List<Integer>> permutations = Collections2.permutations(list);
        for (List<Integer> permutation : permutations) {
            permutation.forEach(integer -> System.out.print(integer + ", "));
            System.out.print("");
        }
    }
}



