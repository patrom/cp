package cp.musicxml;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import cp.musicxml.parsed.Attribute;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.Element;
import cp.musicxml.parsed.ElementWrapper;
import cp.out.instrument.Articulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 29/03/2017.
 */
public class NoteParser {

    private int pc = 0;
    private int octave = 0;
    private int alter = 0;
    private String type = "";
    private boolean dot = false;
    private Note note = new Note();
    private String instrumentName;
    private ComplexElement elementTimeModification;
    private List<Element> beams = new ArrayList<>();
    private boolean isRest;
    private boolean chord = false;

    public Note parseNote(ArrayList<ElementWrapper> elements) {
        for (ElementWrapper element : elements) {
            if (element.getIsComplex()){
                if(element.getComplexElement().getElementName().equals("articulations")){
                    Articulation articulation = Articulation.getArticulation(element.getComplexElement().getElements().get(0).getElement().getElementName());
                    note.setArticulation(articulation);
                    setDynamicLevel(articulation);
                }
                if(element.getComplexElement().getElementName().equals("time-modification")){
                    elementTimeModification = element.getComplexElement();
                }
                if(element.getComplexElement().getElementName().equals("rest")){
                    isRest = true;
                }
                ArrayList<ElementWrapper> elementWrappers = element.getComplexElement().getElements();
                parseNote(elementWrappers);
            }else{
                switch (element.getElement().getElementName()){
                    case "step":
                        pc = getPitchFromStep(element.getElement().getData());
                        break;
                    case "voice":
                        note.setVoice(Integer.parseInt(element.getElement().getData()));
                        break;
                    case "octave":
                        octave = Integer.parseInt(element.getElement().getData()) + 1;
                        break;
                    case "alter":
                        alter = Integer.parseInt(element.getElement().getData());
                        break;
                    case "type":
                        type = element.getElement().getData();
                        break;
                    case "dot":
                        dot = true;
                        break;
                    case "instrument":
                        instrumentName = element.getElement().getAttributes().get(0).getAttributeText();
                        break;
                    case "tied":
                        String tied =  element.getElement().getAttributes().get(0).getAttributeText();
                        if ("start".equals(tied)) {
                            note.setTieStart(true);
                        }
                        if ("stop".equals(tied)) {
                            note.setTieEnd(true);
                        }
                        break;
                    case "tuple":
                        ArrayList<Attribute> attributes = element.getElement().getAttributes();
                        for (Attribute attribute : attributes) {
                            if(attribute.getAttributeName().equals("type")){
                                String type = attribute.getAttributeText();
                                if ("start".equals(type)) {
                                    note.setTupletType(TupletType.START);
                                }
                                if ("stop".equals(type)) {
                                    note.setTupletType(TupletType.STOP);
                                }
                            }
                            if(attribute.getAttributeName().equals("bracket")){
                                String bracket = attribute.getAttributeText();
                                if ("yes".equals(bracket)) {
                                    note.setBracket(true);
                                }
                            }
                        }
                        break;
                    case "beam":
                        beams.add(element.getElement());
                        break;
                    case "chord":
                        chord = true;
                        break;
                }
            }
        }
        if (isRest) {
           note.setPitch(Note.REST);
        }else{
            int pitchClass = (pc + alter + 12) % 12;
            note.setPitchClass(pitchClass );
            note.setOctave(octave);
            note.setPitch(pc + alter + 12 * octave);
        }
        int duration = getDuration(type, dot, elementTimeModification);
        note.setLength(duration);
        note.setDisplayLength(duration);
        note.setInstrument(instrumentName);
        note.setChord(chord);
        setBeanType();
        return note;
    }

    private void setDynamicLevel(Articulation articulation) {
        int oldLevel = note.getDynamicLevel();
        switch (articulation){
            case MARCATO:
                note.setDynamicLevel(oldLevel + (oldLevel * 35)/100);
                break;
            case STRONG_ACCENT:
                note.setDynamicLevel(oldLevel + (oldLevel * 20)/100);
                break;
            case TENUTO:
                note.setDynamicLevel(oldLevel + (oldLevel * 10)/100);
                break;
        }
    }

