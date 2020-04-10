package cp.generator;

import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PCGenerator {

    @Autowired
    private ChordGenerator chordGenerator;

    public List<Integer> getPitchClasses(String fortename, int transpose){
        int[] setClass = chordGenerator.generatePitchClasses(fortename);
        return Arrays.stream(setClass).boxed().map(integer -> {
            return (integer + transpose) % 12;
        }).collect(Collectors.toList());
    }

    public List<Integer> getInversionPitchClasses(String fortename, int transpose){
        int[] setClass = chordGenerator.generateInversionPitchclasses(fortename);
        return Arrays.stream(setClass).boxed().map(integer -> {
            return (integer + transpose) % 12;
        }).collect(Collectors.toList());
    }

    public List<Integer> getShuffledPitchClasses(String forteName, int transpose) {
        List<Integer> pitchClasses = getPitchClasses(forteName, transpose);
        Collections.shuffle(pitchClasses);
        return pitchClasses;
    }

    public List<Integer> getShuffledInversionPitchClasses(String forteName, int transpose) {
        List<Integer> pitchClasses = getInversionPitchClasses(forteName, transpose);
        Collections.shuffle(pitchClasses);
        return pitchClasses;
    }

    public List<Integer> getRandomRepetitionPitchClasses(String forteName, int transpose, int repetitionFactor) {
        List<Integer> pitchClasses = getPitchClasses(forteName, transpose);
        for (int i = 0; i < repetitionFactor; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClasses);
            int randomInt = RandomUtil.randomInt(0, pitchClasses.size());
            pitchClasses.add(randomInt, pitchClass);
        }
        return pitchClasses;
    }

    public List<Integer> getRandomRepetitionInversionPitchClasses(String forteName, int transpose, int repetitionFactor) {
        List<Integer> pitchClasses = getInversionPitchClasses(forteName, transpose);
        for (int i = 0; i < repetitionFactor; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClasses);
            int randomInt = RandomUtil.randomInt(0, pitchClasses.size());
            pitchClasses.add(randomInt, pitchClass);
        }
        return pitchClasses;
    }
}
