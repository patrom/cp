package cp.model.humanize;

import cp.generator.MusicProperties;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.out.orchestration.InstrumentName;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Humanizer implements Humanize {

    @Autowired
    private MusicProperties musicProperties;

    private static final int VELOCITY = 6;
    private static final int TIMING = DurationConstants.TIMING;
    private static final int INTONATION = 2;
    private static final int ATTACK = 20;
    private static final int DURATION = DurationConstants.DURATION;//check no overlap next note possible!!!

    public void humanize(List<Note> notes, Instrument instrument){
        for (Note note : notes) {
            Humanization humanization = new Humanization();
            note.setHumanization(humanization);

            int timing = timing(instrument);
            humanization.setTiming(timing);

            if (!note.isRest()) {
                int velocity = velocity(note);
                humanization.setVelocity(velocity);

                humanization.setAttack(RandomUtil.getRandomNumberInRange(0, ATTACK));
            }
        }

        int size = notes.size() - 1;
        for (int j = 0; j < size; j++) {
            Note note = notes.get(j);
            Note nextNote = notes.get(j + 1);

//            Humanization humanization = note.getHumanization();
//            int noteLength = nextNote.getMidiPosition() - note.getMidiPosition();
//            humanization.setLengthNote(noteLength);
//            int interval = Math.abs(nextNote.getPitch() - note.getPitch());
//            humanization.setInterval(interval);
//            boolean longNote = isLongNote(note.getLength());//original length
//            humanization.setLongNote(longNote);
            Humanization humanization = note.getHumanization();
            int noteLength = note.getLength();
            int noteEndPosition = note.getMidiPosition() + note.getLength();
            if(noteEndPosition >= nextNote.getMidiPosition()){
                int lengthNextNoteBeforeEnd = noteEndPosition - nextNote.getMidiPosition();
                noteLength = noteLength - lengthNextNoteBeforeEnd;
            }
            humanization.setLengthNote(noteLength);

            int interval = Math.abs(nextNote.getPitch() - note.getPitch());
            humanization.setInterval(interval);

            boolean longNote = isLongNote(note.getLength());//original length
            humanization.setLongNote(longNote);

            int duration = duration(instrument, note.getTechnical(), noteLength, longNote, interval);
            humanization.setDuration(duration);

            if (!note.isRest()) {
                int intonation = intonation(note, instrument, interval);
                humanization.setIntonation(intonation);
            }
        }
        //humanize last note
        if (size > 0) {
            Note lastNote = notes.get(size);
            Humanization humanization = lastNote.getHumanization();
            int noteLength = lastNote.getLength() - humanization.getTiming();
            humanization.setLengthNote(noteLength);

            boolean longNote = isLongNote(lastNote.getLength());
            humanization.setLongNote(longNote);

            int duration = duration(instrument, lastNote.getTechnical(), noteLength, longNote, 0);
            humanization.setDuration(duration);

            if (!lastNote.isRest()) {
                int intonation = intonation(lastNote, instrument, 0);
                humanization.setIntonation(intonation);
            }
        }
    }

    protected int velocity(Note note) {
        if (note.getDynamic() == Dynamic.PP || note.getDynamic() == Dynamic.FF){
            return RandomUtil.getRandomNumberInRange(-(VELOCITY + 5), VELOCITY + 5);
        }
        return RandomUtil.getRandomNumberInRange(-VELOCITY, VELOCITY);
    }

    protected int timing(Instrument instrument) {
        if(instrument.getInstrumentGroup() == InstrumentGroup.BRASS){
            return RandomUtil.getRandomNumberInRange(0, TIMING + 5);
        }
        return RandomUtil.getRandomNumberInRange(-TIMING, TIMING);
        //complex rhythm? tuplets, syncopation
    }

    protected int intonation(Note note, Instrument instrument, int interval) {
        if(interval > 12 ){
            if(instrument.getInstrumentGroup() == InstrumentGroup.BRASS){
                return RandomUtil.getRandomNumberInRange(INTONATION, INTONATION + 2);
            } else {
                return RandomUtil.getRandomNumberInRange(0, INTONATION);
            }
        } else if (note.getDynamic() == Dynamic.F || note.getDynamic() == Dynamic.FF){
            if(instrument.getInstrumentGroup() == InstrumentGroup.BRASS || instrument.getInstrumentName().equals(InstrumentName.CONTRABASS.getName())){
                return RandomUtil.getRandomNumberInRange(0, INTONATION + 1);
            } else  {
                return RandomUtil.getRandomNumberInRange(0, INTONATION);
            }
        }
        return RandomUtil.getRandomNumberInRange(-INTONATION, INTONATION);
    }

    protected int duration(Instrument instrument, Technical technical, int noteLength, boolean longNote, int interval) {
        if(technical == Technical.LEGATO){
            switch (instrument.getInstrumentGroup()){
                case STRINGS:
                case ORCHESTRAL_STRINGS:
                case WOODWINDS:
                    if(interval > 12){
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, 0);
                    } else {
                        return noteLength;
                    }
                case BRASS:
                    return noteLength + RandomUtil.getRandomNumberInRange(-(interval + DURATION), -interval);
            }
        } else {
            if(longNote){
                switch (instrument.getInstrumentGroup()){
                    case STRINGS:
                    case ORCHESTRAL_STRINGS:
                        return noteLength;
                    case BRASS:
                        noteLength = (noteLength * 90)/100;
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, DURATION);
                    case WOODWINDS:
                        noteLength = (noteLength * 95)/100;
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, DURATION);
                }
            } else{
                switch (instrument.getInstrumentGroup()){
                    case STRINGS:
                    case ORCHESTRAL_STRINGS:
                        noteLength = (noteLength * 80)/100;
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, DURATION);
                    case BRASS:
                        noteLength = (noteLength * 60)/100;
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, DURATION);
                    case WOODWINDS:
                        noteLength = (noteLength * 70)/100;
                        return noteLength + RandomUtil.getRandomNumberInRange(-DURATION, DURATION);
                }
            }
        }
        return noteLength;
    }

    // Note is considerd long when quarter note is equal or longer than one second.
    protected boolean isLongNote(int noteLength){
        int tempo = musicProperties.getTempo();
        double time = tempo / (double)60;//second
        double tempoLength = time * DurationConstants.QUARTER;//quarter note
        return noteLength >= tempoLength;
    }

}
