package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.beat.harmony.BeatgroupHarmony;
import cp.composition.beat.melody.BeatGroupMelody;
import cp.composition.beat.motive.BeatgroupMotive;
import cp.generator.pitchclass.*;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.*;

@Configuration
public class BeatGroupConfig {

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
    private RandomPitchClasses randomPitchClasses;
    @Autowired
    private PassingPitchClasses passingPitchClasses;
    @Autowired
    private RepeatingPitchClasses repeatingPitchClasses;
    @Autowired
    private OrderPitchClasses orderPitchClasses;
    @Autowired
    private OrderRandomNotePitchClasses orderRandomNotePitchClasses;
    @Autowired
    private OrderNoteRepetitionPitchClasses orderNoteRepetitionPitchClasses;
//    @Autowired
//    private HarmonyPitchClasses harmonyPitchClasses;

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Bean
    public BeatGroup beatGroupOne(){
        return new BeatGroupMelody(1,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupTwo(){
        return new BeatGroupMelody(2,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupThree(){
        return new BeatGroupMelody(3,
                defaultUnEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupFour(){
        return new BeatGroupMelody(4,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }


    @Bean
    public BeatGroup beatGroupHomophonicOne(){
        return new BeatGroupMelody(1,
                homophonicEven , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHomophonicTwo(){
        return new BeatGroupMelody(2,
                homophonicEven , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHomophonicThree(){
        return new BeatGroupMelody(3,
                homophonicUneven , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHomophonicFour(){
        return new BeatGroupMelody(4,
                homophonicEven , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }


    @Bean
    public BeatGroup beatGroupHarmonyOne(){
        return new BeatgroupHarmony(1,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyTwo(){
        return new BeatgroupHarmony(2,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyFour(){
        return new BeatgroupHarmony(16,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupMotiveOne(){
//        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
//        List<RhythmCombination> beatGroups2 = new ArrayList<>();
//        beatGroups2.add(rhythmCombinations.threeNoteEven::pos134);
//        beatGroups2.add(rhythmCombinations.threeNoteEven::pos234);
//        map.put(2, beatGroups2);
//
//        BeatgroupMotive beatgroupMotive = new BeatgroupMotive(1,
//                map, Collections.singletonList(orderPitchClasses::updatePitchClasses));
//        beatgroupMotive.setMotiveScale(Scale.MAJOR_SCALE);
//        beatgroupMotive.setTonality(Tonality.ATONAL);
////        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
////        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
////        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
////        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
//        Scale scale = new Scale(new int[]{0, 3, 2});
////        Scale scale = new Scale(new int[]{0, 2, 3, 4});
//        beatgroupMotive.addMotivePitchClasses(scale);
        return getBeatGroup(1);
    }

    @Bean
    public BeatGroup beatGroupMotiveTwo(){
        return getBeatGroup(2);
    }

    @Bean
    public BeatGroup beatGroupMotiveThree(){
        return getBeatGroup(4);
    }

    @Bean
    public BeatGroup beatGroupMotiveFour(){
        return getBeatGroup(4);
    }

    private BeatGroup getBeatGroup(int type) {
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1quintupletpos12345);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1eptTupletpos1234567);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::custom);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::customRandom);
//        beatGroups2.add(rhythmCombinations.threeNoteEven::pos234);
        map.put(6, beatGroups2);

        BeatgroupMotive beatgroupMotive = new BeatgroupMotive(type,
                map, Collections.singletonList(randomPitchClasses::randomPitchClasses));
        beatgroupMotive.setMotiveScale(Scale.MAJOR_SCALE);
//        beatgroupMotive.setTonality(Tonality.ATONAL);
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
        Scale scale = new Scale(new int[]{0, 1, 2, 3, 4});
//        Scale scale = new Scale(new int[]{0, 2, 3, 4});
//        beatgroupMotive.addMotivePitchClasses(scale);
//        scale = new Scale(new int[]{0, 3, 8});
//        beatgroupMotive.addMotivePitchClasses(scale);
        return beatgroupMotive;
    }

    @Bean
    public BeatGroup beatGroupBalance30(){
        return new BeatGroupMelody(30, getBalance30BeatGroups() , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    private void test() {
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
//        beatGroups2.add(rhythmCombinations.oneNoteEven::pos1);
//        beatGroups2.add();
        rhythmCombinations.combiNoteEven.combiHalf(
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.threeNoteEven::pos234,
                DurationConstants.WHOLE);
        map.put(4, beatGroups2);
        
    }

    private Map<Integer, List<RhythmCombination>> getBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
//        beatGroups2.add(rhythmCombinations.oneNoteEven::pos1);
        beatGroups2.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        beatGroups2.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(4, beatGroups2);

//        List<RhythmCombination> beatGroups4 = new ArrayList<>();
//        beatGroups4.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups4.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        map.put(4, beatGroups4);
        return map;
    }

    private Map<Integer, List<RhythmCombination>> getBalance30BeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups5 = new ArrayList<>();
        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_X0000);
        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_0X000);
        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_00X00);
        map.put(5, beatGroups5);

        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.balancedPattern::pos3);
        map.put(3, beatGroups3);
        return map;
    }
}
