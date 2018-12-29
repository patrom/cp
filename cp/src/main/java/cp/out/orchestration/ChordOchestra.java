package cp.out.orchestration;

import cp.config.ColorQualityConfig;
import cp.config.InstrumentConfig;
import cp.config.OrchestraConfig;
import cp.generator.ChordGenerator;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.orchestration.quality.*;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChordOchestra {

    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private ColorQualityConfig colorQualityConfig;
    @Autowired
    private NoteDistribution noteDistribution;
    @Autowired
    private ChordGenerator chordGenerator;

    @Autowired
    private BrilliantWhite brilliantWhite;
    @Autowired
    private BrightYellow brightYellow;
    @Autowired
    private PleasantGreen pleasantGreen;
    @Autowired
    private RichBlue richBlue;
    @Autowired
    private GoldenOrange goldenOrange;
    @Autowired
    private GlowingRed glowingRed;
    @Autowired
    private MellowPurple mellowPurple;
    @Autowired
    private WarmBrown warmBrown;
    @Autowired
    private OrchestraConfig orchestraConfig;

    public void generateChordsOrchestra(String forteName, int duration, int iteration, int transpose){
        long chordSize = orchestraConfig.getOrchestralMappings().stream().mapToInt(orchestraMapping -> orchestraMapping.getSize()).sum();
        List<Note> notes = chordGenerator.generatePitches(forteName, duration);
        notes.forEach(note -> note.setPitch(note.getPitch() + transpose));
        for (int i = 0; i < iteration; i++) {
            List<Note> duplicateNotes = addDuplicateNotes((int) chordSize, notes);
            duplicateNotes.forEach(note -> {
                note.setPosition(note.getPosition() + duration);
                note.setDynamicLevel(Dynamic.P.getLevel());
            });
            Collections.shuffle(duplicateNotes);
            generateChordOrchestra(duplicateNotes);
        }
    }

    public void generateChordsOrchestra(List<Note> notes, int duration, int iteration, int transpose){
        long chordSize = orchestraConfig.getOrchestralMappings().stream().mapToInt(orchestraMapping -> orchestraMapping.getSize()).sum();
        notes.forEach(note -> note.setPitch(note.getPitch() + transpose));
        for (int i = 0; i < iteration; i++) {
            List<Note> duplicateNotes = addDuplicateNotes((int) chordSize, notes);
            duplicateNotes.forEach(note -> note.setPosition(note.getPosition() + duration));
            Collections.shuffle(duplicateNotes);
            generateChordOrchestra(duplicateNotes);
        }
    }

    public void generateChordOrchestra(List<Note> notes){
        int k = 0;
        for (OrchestraMapping orchestraMapping: orchestraConfig.getOrchestralMappings()) {
            OrchestralQuality orchestralQuality = orchestraMapping.getOrchestralQuality();
            Instrument basicInstrument = null;
            if (orchestralQuality != null) {
                basicInstrument = orchestralQuality.getBasicInstrument(orchestraMapping.getInstrument().getInstrumentName());
            }
            for (int i = 0; i < orchestraMapping.getSize(); i++) {
                Note note = notes.get(k).clone();
                if (orchestralQuality != null) {
                    basicInstrument.updateNoteInRange(note);
                }
                if (!orchestraMapping.getNotes().contains(note)) {//Same note will not be added for same instrument -> chord size will be smaller
                    orchestraMapping.addNote(note);
                }
                k++;
            }
        }
    }

    private List<Note> addDuplicateNotes(int noteSize, List<Note> notes) {
        List<Note> notesDuplicates = new ArrayList<>(notes);
        int size = noteSize - notes.size();
        for (int i = 0; i < size; i++) {
            Note randomNote = RandomUtil.getRandomFromList(notes).clone();
            notesDuplicates.add(randomNote);
        }
        return notesDuplicates;
    }

}
