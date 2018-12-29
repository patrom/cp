package cp.model.common;

import cp.generator.ChordGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonNote {

    @Autowired
    private ChordGenerator chordGenerator;

    public List<CommonNoteValueObject> getCommonNotes(String forteName, int commonSize) {
        List<Integer> pitchClasses = chordGenerator.generatePcs(forteName);
        int[] intervalVector = chordGenerator.getIntervalVector(forteName);
        List<Integer> transformationSizes = new ArrayList<>();
        for (int i = 0; i < intervalVector.length; i++) {
            if (intervalVector[i] == commonSize) {
                transformationSizes.add(i + 1);
            }
        }
        if (transformationSizes.isEmpty()) {
            throw new IllegalArgumentException("geen common notes voor size: " + commonSize);
        }
        List<List<Integer>> transformed = new ArrayList<>();
        for (Integer transformationSize : transformationSizes) {
            List<Integer> transformedPitchClasses = pitchClasses.stream().map(pc -> (pc + transformationSize) % 12).collect(Collectors.toList());
            transformed.add(transformedPitchClasses);
        }
        List<CommonNoteValueObject> commonNoteValueObjects = new ArrayList<>();
        for (List<Integer> transformationPitchClasses : transformed) {
            CommonNoteValueObject commonNoteValueObject = new CommonNoteValueObject();
            Collection<Integer> intersection = CollectionUtils.intersection(pitchClasses, transformationPitchClasses);
            commonNoteValueObject.setCommonPitchClasses(intersection);
            commonNoteValueObject.setDisjunctPitchClasses1(CollectionUtils.disjunction(pitchClasses, intersection));
            commonNoteValueObject.setDisjunctPitchClasses2(CollectionUtils.disjunction(transformationPitchClasses, intersection));
            commonNoteValueObjects.add(commonNoteValueObject);
        }
        return commonNoteValueObjects;
    }
}
