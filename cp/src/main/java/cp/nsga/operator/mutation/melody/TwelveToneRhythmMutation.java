package cp.nsga.operator.mutation.melody;

import cp.config.TwelveToneConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.twelve.TwelveToneBuilder;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component(value = "twelveToneRhythmMutation")
public class TwelveToneRhythmMutation implements MutationOperator<CpMelody> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);

    private double probabilityTwelveToneRhythm;

    @Autowired
    private TwelveToneConfig twelveToneConfig;

    @Autowired
    public TwelveToneRhythmMutation(@Value("${probabilityTwelveToneRhythm}") double probabilityTwelveToneRhythm) {
        this.probabilityTwelveToneRhythm = probabilityTwelveToneRhythm;
    }

    public void doMutation(CpMelody melody) {
        if (PseudoRandom.randDouble() < probabilityTwelveToneRhythm) {
            Integer voice = melody.getVoice();

            TwelveToneBuilder twelveToneBuilder = twelveToneConfig.getTwelveToneBuilder(voice, melody.getStart());
            List<Note> notes = twelveToneBuilder.buildRepeat();


//            List<Note> melNotes = melody.getNotes();
//            Note noteMove = RandomUtil.getRandomFromList(melNotes);
//            Scale scale = this.twelveToneConfig.getTwelveToneConfigForVoice(melody.getVoice());
//            List<Note> melodyNotes = getTwelveToneNotes(melNotes, noteMove, scale);

            melody.updateNotes(notes);

//          LOGGER.info("TwelveToneBuilder tone rhythm mutated: " + voice);
        }
    }

//    protected List<Note> getTwelveToneNotes(List<Note> melNotes, Note noteMove, Scale scale) {
//        List<Note> allNotes = new ArrayList<>();
//        for (Note melNote : melNotes) {
//            allNotes.add(melNote);
//            if (melNote.getDependantHarmony() != null) {
//                allNotes.addAll(melNote.getDependantHarmony().getNotes());
//            }
//        }
//
//        int oldPosition = noteMove.getPosition();
//
//        Integer positionBeforeOrAfter = twelveToneBuilder.getRandomPositionBeforeOrAfter(oldPosition);
//        noteMove.setPosition(positionBeforeOrAfter);
//
//        //update pitch classes
//        int[] pitchClasses = scale.getPitchClasses();
//        Collections.sort(allNotes);
//        for (int i = 0; i < pitchClasses.length; i++) {
//            int pitchClass = pitchClasses[i];
//            Note note = allNotes.get(i);
//            note.setPitchClass(pitchClass);
//        }
//        //set length of note
//        NavigableMap<Integer, List<Note>> notesPerPosition = allNotes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, Collectors.toList()));
//        List<Note> melodyNotes = new ArrayList<>();
//        for (Map.Entry<Integer, List<Note>> entry : notesPerPosition.entrySet()) {
//            List<Note> notes = entry.getValue();
//            Integer position = entry.getKey();
//            Note note = notes.get(0);
//            Integer nextPosition = notesPerPosition.higherKey(entry.getKey());// next
//            int length;
//            //set length if note is larger than positions in between
//            if (nextPosition == null) {//last position
//                length = getNoteLength(twelveToneBuilder.getEnd(), position);
//                note.setLength(length);
//                note.setDisplayLength(length);
//            }
//            else if ((nextPosition - (position + notes.get(0).getLength())) < 0) {
//                length = getNoteLength(nextPosition, position);
//                note.setLength(length);
//                note.setDisplayLength(length);
//            }
//
//            //update dependant notes
//            if (notes.size() > 1) {
//                notes.remove(note);
//                DependantHarmony dependantHarmony = new DependantHarmony();
//                dependantHarmony.setChordType(ChordType.TWELVE_TONE);
//                dependantHarmony.setNotes(notes);
//                note.setDependantHarmony(dependantHarmony);
//                melodyNotes.add(note);
//            } else {
//                melodyNotes.add(note);
//            }
//        }
//        return melodyNotes;
//    }

//    private int getNoteLength(int endGrid, Integer position) {
//        Integer length = RandomUtil.getRandomFromList(twelveToneConfig.getDurations());
//        while (position + length > endGrid ){
//            length = RandomUtil.getRandomFromList(twelveToneConfig.getDurations());
//        }
//        return length;
//    }

    private Optional<Integer> getFirstPositionBefore(Note notePosition, List<Note> grid){
        return grid.stream().map(note -> note.getPosition()).filter(position -> position < notePosition.getPosition()).sorted(Comparator.reverseOrder()).findFirst();
    }

    private Optional<Integer> getFirstPositionAfter(Note notePosition, List<Note> grid){
        return grid.stream().map(note -> note.getPosition()).filter(position -> position > notePosition.getPosition()).sorted().findFirst();
    }


    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(melody);
        return melody;
    }
}