    private int getDuration(String type, boolean hasDot, ComplexElement elementTimeModifiction){
        int duration = 0;
        if(elementTimeModifiction == null){
            switch (type) {
                case "16th":
                    duration = DurationConstants.SIXTEENTH;
                    if(hasDot){
                        //TODO
                    }
                    break;
                case "eighth":
                    duration = DurationConstants.EIGHT;
                    if(hasDot){
                        duration = DurationConstants.EIGHT + DurationConstants.SIXTEENTH;
                    }
                    break;
                case "quarter":
                    duration = DurationConstants.QUARTER;
                    if(hasDot){
                        duration = DurationConstants.QUARTER + DurationConstants.EIGHT;
                    }
                    break;
                case "half":
                    duration = DurationConstants.HALF;
                    if(hasDot){
                        duration = DurationConstants.HALF + DurationConstants.QUARTER;
                    }
                    break;
                case "whole":
                    duration = DurationConstants.WHOLE;
                    if(hasDot){
                        duration = DurationConstants.WHOLE + DurationConstants.HALF;
                    }
                    break;
                default:
                    break;
            }
        }else{
            if(elementTimeModifiction != null){
                Element actualNotes = elementTimeModifiction.getElements().get(0).getElement();
                Element normalType = elementTimeModifiction.getElements().get(2).getElement();
                if(actualNotes.getElementName().equals("actual-notes") && actualNotes.getData().equals("3")){
                    note.setTriplet(true);
                    note.setTimeModification(normalType.getData());
                    switch (type) {
                        case "16th":
                            duration = DurationConstants.SIXTEENTH_TRIPLET;
                            break;
                        case "eighth":
                            duration = DurationConstants.EIGHT_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
                            }
                            break;
                        case "quarter":
                            duration = DurationConstants.QUARTER_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
                            }
                            break;
                        case "half":
                            duration = DurationConstants.HALF_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
                            }
                            break;
                        default:
                            break;
                    }
                }
                if(actualNotes.getElementName().equals("actual-notes") && actualNotes.getData().equals("6")){
                    note.setSextuplet(true);
                    note.setTimeModification(normalType.getData());
                    switch (type) {
                        case "16th":
                            duration = DurationConstants.SIXTEENTH_TRIPLET;
                            break;
                        case "eighth":
                            duration = DurationConstants.EIGHT_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
                            }
                            break;
                        case "quarter":
                            duration = DurationConstants.QUARTER_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
                            }
                            break;
                        case "half":
                            duration = DurationConstants.HALF_TRIPLET;
                            if(hasDot){
                                duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
                            }
                            break;
                        default:
                            break;
                    }
                }
                if(actualNotes.getElementName().equals("actual-notes") && actualNotes.getData().equals("5")){
                    note.setQuintuplet(true);
                    note.setTimeModification(normalType.getData());
                    switch (type) {
                        case "16th":
                            duration = DurationConstants.SIXTEENTH_QUINTUPLET;
                            break;
                        case "eighth":
                            duration = DurationConstants.EIGHT_QUINTUPLET;
                            if(hasDot){
                                duration = DurationConstants.EIGHT_QUINTUPLET + DurationConstants.SIXTEENTH_QUINTUPLET;
                            }
                            break;
                        default:
                            break;
                    }
                }
                if(actualNotes.getElementName().equals("actual-notes") && actualNotes.getData().equals("7")){
                    note.setSepttuplet(true);
                    note.setTimeModification(normalType.getData());
                    switch (type) {
                        case "16th":
                            duration = DurationConstants.SIXTEENTH_SEPTUPLET;
                            break;
//                        case "eighth":
//                            duration = DurationConstants.EIGHT_QUINTUPLET;
//                            if(hasDot){
//                                duration = DurationConstants.EIGHT_QUINTUPLET + DurationConstants.SIXTEENTH_QUINTUPLET;
//                            }
//                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return duration;
    }

    private void setBeanType(){
        if (!beams.isEmpty()) {
            Element firstBeam = beams.get(0);
            if(firstBeam.getAttributes().get(0).getAttributeText().equals("1")) {
                if( "begin".equals(firstBeam.getData())){
                    note.setBeamType(BeamType.BEGIN);
                }else if("continue".equals(firstBeam.getData())){
                    note.setBeamType(BeamType.CONTINUE);
                }else if("end".equals(firstBeam.getData())){
                    note.setBeamType(BeamType.END);
                }
            }
            if (beams.size() > 1) {
                Element secondBeam = beams.get(1);
                if(secondBeam != null && secondBeam.getAttributes().get(0).getAttributeText().equals("2")){
                    if("begin".equals(firstBeam.getData()) && "begin".equals(secondBeam.getData())){
                        note.setBeamType(BeamType.BEGIN_BEGIN);
                    }else if("continue".equals(firstBeam.getData()) && "continue".equals(secondBeam.getData())){
                        note.setBeamType(BeamType.CONTINUE_CONTINUE);
                    }else if("end".equals(firstBeam.getData()) && "end".equals(secondBeam.getData())){
                        note.setBeamType(BeamType.END_END);
                    }
                    else if("continue".equals(firstBeam.getData()) && "begin".equals(secondBeam.getData())){
                        note.setBeamType(BeamType.CONTINUE_BEGIN);
                    }
                    else if("continue".equals(secondBeam.getData()) && "end".equals(secondBeam.getData())){
                        note.setBeamType(BeamType.CONTINUE_END);
                    }
                }
            }
        }
    }

    private int getPitchFromStep(String step) {
        int pitch = 0;
        switch (step) {
            case "C":
                pitch = 0;
                break;
            case "D":
                pitch = 2;
                break;
            case "E":
                pitch = 4;
                break;
            case "F":
                pitch = 5;
                break;
            case "G":
                pitch = 7;
                break;
            case "A":
                pitch = 9;
                break;
            case "B":
                pitch = 11;
                break;
            default:
                break;
        }
        return pitch;
    }

}
