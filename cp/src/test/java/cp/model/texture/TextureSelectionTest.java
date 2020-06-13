package cp.model.texture;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.generator.PCGenerator;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cp.model.note.Scale.MAJOR_SCALE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
class TextureSelectionTest {

    private TextureSelection textureSelection;

    @Autowired
    private Composition composition;
    @Autowired
    private SubSetCalculator subSetCalculator;
    @Autowired
    private TnTnIType tnTnIType;
    @Autowired
    private PCGenerator pcGenerator;

    @BeforeEach
    public void setUp(){
       textureSelection = new TextureSelection();
    }

    @Test
    void getChordTypes() {
        textureSelection.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
        for (List<DependantHarmony> value : textureSelection.getTextureTypes().values()) {
            System.out.println(value);
        }
    }

    @Test
    void getSelection() {
        List<Chord> subSets = new ArrayList<>();
        subSets.addAll(subSetCalculator.getSubSets(MAJOR_SCALE.getForteName(), "3-11"));
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getSelection(subSets).getTextureTypes();
        for (Map.Entry<Integer, List<DependantHarmony>> integerListEntry : textureTypes.entrySet()) {
            System.out.println(integerListEntry.getKey());
            System.out.println(integerListEntry.getValue());
        }
    }

    @Test
    public void addIntervals(){
        textureSelection.addIntervals(Scale.MAJOR_SCALE, ChordType.CH2_GROTE_SIXT_CHR, ChordType.CH2_KLEINE_SIXT_CHR,
                ChordType.CH2_KLEINE_TERTS_CHR, ChordType.CH2_GROTE_TERTS_CHR);
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getTextureTypes();
        for (Map.Entry<Integer, List<DependantHarmony>> integerListEntry : textureTypes.entrySet()) {
            System.out.println(integerListEntry.getKey());
            List<DependantHarmony> value = integerListEntry.getValue();
            for (DependantHarmony dependantHarmony : value) {
                System.out.println(dependantHarmony.getChordType());
            }
        }
    }

    @Test
    public void addOctatonicAlternatingIntervals(){
        textureSelection.addOctatonicAlternatingIntervals();
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getTextureTypes();
        for (Map.Entry<Integer, List<DependantHarmony>> integerListEntry : textureTypes.entrySet()) {
            System.out.println(integerListEntry.getKey());
            List<DependantHarmony> value = integerListEntry.getValue();
            for (DependantHarmony dependantHarmony : value) {
                System.out.println(dependantHarmony.getChordType());
            }
        }
    }

    @Test
    public void addIntervals2(){
        textureSelection.addIntervals(pcGenerator.getPitchClasses("7-Z37", 0),
                ChordType.CH2_GROTE_SIXT_CHR, ChordType.CH2_KLEINE_SIXT_CHR);
        Map<Integer, List<DependantHarmony>> textureTypes = textureSelection.getTextureTypes();
        for (Map.Entry<Integer, List<DependantHarmony>> integerListEntry : textureTypes.entrySet()) {
            System.out.println(integerListEntry.getKey());
            List<DependantHarmony> value = integerListEntry.getValue();
            for (DependantHarmony dependantHarmony : value) {
                System.out.println(dependantHarmony.getChordType());
            }
        }
    }


}