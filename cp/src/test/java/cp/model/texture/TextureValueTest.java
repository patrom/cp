package cp.model.texture;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class TextureValueTest {

    @Test
    public void addTextureNotes() {
        TextureValue textureValue = new TextureValue();

        textureValue.addTextureNotes(0, NoteBuilder.note().pc(0).build());
        textureValue.addTextureNotes(1, NoteBuilder.note().pc(2).build());

        Map<Integer, List<Note>> textureNotesPerLine = textureValue.getTextureNotesPerLine();
        assertEquals(1 ,textureNotesPerLine.get(0).size());
        assertEquals(1 ,textureNotesPerLine.get(1).size());
    }

    @Test
    public void getAllTextureNotes() {
        TextureValue textureValue = new TextureValue();

        textureValue.addTextureNotes(0, NoteBuilder.note().pc(0).build());
        textureValue.addTextureNotes(0, NoteBuilder.note().pc(2).build());

        List<Note> allTextureNotes = textureValue.getAllTextureNotes();
        assertEquals(2 ,allTextureNotes.size());
    }

}