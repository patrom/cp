package cp.model.texture;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.model.TimeLineKey;
import cp.model.harmony.Chord;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Scale;
import cp.model.setclass.SubSetCalculator;
import cp.model.setclass.TnTnIType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
class TextureSelectionTest {

    private TextureSelection textureSelection;

    @Autowired
    private Composition composition;
    @Autowired
    private SubSetCalculator subSetCalculator;
    @Autowired
    private TnTnIType tnTnIType;

    @BeforeEach
    public void setUp(){
       textureSelection = new TextureSelection();
    }

    @Test
    void getChordTypes() {
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getChordTypes(ChordType.CH2_GROTE_SIXT_CHR).getTextureTypes();
        for (List<DependantHarmony> value : textureTypes.values()) {
            System.out.println(value);
        }
    }

    @Test
    void getSelection() {
        List<Chord> subSets = new ArrayList<>();
        subSets.addAll(subSetCalculator.getSubSets(Scale.MAJOR_SCALE.getForteName(), "3-11"));
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getSelection(subSets).getTextureTypes();
        for (Map.Entry<Integer, List<DependantHarmony>> integerListEntry : textureTypes.entrySet()) {
            System.out.println(integerListEntry.getKey());
            System.out.println(integerListEntry.getValue());
        }
    }
}