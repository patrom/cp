package cp.config;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.harmony.BeatgroupHarmony;
import cp.composition.beat.melody.BeatGroupMelody;
import cp.composition.beat.motive.BeatgroupMotive;
import cp.generator.pitchclass.*;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

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
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupOne(){
        return new BeatGroupMelody(DurationConstants.QUARTER,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupTwo(){
        return new BeatGroupMelody(DurationConstants.HALF,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupThree(){
        return new BeatGroupMelody(DurationConstants.THREE_EIGHTS, DurationConstants.SIXTEENTH_TRIPLET,
                defaultUnEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupFour(){
        return new BeatGroupMelody(DurationConstants.WHOLE,
                defaultEvenCombinations , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }


    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicOne(){
        return new BeatGroupMelody(DurationConstants.QUARTER,
                homophonicEven , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicTwo(){
        return new BeatGroupMelody(2,
                homophonicEven , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicThree(){
        return new BeatGroupMelody(DurationConstants.SIX_EIGHTS,
                homophonicUneven , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicFour(){
        return new BeatGroupMelody(DurationConstants.WHOLE,
                homophonicEven , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }


    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupHarmonyOne(){
        return new BeatgroupHarmony(DurationConstants.QUARTER,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyTwo(){
        return new BeatgroupHarmony(DurationConstants.HALF,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyThree(){
        return new BeatgroupHarmony(DurationConstants.THREE_EIGHTS,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHarmonyFour(){
        return new BeatgroupHarmony(DurationConstants.WHOLE,
                getBeatGroups() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
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
        return getBeatGroup(DurationConstants.QUARTER);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupMotiveTwo(){
        return getBeatGroup(DurationConstants.HALF);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupMotiveThree(){
        return getBeatGroup(DurationConstants.THREE_EIGHTS);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupMotiveFour(){
        return getBeatGroup(DurationConstants.WHOLE);
    }

    private BeatGroup getBeatGroup(int length) {
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1quintupletpos12345);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1eptTupletpos1234567);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::custom);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::customRandom);
//        beatGroups2.add(rhythmCombinations.threeNoteEven::pos234);
        map.put(6, beatGroups2);

        BeatgroupMotive beatgroupMotive = new BeatgroupMotive(length, DurationConstants.EIGHT,
                map, Collections.singletonList(randomPitchClasses::randomPitchClasses));
        beatgroupMotive.setMotiveScale(Scale.MAJOR_SCALE);
//        beatgroupMotive.setTonality(Tonality.ATONAL);
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
        Scale scale = new Scale(new int[]{0, 1});
//        Scale scale = new Scale(new int[]{0, 2, 3, 4});
//        beatgroupMotive.addMotivePitchClasses(scale);
//        scale = new Scale(new int[]{0, 3, 8});
//        beatgroupMotive.addMotivePitchClasses(scale);
        return beatgroupMotive;
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_5gon(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups5 = new ArrayList<>();
//        rhythmCombinations.balancedPattern.setSize(30);
        beatGroups5.add(rhythmCombinations.balancedPattern::pos5);
//        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_0X000);
//        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_00X00);
        map.put(5, beatGroups5);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,  map , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_3gon(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.balancedPattern::pos3);//random
        map.put(3, beatGroups3);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,  map , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_6in30gon(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.balancedPattern::pos6in30);//random
        map.put(6, beatGroups);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,  map , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups6 = new ArrayList<>();
        beatGroups6.add(rhythmCombinations.balancedPattern::pos6in30);//random
        map.put(6, beatGroups6);

        List<RhythmCombination> beatGroups7 = new ArrayList<>();
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7ain30);
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7bin30);
        map.put(7, beatGroups7);

        List<RhythmCombination> beatGroups8 = new ArrayList<>();
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8ain30);
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8bin30);
        map.put(8, beatGroups8);

        List<RhythmCombination> beatGroups9 = new ArrayList<>();
        beatGroups9.add(rhythmCombinations.balancedPattern::pos9in30);//random
        map.put(9, beatGroups9);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,  map , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp1(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.twoNoteEven::pos13);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos3);
//        map.put(1, beatGroups1);
        return new BeatgroupMotive(DurationConstants.QUARTER,
                map , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp2(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.sixNoteSexTuplet::pos123456);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos1);
//        map.put(1, beatGroups1);
        return new BeatgroupMotive(DurationConstants.QUARTER,
                map , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp3(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.fourNoteEven::pos1234);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos4);
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos3);
//        map.put(1, beatGroups1);
        return new BeatgroupMotive(DurationConstants.QUARTER,
                map , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

//    private void test() {
//        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
//        List<RhythmCombination> beatGroups2 = new ArrayList<>();
////        beatGroups2.add(rhythmCombinations.oneNoteEven::pos1);
////        beatGroups2.add();
//        rhythmCombinations.combiNoteEven.combiHalf(
//                rhythmCombinations.twoNoteEven::pos12,
//                rhythmCombinations.threeNoteEven::pos234,
//                DurationConstants.WHOLE);
//        map.put(4, beatGroups2);
//
//    }

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
