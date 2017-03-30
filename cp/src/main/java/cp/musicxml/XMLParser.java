/*
 * The main Parser for MusicXML files
 */


package cp.musicxml;

import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.musicxml.parsed.ElementWrapper;
import cp.musicxml.parsed.Score;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private XMLStreamReader2 xmlStreamReader;
    private Score score = null;
    private List<Note> notes = new ArrayList<>();
    private int bpm;

    private ParseXMLHeader parseHeaderObj;
    private ParseXMLBody   parseBodyObj;

    public static void main(String[] args) {
        XMLParser xmlParser = new XMLParser();
        try {
            xmlParser.startParsing("cp/src/main/resources/test.xml");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
        xmlParser.traverse(body);
        List<Note> notes = xmlParser.getNotes();
        for (Note note : notes) {
            System.out.println(note);
            System.out.println(note.getDynamicLevel());
        }
        System.out.println(xmlParser.getBpm());


    }

    private void traverse(ArrayList<ElementWrapper> body) {
        int position = 0;
        Dynamic dynamic = null;
        for (ElementWrapper element : body) {
            if (element.getIsComplex()){
                ArrayList<ElementWrapper> elements = element.getComplexElement().getElements();
                if(element.getComplexElement().getElementName().equals("direction")){
                    ArrayList<ElementWrapper> directionElements = element.getComplexElement().getElements();
                    for (ElementWrapper directionElement : directionElements) {
                        if(directionElement.getIsComplex() && directionElement.getComplexElement().getElementName().equals("direction-type")){
                            ArrayList<ElementWrapper> directionTypeElements = directionElement.getComplexElement().getElements();
                            for (ElementWrapper directionTypeElement : directionTypeElements) {
                                if(directionTypeElement.getComplexElement().getElementName().equals("dynamics")){
                                    dynamic = Dynamic.valueOf(directionTypeElement.getComplexElement().getElements().get(0).getElement().getElementName().toUpperCase());
                                }
                            }
                        }
                    }
                }
                if (element.getComplexElement().getElementName().equals("note")){
                    NoteParser noteParser = new NoteParser(position);
                    Note note = noteParser.parseNote(element.getComplexElement().getElements());
                    position = position + note.getLength();
                    if (dynamic != null) {
                        note.setDynamic(dynamic);
                        note.setDynamicLevel(dynamic.getLevel());
                    }
                    dynamic = null;
                    notes.add(note);
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


    // GETTERS
    public Score getScore(){
        return score;
    }

    public List<Note> getNotes() {
        return notes;
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