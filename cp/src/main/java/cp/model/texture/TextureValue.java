package cp.model.texture;

import cp.model.note.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class TextureValue {

    private Map<Integer, List<Note>> textureNotesPerLine = new HashMap<>();

    public void addTextureNotes(int line, Note note){
        textureNotesPerLine.compute(line, (integer, textureNotes) -> {
            if (textureNotes == null) {
                List<Note> notes = new ArrayList<>();
                notes.add(note);
                return notes;
            }else {
                textureNotes.add(note);
                return textureNotes;
            }
        });
    }

    public Map<Integer, List<Note>> getTextureNotesPerLine() {
        return textureNotesPerLine;
    }

    public List<Note> getAllTextureNotes(){
        return textureNotesPerLine.values().stream().flatMap(notes -> notes.stream()).collect(toList());
    }

}
