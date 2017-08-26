/*
 * The main Parser for MusicXML files
 */


package cp.musicxml;

import cp.config.InstrumentConfig;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.musicxml.parsed.Score;
import cp.out.instrument.Technical;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class XMLParser {

    private Map<String, List<Note>> notesPerInstrument = new HashMap<>();

    private XMLStreamReader2 xmlStreamReader;
    private Score score = null;
    private int bpm;
    private int position = 0;
    Technical words = null;

    private ParseXMLHeader parseHeaderObj;
    private ParseXMLBody   parseBodyObj;
    private InstrumentConfig instrumentConfig;

    public void setInstrumentConfig(InstrumentConfig instrumentConfig) {
        this.instrumentConfig = instrumentConfig;
    }

    public void traverse(ArrayList<ElementWrapper> elementWrappers) {
        Dynamic dynamic = null;
        for (ElementWrapper element : elementWrappers) {
            if (element.getIsComplex()){
                ArrayList<ElementWrapper> elements = element.getComplexElement().getElements();
                if(element.getComplexElement().getElementName().equals("direction")){
                    ArrayList<ElementWrapper> directionElements = element.getComplexElement().getElements();
                    for (ElementWrapper directionElement : directionElements) {
                        if(directionElement.getIsComplex() && directionElement.getComplexElement().getElementName().equals("direction-type")){
                            ArrayList<ElementWrapper> directionTypeElements = directionElement.getComplexElement().getElements();
                            for (ElementWrapper directionTypeElement : directionTypeElements) {
                                if(directionTypeElement.getIsComplex() && directionTypeElement.getComplexElement().getElementName().equals("dynamics")){
                                    dynamic = Dynamic.valueOf(directionTypeElement.getComplexElement().getElements().get(0).getElement().getElementName().toUpperCase());
                                }
                                if(!directionTypeElement.getIsComplex() && directionTypeElement.getElement().getElementName().equals("words")){
                                    words = Technical.getTechnical(directionTypeElement.getElement().getData());
                                }
                            }
                        }
                    }
                }
                if (element.getComplexElement().getElementName().equals("note")){
                    NoteParser noteParser = new NoteParser();
                    Note note = noteParser.parseNote(element.getComplexElement().getElements());
                    List<Note> notes = notesPerInstrument.get(note.getInstrument());
                    if (notes != null && !notes.isEmpty()) {
                        Collections.sort(notes);
                        Note lastNote = notes.get(notes.size() - 1);
                        if (note.isChord()) {
                            note.setPosition(lastNote.getPosition());
                        } else {
                            note.setPosition(lastNote.getPosition() + lastNote.getLength());
                        }
                    } else {
                        note.setPosition(0);
                    }
                    if (dynamic != null) {
                        note.setDynamic(dynamic);
                        note.setDynamicLevel(dynamic.getLevel());
                    }
                    if (words != null){
                        note.setTechnical(words);
                    }
                    dynamic = null;
                    notesPerInstrument.compute(note.getInstrument(), (k, v) -> {
                                if (v == null) {
                                    List<Note> m = new ArrayList<>();
                                    m.add(note);
                                    return m;
                                }else {
                                    v.add(note);
                                    return v;
                                }
                            }
                    );
                }else {
                    traverse(elements);
                }
            }else{
                switch (element.getElement().getElementName()){
                    case "divisions":
//                        System.out.println(element.getElement().getData());
                        break;
                    case "per-minute":
                        bpm = Integer.parseInt(element.getElement().getData());
                        break;
                }
            }
        }
    }

    public void setInstrumentNames(ComplexElement partList){
        ArrayList<ElementWrapper> elements = partList.getElements();
        for (ElementWrapper scorePartElement : elements) {
            if(scorePartElement.getIsComplex() && scorePartElement.getComplexElement().getElementName().equals("score-part")){
                ArrayList<ElementWrapper> scoreElements = scorePartElement.getComplexElement().getElements();
                for (ElementWrapper scoreElement : scoreElements) {
                    if(scoreElement.getIsComplex() && scoreElement.getComplexElement().getElementName().equals("score-instrument")){
                        if (instrumentConfig != null){
                            String id = scoreElement.getComplexElement().getAttributes().get(0).getAttributeText();
                            notesPerInstrument.put(id, new ArrayList<>());
                        }
                    }
                }
            }
        }
    }


    // GETTERS
    public Score getScore(){
        return score;
    }

    public Map<String,List<Note>> getNotesPerInstrument() {
        return notesPerInstrument;
    }

    public int getBpm() {
        return bpm;
    }

    // Start Parsing
    public void startParsing(String path) throws XMLStreamException, IOException {
        // load the xml file
        final Resource resource = new FileSystemResource(path);
        InputStream inputStream = resource.getInputStream();
        //InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory2.SUPPORT_DTD, false);   //do not read web based DTD, causes error

        xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(inputStream);

        parseHeaderObj = new ParseXMLHeader(xmlStreamReader);
        parseBodyObj = new ParseXMLBody(xmlStreamReader);
        getElements(xmlStreamReader, () -> scoreStart(), () -> scoreEnd());
        // If preferred the above line can be written in the method reference style instead of the lambda style
        // getElements(xmlStreamReader, this::scoreStart, this::scoreEnd);
    }


    /*
     * How to pass methods as arguments in Java
     * http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
     * http://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
     * http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
     * http://stackoverflow.com/questions/4685435/what-is-the-cloest-thing-to-a-function-pointer-in-java
     */
    public static void getElements(XMLStreamReader2 xmlStreamReader, Interface startMethod, Interface endMethod) {
        try {
           wLoop: while(xmlStreamReader.hasNext()){
                int eventType = xmlStreamReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        //System.out.print("<" + xmlStreamReader.getName().toString() + ">");
                        if (startMethod.interfaceMethod()) {
                            break wLoop;
                        }
                        break;
                    /* Parse characters
                    case XMLEvent.CHARACTERS:
                        //System.out.print(xmlStreamReader.getText());
                        charMethod.interfaceMethod();
                        break;
                    */
                    case XMLEvent.END_ELEMENT:
                        //System.out.println("</"+xmlStreamReader.getName().toString()+">");
                        if (endMethod.interfaceMethod()) {
                            break wLoop;
                        }
                        break;

                    default:
                        //do nothing
                        break;
                }
            }
        } catch (XMLStreamException e) {
            //e.printStackTrace();
            System.out.println("ERROR: Stream Exception - Streaming file error");
        }
    }


    // Starts parsing the score
    private Boolean scoreStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PARTWISE)) {
            // create part-wise score object
            score = new Score(XMLConsts.PARTWISE);
            if (xmlStreamReader.getAttributeCount() == 1) { // only has one option attribute - version
                score.setScoreVersion(xmlStreamReader.getAttributeValue(0));
            }
            parseHeaderObj.setScore(score);
            parseBodyObj.setScore(score);
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_TIMEWISE)) {
            // create time-wise score object
            score = new Score(XMLConsts.TIMEWISE);
            if (xmlStreamReader.getAttributeCount() == 1) { // only has one option attribute - version
                score.setScoreVersion(xmlStreamReader.getAttributeValue(0));
            }
            parseHeaderObj.setScore(score);
            parseBodyObj.setScore(score);
        }

        // check validity of XML
        if (score == null) {
            System.out.println("ERROR: Not a MusicXML File");
            return true;
        }

        parseHeaderObj.parseHeader();
        parseBodyObj.parseBody();
        return false;
    }

    private Boolean scoreEnd() {
        return false;
    }



}