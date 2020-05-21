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
        return Arrays.stream(setClass).map(integer -> (integer + transpose) % 12).boxed().collect(Collectors.toList());
    }

    public List<Integer> getInversionPitchClasses(String fortename, int transpose){
        int[] setClass = chordGenerator.generateInversionPitchclasses(fortename);
        return Arrays.stream(setClass).map(integer -> (integer + transpose) % 12).boxed().collect(Collectors.toList());
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

    public List<Integer> getRandomRepetitionPitchClasses(String forteName, int repetitionFactor, int transpose) {
        List<Integer> pitchClasses = getPitchClasses(forteName, transpose);
        for (int i = 0; i < repetitionFactor; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClasses);
            int randomInt = RandomUtil.randomInt(0, pitchClasses.size());
            pitchClasses.add(randomInt, pitchClass);
        }
        return pitchClasses;
    }

    public List<Integer> getRandomRepetitionInversionPitchClasses(String forteName, int repetitionFactor, int transpose) {
        List<Integer> pitchClasses = getInversionPitchClasses(forteName, transpose);
        for (int i = 0; i < repetitionFactor; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClasses);
            int randomInt = RandomUtil.randomInt(0, pitchClasses.size());
            pitchClasses.add(randomInt, pitchClass);
        }
        return pitchClasses;
    }

    public List<Integer> getOrderedRepetitionPitchClasses(String forteName, int repetitionFactor, int transpose) {
        List<Integer> pitchClasses = getPitchClasses(forteName, transpose);
        int size = pitchClasses.size() - 1;
        for (int i = 0; i < repetitionFactor; i++) {
            List<Integer> pitchClassesWithoutLast = pitchClasses.subList(0, size);
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClassesWithoutLast);
            int index = pitchClassesWithoutLast.indexOf(pitchClass);
            int randomInt = RandomUtil.randomInt(index + 1 , pitchClasses. size());
            pitchClasses.add(randomInt, pitchClass);
            size = size + 1;
        }
        return pitchClasses;
    }

    public List<Integer> getOrderedRepetitionInversionPitchClasses(String forteName, int repetitionFactor, int transpose) {
        List<Integer> pitchClasses = getInversionPitchClasses(forteName, transpose);
        int size = pitchClasses.size() - 1;
        for (int i = 0; i < repetitionFactor; i++) {
            List<Integer> pitchClassesWithoutLast = pitchClasses.subList(0, size);
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClassesWithoutLast);
            int index = pitchClassesWithoutLast.indexOf(pitchClass);
            int randomInt = RandomUtil.randomInt(index + 1 , pitchClasses. size());
            pitchClasses.add(randomInt, pitchClass);
            size = size + 1;
        }
        return pitchClasses;
    }
}
