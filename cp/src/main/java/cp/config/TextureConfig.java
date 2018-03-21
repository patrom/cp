package cp.config;

import cp.composition.Composition;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.harmony.VoicingType;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import cp.util.RowMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by prombouts on 4/06/2017.
 */
@Component
public class TextureConfig {

    private Map<Integer, List<DependantHarmony>> textureTypes = new HashMap<>();
    @Autowired
    private Composition composition;

    @PostConstruct
    public void ini(){
        //chords
        List<DependantHarmony> chordTypes = new ArrayList<>();
		chordTypes.add(createDependantHarmony(ChordType.MAJOR));//major and minor
		chordTypes.add(createDependantHarmony(ChordType.MAJOR_CHR));
		chordTypes.add(createDependantHarmony(ChordType.MAJOR_1));//major and minor
		chordTypes.add(createDependantHarmony(ChordType.MAJOR_1_CHR));
        chordTypes.add(createDependantHarmony(ChordType.MAJOR_2));//major and minor
        chordTypes.add(createDependantHarmony(ChordType.MAJOR_2_CHR));
        chordTypes.add(createDependantHarmony(ChordType.MINOR_CHR));
        chordTypes.add(createDependantHarmony(ChordType.MINOR_1_CHR));
        chordTypes.add(createDependantHarmony(ChordType.MINOR_2_CHR));

        chordTypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_OCTAVE));
        chordTypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_OCTAVE));
        chordTypes.add(createDependantHarmony(ChordType.CH2_KWART_OCTAVE));
        chordTypes.add(createDependantHarmony(ChordType.CH2_KWINT_OCTAVE));

//        chordTypes.add(createDependantHarmony(ChordType.DOM));
//        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_1));
//        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_2));

//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));


        List<DependantHarmony> intervaltypes = new ArrayList<>();
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWART));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWINT));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE)); == kwart diatonic
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_TERTS_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_OCTAVE));

        List<DependantHarmony> intervalOctavetypes = new ArrayList<>();
        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_OCTAVE));
        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_OCTAVE));
        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_KWART_OCTAVE));
        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_KWINT_OCTAVE));
        intervalOctavetypes.add(createDependantHarmony(ChordType.NO_INTERVALS));

//        textureTypes.put(1, intervalOctavetypes);
//        textureTypes.put(2, chordTypes);
//        textureTypes.put(1, chordTypes);

        List<DependantHarmony> octaveDoubling = new ArrayList<>();
        octaveDoubling.add(createDependantHarmony(ChordType.CH2_OCTAVE));
//        textureTypes.put(0, octaveDoubling);
//        textureTypes.put(3, octaveDoubling);

        List<DependantHarmony> symmetryChords = new ArrayList<>();
        symmetryChords.add(createDependantHarmony(ChordType.SYMMEETRY, composition.axisHigh,composition.axisLow));
        symmetryChords.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        textureTypes.put(0, symmetryChords);
//        textureTypes.put(1, symmetryChords);
//
        List<DependantHarmony> symmetryChords2 = new ArrayList<>();
        symmetryChords2.add(createDependantHarmony(ChordType.SYMMEETRY, composition.axisHigh,composition.axisLow));
//        symmetryChords2.add(createDependantHarmony(ChordType.SYMMEETRY, composition.axisHigh,composition.axisLow));
//        symmetryChords2.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        textureTypes.put(2, symmetryChords2);

        TnTnIType tnTnIType = new TnTnIType();
        Set set3_1 = tnTnIType.getPrimeByName("3-1");
        List<DependantHarmony> setClassTypes = new ArrayList<>();
        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.CLOSE));
        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_2));
        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_3));
//        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_2_4));
        textureTypes.put(3, setClassTypes);
    }

    public List<DependantHarmony> rowMatrix(int[] setClass, VoicingType voicingType) {
        List<DependantHarmony> setClassTypes = new ArrayList<>();

        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(Collectors.toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);
        rowMatrix.show();

        int[] row = rowMatrix.getRow(setClass.length - 1);
        DependantHarmony dependantHarmony = new DependantHarmony(row, voicingType);
        dependantHarmony.setChordType(ChordType.SETCLASS);
        setClassTypes.add(dependantHarmony);
        System.out.println(Arrays.toString(row));

        int[] column = rowMatrix.getColumn(0);
        DependantHarmony dependantHarmonyInversion = new DependantHarmony(column, voicingType);
        dependantHarmonyInversion.setChordType(ChordType.SETCLASS);
        setClassTypes.add(dependantHarmonyInversion);
        System.out.println(Arrays.toString(column));
        return setClassTypes;
    }

    private DependantHarmony createDependantHarmony(ChordType chordType){
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(chordType);
        return  dependantHarmony;
    }

    private DependantHarmony createDependantHarmony(int[] setClass, VoicingType voicingType){
        DependantHarmony dependantHarmony = new DependantHarmony(setClass, voicingType);
        dependantHarmony.setChordType(ChordType.SETCLASS);
        return dependantHarmony;
    }

    private DependantHarmony createDependantHarmony(ChordType chordType, int axisPitchClassHigh, int axisPitchClassLow){
        return new DependantHarmony(chordType, axisPitchClassHigh, axisPitchClassLow);
    }

    public List<DependantHarmony> getTextureFor(int voice){
        return textureTypes.get(voice);
    }

    public boolean hasTexture(int voice){
        return textureTypes.containsKey(voice);
    }
}
