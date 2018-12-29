package cp.out.orchestration;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NoteDistribution {

    private Multimap<String, List<Integer>> distributions = ArrayListMultimap.create();

    public NoteDistribution() {
        //Chord size - instrument size
        this.distributions.put("4-2", Arrays.asList(1,3));
        this.distributions.put("4-2", Arrays.asList(2,2));
        this.distributions.put("4-3", Arrays.asList(1,1,2));

        this.distributions.put("5-2", Arrays.asList(2,3));
        this.distributions.put("5-3", Arrays.asList(1,1,3));
        this.distributions.put("5-3", Arrays.asList(1,2,2));

        this.distributions.put("6-2", Arrays.asList(3,3));
        this.distributions.put("6-3", Arrays.asList(1,2,3));
        this.distributions.put("6-4", Arrays.asList(1,1,2,2));

        this.distributions.put("7-3", Arrays.asList(2,2,3));
        this.distributions.put("7-4", Arrays.asList(2,2,2,1));
        this.distributions.put("7-4", Arrays.asList(3,2,1,1));

        this.distributions.put("8-4", Arrays.asList(3,2,2,1));
        this.distributions.put("8-4", Arrays.asList(2,2,2,2));

        this.distributions.put("9-4", Arrays.asList(3,2,2,2));
        this.distributions.put("9-4", Arrays.asList(3,3,2,1));
        this.distributions.put("9-5", Arrays.asList(2,2,2,2,1));
        this.distributions.put("9-5", Arrays.asList(3,2,2,1,1));

        this.distributions.put("10-5", Arrays.asList(3,2,2,2,1));
        this.distributions.put("10-5", Arrays.asList(2,2,2,2,2));
        this.distributions.put("10-5", Arrays.asList(1,1,2,3,3));
        this.distributions.put("10-6", Arrays.asList(1,1,2,2,2,2));

        this.distributions.put("11-6", Arrays.asList(1,2,2,2,2,2));
        this.distributions.put("11-6", Arrays.asList(1,1,2,2,2,3));

        this.distributions.put("12-6", Arrays.asList(2,2,2,2,2,2));
        this.distributions.put("12-6", Arrays.asList(3,1,2,2,2,2));
    }

    public Integer[] getNoteDistribution(int noteSize, int instrumentSize) {
        String input = noteSize + "-" + instrumentSize;
        Collection<List<Integer>> distributions = this.distributions.get(input);
        if (distributions.isEmpty()) {
            throw new IllegalArgumentException("No note distribution found.");
        }
        List<Integer> integers = RandomUtil.getRandomFromList(new ArrayList<>(distributions));
        Collections.shuffle(integers);
        return integers.toArray(new Integer[integers.size()]);
    }
}
