package cp.out.orchestration;

import cp.composition.Composition;
import cp.composition.voice.MelodyVoice;
import cp.composition.voice.VoiceConfig;
import cp.generator.MelodyGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentConfig;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 7/01/2017.
 */
@Component
public class HarmonyOrchestrator {

    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private MelodyGenerator melodyGenerator;
    @Autowired
    private TimeLine timeLine;
    @Autowired
    private Composition composition;
    @Autowired
    private MelodyVoice melodyVoice;

    public MelodyBlock varyNextHarmonyNote(Motive motive, int voiceSource, int voiceTarget, Predicate<Note> harmonyFilter){
        Instrument instrument = instrumentConfig.getInstrumentForVoice(voiceSource);
        int start = motive.getHarmonies().get(0).getPosition();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voiceTarget, instrument.pickRandomOctaveFromRange(), start, composition.getEnd());


        List<Note> melodyBlockNotes = melodyBlock.getMelodyBlockNotes();
        int counter = 0;
        for (Note note : melodyBlockNotes) {
            Note harmonyNote = motive.getNextHarmonyNoteForPosition(note.getPosition(), harmonyFilter, counter);
            note.setPitchClass(harmonyNote.getPitchClass());
            counter++;
        }
        melodyBlock.updatePitchesFromContour(timeLine);
//        melodyBlock.updateMelodyBetween();
        instrument.updateMelodyInRange(melodyBlock.getMelodyBlockNotes());
        return  melodyBlock;
    }

    public List<MelodyBlock> varyHarmonyRhythmDependant(Motive motive, int voiceSource, int voiceTarget, Predicate<Note> harmonyFilter, int harmonySize){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        Instrument instrument = instrumentConfig.getInstrumentForVoice(voiceSource);
        int start = motive.getHarmonies().get(0).getPosition();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voiceTarget, instrument.pickRandomOctaveFromRange(), start, composition.getEnd());

        List<Note> melodyBlockNotes = melodyBlock.getMelodyBlockNotes();
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < melodyBlockNotes.size(); i++) {
            Note note = melodyBlockNotes.get(i);
            int randomSize = RandomUtil.getRandomNumberInRange(1, harmonySize);
            List<Note> harmonyNotes = motive.getHarmonyNotesPosition(note.getPosition(), randomSize, harmonyFilter);
            if(!harmonyNotes.isEmpty()){
                note.setPitchClass(harmonyNotes.get(0).getPitchClass());
                for (int j = 1; j < harmonyNotes.size(); j++) {
                    Note clonedNote =  note.clone();
                    clonedNote.setVoice(voiceTarget + j);
                    clonedNote.setPitchClass(harmonyNotes.get(j).getPitchClass());
                    notes.add(clonedNote);
                }
            }
        }

        melodyBlock.updatePitchesFromContour(timeLine);
//        melodyBlock.updateMelodyBetween();
        instrument.updateMelodyInRange(melodyBlock.getMelodyBlockNotes());
        melodyBlocks.add(melodyBlock);

        Map<Integer, List<Note>> notesPerVoice = notes.stream().collect(groupingBy(Note::getVoice, TreeMap::new, Collectors.toList()));
        for (Map.Entry<Integer,List<Note>> voiceEntry : notesPerVoice.entrySet()) {
            instrument = instrumentConfig.getInstrumentForVoice(voiceSource);
            CpMelody melody = new CpMelody(voiceEntry.getValue(),voiceEntry.getKey(), composition.getStart(), composition.getEnd());
            MelodyBlock block = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voiceEntry.getKey());
            block.addMelodyBlock(melody);
            block.updatePitchesFromContour(timeLine);
//            block.updateMelodyBetween();
            instrument.updateMelodyInRange(block.getMelodyBlockNotes());
            melodyBlocks.add(block);
        }

        return  melodyBlocks;
    }

    public MelodyBlock varyOriginalNote(Motive motive, int voiceSource, int voiceTarget) {
        MelodyBlock melodyBlock = motive.getMelodyBlock(voiceSource);
        Instrument instrument = instrumentConfig.getInstrumentForVoice(voiceSource);

        int start = melodyBlock.getMelodyBlocks().get(0).getStart();
        MelodyBlock generatedMelodyBlock = melodyGenerator.generateMelodyBlockConfig(voiceTarget, instrument.pickRandomOctaveFromRange(), start, composition.getEnd());

        List<Note> melodyBlockNotes = generatedMelodyBlock.getMelodyBlockNotes();
        for (int i = 0; i < melodyBlockNotes.size(); i++) {
            Note note = melodyBlockNotes.get(i);
            Note noteToCopy = melodyBlock.getNoteAtPosition(note.getPosition());
            note.setPitchClass(noteToCopy.getPitchClass());
            note.setPitch(noteToCopy.getPitch());
            note.setOctave(noteToCopy.getOctave());
        }

        generatedMelodyBlock.updatePitchesFromContour(timeLine);
//        generatedMelodyBlock.updateMelodyBetween();
        instrument.updateMelodyInRange(generatedMelodyBlock.getMelodyBlockNotes());
        return generatedMelodyBlock;
    }

    public MelodyBlock getChordsRhythmDependant(Motive motive, int voiceTarget, VoiceConfig voiceConfig,  Predicate<Note> harmonyFilter, int harmonySize){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        Instrument instrument = instrumentConfig.getInstrumentForVoice(voiceTarget);
        int start = motive.getHarmonies().get(0).getPosition();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voiceTarget, voiceConfig, instrument.pickRandomOctaveFromRange(), start, composition.getEnd());

        List<Note> melodyBlockNotes = melodyBlock.getMelodyBlockNotesWithRests();
        List<Note> allHarmonyNotes = new ArrayList<>();
        for (Note note : melodyBlockNotes) {
            if (note.isRest()){
                allHarmonyNotes.add(note.clone());
            }else{
//                int randomSize = RandomUtil.getRandomNumberInRange(1, harmonySize);
                List<Note> harmonyNotes = motive.getHarmonyNotesPosition(note.getPosition(), harmonySize, harmonyFilter);
                List<Note> clonedHarmonyNotes = harmonyNotes.stream().map(n ->
                {   Note clone = n.clone();
                    clone.setPosition(note.getPosition());
                    clone.setVoice(voiceTarget);
                    clone.setLength(note.getLength());
                    clone.setDisplayLength(note.getDisplayLength());
                    return clone;
                }).collect(toList());
                allHarmonyNotes.addAll(clonedHarmonyNotes);
            }
        }

        MelodyBlock dependantMelodyBlock = new MelodyBlock(0, voiceTarget);
        dependantMelodyBlock.addMelodyBlock(new CpMelody(allHarmonyNotes,voiceTarget,composition.getStart(), composition.getEnd()));
//        dependantMelodyBlock.updateMelodyBetween();
        instrument.updateMelodyInRange(dependantMelodyBlock.getMelodyBlockNotes());
        return dependantMelodyBlock;
    }

}
