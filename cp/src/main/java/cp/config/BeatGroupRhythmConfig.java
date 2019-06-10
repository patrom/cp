package cp.config;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.rhythm.BeatGroupRhythm;
import cp.rhythm.CompleteBeatGroupRhythm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BeatGroupRhythmConfig {

    @Resource(name = "defaultUnevenCombinations")
    private Map<Integer, List<RhythmCombination>> defaultUnEvenCombinations;

    @Resource(name = "defaultEvenCombinations")
    private Map<Integer, List<RhythmCombination>> defaultEvenCombinations;

    @Resource(name = "homophonicEven")
    private  Map<Integer, List<RhythmCombination>> homophonicEven;

    @Resource(name = "homophonicUneven")
    private Map<Integer, List<RhythmCombination>> homophonicUneven;

    @Resource(name = "fixedEven")
    private Map<Integer, List<RhythmCombination>> fixedEven;

    @Resource(name = "fixedUneven")
    private Map<Integer, List<RhythmCombination>> fixedUneven;


    @Autowired
    private RhythmCombinations rhythmCombinations;


    @Bean
    public BeatGroupRhythm defaultEvenBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(defaultEvenCombinations);
        return new CompleteBeatGroupRhythm(defaultEvenCombinations);
    }

    @Bean
    public BeatGroupRhythm defaultUnEvenBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(defaultUnEvenCombinations);
        return new CompleteBeatGroupRhythm(defaultUnEvenCombinations);
    }

    @Bean
    public BeatGroupRhythm homophonicUnevenBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(homophonicUneven);
        return new CompleteBeatGroupRhythm(homophonicUneven);
    }

    @Bean
    public BeatGroupRhythm homophonicEvenBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(homophonicUneven);
        return new CompleteBeatGroupRhythm(homophonicEven);
    }

    @Bean
    public BeatGroupRhythm smallBeatGroupRhythm(){
//        return new RandomBeatGroupRhythm(getBeatGroups());
        return new CompleteBeatGroupRhythm(getBeatGroups());
    }


    private Map<Integer, List<RhythmCombination>> getBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
//        beatGroups2.add(rhythmCombinations.oneNoteEven::pos1);
//        beatGroups2.add(rhythmCombinations.fourNoteEven::pos1234);
        beatGroups2.add(rhythmCombinations.threeNoteUneven::pos123);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        beatGroups2.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(3, beatGroups2);

//        List<RhythmCombination> beatGroups4 = new ArrayList<>();
//        beatGroups4.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups4.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        map.put(4, beatGroups4);
        return map;
    }
}
