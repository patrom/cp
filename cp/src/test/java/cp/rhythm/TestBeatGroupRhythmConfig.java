package cp.rhythm;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TestBeatGroupRhythmConfig {

    @Autowired
    private RhythmCombinations rhythmCombinations;


    @Bean
    public BeatGroupRhythm testCompleteBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(defaultEvenCombinations);
        return new CompleteBeatGroupRhythm(beatGroupRhythm());
    }

    public Map<Integer, List<RhythmCombination>> beatGroupRhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.twoNoteEven::pos13);
        beatGroups2.add(rhythmCombinations.twoNoteEven::pos12);
        map.put(2, beatGroups2);
        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.threeNoteEven::pos134);
        map.put(3, beatGroups3);
        return map;
    }
}
