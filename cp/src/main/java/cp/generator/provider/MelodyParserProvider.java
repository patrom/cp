package cp.generator.provider;

import cp.config.InstrumentConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
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
import java.util.List;
import java.util.Map;

@Component(value = "melodyParserProvider")
public class MelodyParserProvider extends AbstractProvidder implements MelodyProvider {

    @Autowired
    private InstrumentConfig instrumentConfig;

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    protected MelodyProvider melodyGeneratorProvider;

    @Override
    public List<CpMelody> getMelodies(int voice) {
        if(melodies.isEmpty()){
            try {
                parse();
//                melodies.add(getNote(0, DurationConstants.EIGHT));
//                melodies.add(getNote(0, DurationConstants.QUARTER));
//                melodies.add(getRest(0, DurationConstants.EIGHT));
//                melodies.add(getRest(0, DurationConstants.QUARTER));
//                melodies.add(getNote(0, DurationConstants.THREE_EIGHTS));
//                melodies.add(getNote(0, DurationConstants.QUARTER));
//                melodies.addAll(melodyGeneratorProvider.getMelodies(voice));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
//            melodies.forEach(m -> m.setTonality(Tonality.ATONAL));
        }
        return melodies;
    }

    protected void parse() throws IOException, XMLStreamException {
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
            for (List<Note> notes : notesPerInstrument.values()) {
                Note lastNote = notes.get(notes.size() - 1);
                int duration = lastNote.getPosition() + lastNote.getLength();
                CpMelody melody = new CpMelody(notes, 0, 0, duration);
//                melody.setBeatGroup(new BeatGroupTwo(duration/2));//beatgroup 2 or 3 -> time!!
//                melody.setBeatGroup(new BeatGroupThree(duration/3));//beatgroup 2 or 3 -> time!!
                melody.setNotesSize((int) notes.stream().filter(n -> !n.isRest()).count());
                melody.setMutationType(MutationType.OPERATOR);
//                melody.setTimeLineKey(new TimeLineKey(keys.Eflat, Scale.MAJOR_SCALE));
                melodies.add(melody);
            }
        }
    }
}
