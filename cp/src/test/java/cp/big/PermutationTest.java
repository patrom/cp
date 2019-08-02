package cp.big;


import com.google.common.collect.Collections2;
import cp.DefaultConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
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



