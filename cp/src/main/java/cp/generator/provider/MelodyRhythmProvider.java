package cp.generator.provider;

import cp.config.InstrumentConfig;
import cp.config.VoiceConfig;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.musicxml.XMLParser;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.nsga.operator.mutation.MutationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

@Component(value = "melodyRhythmProvider")
public class MelodyRhythmProvider extends AbstractProvidder implements MelodyProvider {

    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    public MelodyGeneratorProvider melodyGeneratorProvider;

    public List<CpMelody> getMelodies(int voice){
        if(melodies.isEmpty()){
//            try {
//                parse(voice);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (XMLStreamException e) {
//                e.printStackTrace();
//            }
            melodies.addAll(melodyGeneratorProvider.getMelodies(voice));
            melodies.forEach(melody -> melody.setMutationType(MutationType.PITCH));
//            getRhythmMelodies();
//            melodies.add(getRest(0, DurationConstants.EIGHT));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
//            final BeatGroupTwo beatGroupTwo = new BeatGroupTwo(DurationConstants.QUARTER);
//            CpMelody cpMelody = melodyGeneratorProvider.generateMelodyConfig(0);
//            melodies.add(cpMelody);
        }
        return melodies;
    }

    protected void parse(int voice) throws IOException, XMLStreamException {
        final Resource resource = new FileSystemResource("src/main/resources/parser");
        File dir = resource.getFile();
        for (File xmlFile : dir.listFiles()) {
            XMLParser xmlParser = new XMLParser();
            xmlParser.setInstrumentConfig(instrumentConfig);
            xmlParser.startParsing(xmlFile.getPath());
            ComplexElement partList = xmlParser.getScore().getPartList();
            xmlParser.setInstrumentNames(partList);
            ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
            xmlParser.traverse(body);
            Map<String, List<Note>> notesPerInstrument = xmlParser.getNotesPerInstrument();
            Collection<List<Note>> values = notesPerInstrument.values();
            for (List<Note> notes : notesPerInstrument.values()) {
//                final List<Note> melodyNotes = voiceConfig.getRandomPitchClassGenerator(voice).updatePitchClasses(notes);
                final List<Note> melodyNotes = notes;
                Note lastNote = melodyNotes.get(melodyNotes.size() - 1);
                int duration = lastNote.getPosition() + lastNote.getLength();
                CpMelody melody = new CpMelody(melodyNotes, 0, 0, duration);
//                melody.setBeatGroup(new BeatGroupTwo(duration/2));//beatgroup 2 or 3 -> time!!
//                melody.setBeatGroup(new BeatGroupThree(duration/3));//beatgroup 2 or 3 -> time!!
                melody.setNotesSize((int) melodyNotes.stream().filter(n -> !n.isRest()).count());
                melody.setMutationType(MutationType.PITCH);
//                melody.setTimeLineKey(new TimeLineKey(keys.Eflat, Scale.MAJOR_SCALE));
//                melody.convertToTimelineKey(timeLine);
                melodies.add(melody);
            }
        }
    }

    private List<CpMelody> getRhythmMelodies(){
        int voice0 = 0;
        TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(0).len(DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
        melody.setMutationType(MutationType.PITCH);
//        melody.setTonality(Tonality.ATONAL);
//        melody.setTimeLineKey(timeLineKey);
        melodies.add(melody);
        //variation1
        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.EIGHT).pc(0).len(DurationConstants.EIGHT ).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT ));
        melody.setNotesSize(1);
//        melody.setTimeLineKey(timeLineKey);
        melody.setMutationType(MutationType.PITCH);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(0).len(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
        melody.setMutationType(MutationType.PITCH);
//        melody.setTimeLineKey(timeLineKey);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);


        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.HALF).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
        melody.setMutationType(MutationType.PITCH);
//        melody.setTimeLineKey(timeLineKey);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);
//        //variation2
//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).build());
//        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(1).len(DurationConstants.SIXTEENTH).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(5).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(4).len(DurationConstants.EIGHT).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER));
//        melody.setNotesSize(4);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER * 2).pc(3).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER * 3).pc(2).len(DurationConstants.QUARTER * 3).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER * 6);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER * 2));
//        melody.setNotesSize(4);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.EIGHT));
//        melody.setNotesSize(1);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

        return melodies;
    }
}
