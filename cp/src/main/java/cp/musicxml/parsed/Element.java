/*
 * A MusicXML element i.e. <'elementName' 'attributes'>'data'</'elementName'>
 */

package cp.musicxml.parsed;

import java.util.ArrayList;


public class Element {
    private String elementName;
    private ArrayList<Attribute> attributes;
    private String data;

    public Element() {
        attributes = new ArrayList<>();
    }

    // GETTERS
    public String getElementName() {
        return elementName;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public String getData() {
        return data;
    }


    // SETTERS
    public void setElementName(String elName) {
        elementName = elName;
    }

    public void setData(String aData) {
        data = aData;
    }


    // ADD TO
    public void addToAttributes(Attribute atb) {
        attributes.add(atb);
    }
}
