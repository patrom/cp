package cp.model.common;

import cp.model.note.Scale;

import java.util.Collection;

public class CommonNoteValueObject {

    Collection<Integer> commonPitchClasses;
    Collection<Integer> disjunctPitchClasses1;
    Collection<Integer> disjunctPitchClasses2;

    public Collection<Integer> getCommonPitchClasses() {
        return commonPitchClasses;
    }

    public void setCommonPitchClasses(Collection<Integer> commonPitchClasses) {
        this.commonPitchClasses = commonPitchClasses;
    }

    public Collection<Integer> getDisjunctPitchClasses1() {
        return disjunctPitchClasses1;
    }

    public void setDisjunctPitchClasses1(Collection<Integer> disjunctPitchClasses1) {
        this.disjunctPitchClasses1 = disjunctPitchClasses1;
    }

    public Collection<Integer> getDisjunctPitchClasses2() {
        return disjunctPitchClasses2;
    }

    public void setDisjunctPitchClasses2(Collection<Integer> disjunctPitchClasses2) {
        this.disjunctPitchClasses2 = disjunctPitchClasses2;
    }

    public Scale getCommonScale() {
        return new Scale(commonPitchClasses.stream().mapToInt(Integer::intValue).toArray());
    }

    public Scale getDisjunct1Scale() {
        return new Scale(disjunctPitchClasses1.stream().mapToInt(Integer::intValue).toArray());
    }

    public Scale getDisjunct2Scale() {
        return new Scale(disjunctPitchClasses2.stream().mapToInt(Integer::intValue).toArray());
    }
}
