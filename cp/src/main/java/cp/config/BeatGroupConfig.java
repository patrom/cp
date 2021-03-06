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
import cp.rhythm.BeatGroupRhythm;
import cp.rhythm.RandomBeatGroupRhythm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.*;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Configuration
public class BeatGroupConfig {

    @Autowired
    private BeatGroupRhythm defaultEvenBeatGroupRhythm;
    @Autowired
    private BeatGroupRhythm defaultUnEvenBeatGroupRhythm;
    @Autowired
    private BeatGroupRhythm homophonicUnevenBeatGroupRhythm;
    @Autowired
    private BeatGroupRhythm homophonicEvenBeatGroupRhythm;
    @Autowired
    private BeatGroupRhythm smallBeatGroupRhythm;

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
    @Autowired
    private RandomAllPitchClasses randomAllPitchClasses;
//    @Autowired
//    private HarmonyPitchClasses harmonyPitchClasses;

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupOne(){
        return new BeatGroupMelody(DurationConstants.QUARTER,
                defaultEvenBeatGroupRhythm , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupTwo(){
        return new BeatGroupMelody(DurationConstants.HALF,
                defaultEvenBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupThree(){
        return new BeatGroupMelody(DurationConstants.THREE_EIGHTS, DurationConstants.SIXTEENTH_TRIPLET,
                defaultUnEvenBeatGroupRhythm , Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupFour(){
        return new BeatGroupMelody(DurationConstants.WHOLE,
                defaultEvenBeatGroupRhythm , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }


    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicOne(){
        return new BeatGroupMelody(DurationConstants.QUARTER,
                homophonicEvenBeatGroupRhythm, Collections.singletonList(passingPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicTwo(){
        return new BeatGroupMelody(DurationConstants.HALF,
                homophonicEvenBeatGroupRhythm, Collections.singletonList(randomAllPitchClasses::randomPitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicThree(){
        return new BeatGroupMelody(DurationConstants.SIX_EIGHTS,
                homophonicUnevenBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHomophonicFour(){
        return new BeatGroupMelody(DurationConstants.WHOLE,
                homophonicEvenBeatGroupRhythm, Collections.singletonList(randomAllPitchClasses::randomPitchClasses));
    }


    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupHarmonyOne(){
        return new BeatgroupHarmony(DurationConstants.QUARTER,
                smallBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyTwo(){
        return new BeatgroupHarmony(DurationConstants.HALF,
                smallBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroup beatGroupHarmonyThree(){
        return new BeatgroupHarmony(DurationConstants.THREE_EIGHTS,
                smallBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupHarmonyFour(){
        return new BeatgroupHarmony(DurationConstants.WHOLE,
                smallBeatGroupRhythm , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupMotiveOne(){
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
        BeatgroupMotive beatgroupMotive = new BeatgroupMotive(length, DurationConstants.EIGHT,
                getBeatGroupRhythm(length), Collections.singletonList(randomPitchClasses::randomPitchClasses));
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
    public BeatGroupRhythm getBeatGroupRhythm(int length){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1quintupletpos12345);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::pos1eptTupletpos1234567);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::custom);
//        beatGroups2.add(rhythmCombinations.combiNoteEven::customRandom);
//        beatGroups2.add(rhythmCombinations.threeNoteEven::pos234);
        map.put(6, beatGroups2);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_5gon(){
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,
                beatGroupBalance30_5gonRhythm() ,
                Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupBalance30_5gonRhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups5 = new ArrayList<>();
//        rhythmCombinations.balancedPattern.setSize(30);
        beatGroups5.add(rhythmCombinations.balancedPattern::pos5N30);
//        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_0X000);
//        beatGroups5.add(rhythmCombinations.balancedPattern::pos5_00X00);
        map.put(5, beatGroups5);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_3gon(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.balancedPattern::pos3N30);//random
        map.put(3, beatGroups3);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,
                beatGroupBalance30_3gonRhythm() ,
                Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupBalance30_3gonRhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.balancedPattern::pos3N30);//random
        map.put(3, beatGroups3);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30_6in30gon(){
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,
                beatGroupBalance30_6in30gonRhythm()
                , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupBalance30_6in30gonRhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.balancedPattern::pos6N30);//random
        map.put(6, beatGroups);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public BeatGroup beatGroupBalance30(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups6 = new ArrayList<>();
        beatGroups6.add(rhythmCombinations.balancedPattern::pos6N30);//random
        map.put(6, beatGroups6);

        List<RhythmCombination> beatGroups7 = new ArrayList<>();
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7ain30);
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7bN30);
        map.put(7, beatGroups7);

        List<RhythmCombination> beatGroups8 = new ArrayList<>();
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8aN30);
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8bN30);
        map.put(8, beatGroups8);

        List<RhythmCombination> beatGroups9 = new ArrayList<>();
        beatGroups9.add(rhythmCombinations.balancedPattern::pos9N30);//random
        map.put(9, beatGroups9);
        return new BeatGroupMelody(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH,
                beatGroupBalance30Rhythm() ,
                Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupBalance30Rhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups6 = new ArrayList<>();
        beatGroups6.add(rhythmCombinations.balancedPattern::pos6N30);//random
        map.put(6, beatGroups6);

        List<RhythmCombination> beatGroups7 = new ArrayList<>();
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7ain30);
        beatGroups7.add(rhythmCombinations.balancedPattern::pos7bN30);
        map.put(7, beatGroups7);

        List<RhythmCombination> beatGroups8 = new ArrayList<>();
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8aN30);
        beatGroups8.add(rhythmCombinations.balancedPattern::pos8bN30);
        map.put(8, beatGroups8);

        List<RhythmCombination> beatGroups9 = new ArrayList<>();
        beatGroups9.add(rhythmCombinations.balancedPattern::pos9N30);//random
        map.put(9, beatGroups9);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp1(){
        return new BeatgroupMotive(DurationConstants.HALF,
                beatGroupAccomp1Rhythm() , Collections.singletonList(randomAllPitchClasses::randomPitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupAccomp1Rhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups2.add(rhythmCombinations.oneNoteEven::pos1);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos3);
//        map.put(1, beatGroups1);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp2(){
        return new BeatgroupMotive(DurationConstants.QUARTER,
                beatGroupAccomp2Rhythm() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupAccomp2Rhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.sixNoteSexTuplet::pos123456);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos1);
//        map.put(1, beatGroups1);
        return new RandomBeatGroupRhythm(map);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public BeatGroup beatGroupAccomp3(){
        return new BeatgroupMotive(DurationConstants.QUARTER,
                beatGroupAccomp3Rhythm() , Collections.singletonList(orderPitchClasses::updatePitchClasses));
    }

    @Bean
    public BeatGroupRhythm beatGroupAccomp3Rhythm(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.fourNoteEven::pos1234);
        map.put(2, beatGroups2);
//        List<RhythmCombination> beatGroups1 = new ArrayList<>();
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos4);
//        beatGroups1.add(rhythmCombinations.oneNoteEven::pos3);
//        map.put(1, beatGroups1);
        return new RandomBeatGroupRhythm(map);
    }

}
