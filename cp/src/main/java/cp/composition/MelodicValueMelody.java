package cp.composition;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MelodicValueMelody implements MelodicValue{

    private List<CpMelody> melodies = new ArrayList<>();
    private List<CpMelody> exhaustiveMelodies = new ArrayList<>();
    private List<CpMelody> melodiesWithMultipleNotes = new ArrayList<>();
    private ContourType contourType;

    public MelodicValueMelody() {
    }

    public MelodicValueMelody(MelodicValueMelody melodicValue) {
        this.melodies = melodicValue.getMelodies();
        exhaustiveMelodies = new ArrayList<>(melodies);
        melodiesWithMultipleNotes = melodies.stream().filter(cpMelody -> cpMelody.getNotes().size() > 1).collect(Collectors.toList());
    }

    public MelodicValueMelody(List<CpMelody> melodies) {
        this.melodies = melodies;
        exhaustiveMelodies = new ArrayList<>(melodies);
    }

    public MelodicValueMelody clone() {
        return new MelodicValueMelody(this);
    }

    public CpMelody pickRandomMelody(){
        CpMelody melody = RandomUtil.getRandomFromList(melodies);
        if (ContourType.ASC == contourType){
            melody.updateContourAscending();
        }
        if (ContourType.DESC == contourType){
            melody.updateContourDescending();
        }
        return melody;
    }

    @Override
    public CpMelody pickRandomMelodyWithMultipleNotes(){
        return RandomUtil.getRandomFromList(melodiesWithMultipleNotes);
    }

    public CpMelody pickExhaustiveMelody(){
        if (melodies.isEmpty()) {
            melodies.addAll(exhaustiveMelodies);
        }
        CpMelody melody = RandomUtil.getRandomFromList(melodies);
        melodies.remove(melody);
        return melody;
    }

    public void setMelodies(List<CpMelody> melodies) {
        this.melodies = melodies;
        exhaustiveMelodies = new ArrayList<>(melodies);
        melodiesWithMultipleNotes = melodies.stream().filter(cpMelody -> cpMelody.getNotes().size() > 1).collect(Collectors.toList());
    }

    public void addMelodies(List<CpMelody> melodies){
        this.melodies.addAll(melodies);
    }

    public int size(){
        return melodies.size();
    }

    public List<CpMelody> getMelodies() {
        return melodies;
    }

    public void setContourType(ContourType contourType) {
        this.contourType = contourType;
    }
}
