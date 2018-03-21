package cp.musicxml;

import cp.model.note.Note;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by prombouts on 13/05/2017.
 */
public class XMLParserTest {

    @Test
    public void startParsing() throws Exception {
        XMLParser xmlParser = new XMLParser();
        try {
            xmlParser.startParsing("C:\\Users\\prombouts\\git\\cp\\cp\\src\\main\\resources\\rowMatrix\\tripletparsing.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComplexElement partList = xmlParser.getScore().getPartList();
        xmlParser.setInstrumentNames(partList);
        ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
        xmlParser.traverse(body);
        for (Map.Entry<String,List<Note>> entry : xmlParser.getNotesPerInstrument().entrySet()) {
            System.out.println(entry.getKey());
            List<Note> notes = entry.getValue();
            notes.forEach(System.out::println);
        }

//        List<Note> notes = xmlParser.getNotes(1);
//        for (Note note : notes) {
//            System.out.println(note);
//            System.out.println(note.getDynamicLevel());
//        }
//        System.out.println(xmlParser.getBpm());
    }

}