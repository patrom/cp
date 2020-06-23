package cp.config;

import cp.generator.PCGenerator;
import cp.model.harmony.Chord;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.harmony.VoicingType;
import cp.model.note.Scale;
import cp.model.setclass.SubSetCalculator;
import cp.model.texture.TextureSelection;
import cp.util.RowMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 4/06/2017.
 */
@Component
public class TextureConfig {

    private Map<Integer, TextureSelection> textureSelections = new HashMap<>();
    @Autowired
    private SubSetCalculator subSetCalculator;
    @Autowired
    private PCGenerator pcGenerator;

    @PostConstruct
    public void init() {
        TextureSelection textureSelection = new TextureSelection();
//        textureSelections.put(0, textureSelection.getChordTypes(ChordType.CH2_KLEINE_TERTS_CHR, ChordType.CH2_GROTE_TERTS_CHR));

        List<Chord> subSets = new ArrayList<>();
//        subSets.addAll(subSetCalculator.getSubSets(Scale.MAJOR_SCALE.getForteName(), "3-11"));
//        subSets.addAll(subSetCalculator.getSubSets(Scale.MAJOR_SCALE.getForteName(), "3-5"));
//        textureSelection.addChordType(ChordType.MAJOR_CHR, ChordType.MAJOR_1_CHR, ChordType.MAJOR_2_CHR,
//                ChordType.MINOR_CHR, ChordType.MINOR_1_CHR, ChordType.MINOR_2_CHR);
//        textureSelection.addChordType(ChordType.CH2_TRITONE_CHR, ChordType.NO_INTERVALS);
//        textureSelection.addChordType(ChordType.CH2_KWINT_CHR);
//        textureSelection.addIntervals(pcGenerator.getPitchClasses("7-Z37", 0),
//                ChordType.CH2_GROTE_SIXT_CHR, ChordType.CH2_KLEINE_SIXT_CHR,
//                ChordType.CH2_KLEINE_TERTS_CHR, ChordType.CH2_GROTE_TERTS_CHR);
        textureSelection.addOctatonicAlternatingIntervals();
//        textureSelection.addSetclasses(VoicingType.DROP_2, Scale.SET_3_5.getPitchClasses());
//        textureSelection.addSetclasses(VoicingType.UP_2, Scale.SET_3_4.getPitchClasses());
//        textureSelection.addSetclasses(VoicingType.UP_2, Scale.SET_4_8.getPitchClasses());
//        textureSelections.put(1, textureSelection);
//        textureSelections.put(2, textureSelection);
    }


    public DependantHarmony getTextureFor(int voice, int pitchClass){
        TextureSelection textureSelection = textureSelections.get(voice);
        return textureSelection.getRandomDependantHarmony(pitchClass);
    }


    public boolean hasTexture(int voice) {
        return textureSelections.containsKey(voice);
    }

    public List<DependantHarmony> getAllRowMatrix(int[] setClass, VoicingType voicingType){
        List<DependantHarmony> setClassTypes = new ArrayList<>();

        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);
        rowMatrix.show();

        for (int i = 0; i < setClass.length; i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
//            System.out.println(Arrays.toString(row));
            DependantHarmony dependantHarmonyRow = new DependantHarmony(row, voicingType);
            dependantHarmonyRow.setChordType(ChordType.SETCLASS);
            setClassTypes.add(dependantHarmonyRow);

            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
//            System.out.println(Arrays.toString(column));
            DependantHarmony dependantHarmonyInversion = new DependantHarmony(column, voicingType);
            dependantHarmonyInversion.setChordType(ChordType.SETCLASS);
            setClassTypes.add(dependantHarmonyInversion);
        }
        return setClassTypes;
    }

}

//        //chords
//        List<DependantHarmony> chordTypes = new ArrayList<>();
//		chordTypes.add(createDependantHarmony(ChordType.MAJOR));//major and minor
////		chordTypes.add(createDependantHarmony(ChordType.MAJOR_CHR));
//		chordTypes.add(createDependantHarmony(ChordType.MAJOR_1));//major and minor
////		chordTypes.add(createDependantHarmony(ChordType.MAJOR_1_CHR));
//        chordTypes.add(createDependantHarmony(ChordType.MAJOR_2));//major and minor
////        chordTypes.add(createDependantHarmony(ChordType.MAJOR_2_CHR));
////        chordTypes.add(createDependantHarmony(ChordType.MINOR_CHR));
////        chordTypes.add(createDependantHarmony(ChordType.MINOR_1_CHR));
////        chordTypes.add(createDependantHarmony(ChordType.MINOR_2_CHR));
//
////        chordTypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_OCTAVE));
////        chordTypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_OCTAVE));
////        chordTypes.add(createDependantHarmony(ChordType.CH2_KWART_OCTAVE));
////        chordTypes.add(createDependantHarmony(ChordType.CH2_KWINT_OCTAVE));
//
////        chordTypes.add(createDependantHarmony(ChordType.DOM));
////        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_1));
////        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_2));
//
////        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//
//
////        textureTypes.put(0, chordTypes);
////        textureTypes.put(1, chordTypes);
//
//        List<DependantHarmony> intervaltypes = new ArrayList<>();
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWART));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWINT));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SECONDE));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE)); == kwart diatonic
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_TERTS_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_TERTS_CHR));
////        intervaltypes.add(createDependantHarmony(ChordType.CH2_OCTAVE));
//
//        List<DependantHarmony> intervalOctavetypes = new ArrayList<>();
//        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_OCTAVE));
//        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_OCTAVE));
//        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_KWART_OCTAVE));
//        intervalOctavetypes.add(createDependantHarmony(ChordType.CH2_KWINT_OCTAVE));
//        intervalOctavetypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//
//        textureTypes.put(0, intervaltypes);
//        textureTypes.put(1, intervaltypes);
//
//        List<DependantHarmony> intervals = new ArrayList<>();
////        intervals.add(createDependantHarmony(ChordType.CH2_KLEIN_SEPTIEM));
////        intervals.add(createDependantHarmony(ChordType.CH2_GROTE_SECONDE_CHR));
////        intervals.add(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR));
////        intervals.add(createDependantHarmony(ChordType.MINOR_CHR));
////        intervals.add(createDependantHarmony(ChordType.MINOR_1_CHR));
////        intervals.add(createDependantHarmony(ChordType.MINOR_2_CHR));
//        intervals.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
////        intervals.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        intervals.add(createDependantHarmony(ChordType.CH2_KWINT));
////        textureTypes.put(2, intervals);
////        intervals = new ArrayList<>();
////        intervals.add(createDependantHarmony(ChordType.CH2_OCTAVE));
////        intervals.add(createDependantHarmony(ChordType.CH2_KWINT));
////        intervals.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS));
////        intervals.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
////        intervals.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        textureTypes.put(1, intervals);
////        textureTypes.put(2, intervaltypes);
////        textureTypes.put(2, chordTypes);
////        textureTypes.addAll(intervaltypes);
////        textureTypes.put(0, chordTypes);
//
//        List<DependantHarmony> octaveDoubling = new ArrayList<>();
//        octaveDoubling.add(createDependantHarmony(ChordType.CH2_OCTAVE));
////        textureTypes.put(0, octaveDoubling);
////        textureTypes.put(3, octaveDoubling);
//
//        List<DependantHarmony> symmetryChords = new ArrayList<>();
//        symmetryChords.add(createDependantHarmony(ChordType.SYMMETRY, composition.axisHigh,composition.axisLow));
//        symmetryChords.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        textureTypes.put(0, symmetryChords);
////        textureTypes.put(1, symmetryChords);
////
//        List<DependantHarmony> symmetryChords2 = new ArrayList<>();
//        symmetryChords2.add(createDependantHarmony(ChordType.SYMMETRY, composition.axisHigh,composition.axisLow));
////        symmetryChords2.add(createDependantHarmony(ChordType.SYMMETRY, composition.axisHigh,composition.axisLow));
////        symmetryChords2.add(createDependantHarmony(ChordType.NO_INTERVALS));
////        textureTypes.put(2, symmetryChords2);
//
//        Set set3_1 = tnTnIType.getPrimeByName("3-5");
//        List<DependantHarmony> setClassTypes = new ArrayList<>();
////        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.CLOSE));
////        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_2));
////        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_3));
////        setClassTypes.add(createDependantHarmony(set3_1.tntnitype, VoicingType.DROP_2_4));
//
//        List<DependantHarmony> allRowMatrixDrop2 = getAllRowMatrix(set3_1.tntnitype, VoicingType.CLOSE);
////        List<DependantHarmony> allRowMatrixDrop3 = getAllRowMatrix(set3_1.tntnitype, VoicingType.DROP_3);
////        allRowMatrixDrop2.addAll(allRowMatrixDrop3);
//
//        Set set3_4 = tnTnIType.getPrimeByName("3-4");
//        List<DependantHarmony> allRowMatrixDrop2set3_4 = getAllRowMatrix(set3_4.tntnitype, VoicingType.CLOSE);
////        List<DependantHarmony> allRowMatrixDrop3set3_4 = getAllRowMatrix(set3_4.tntnitype, VoicingType.DROP_3);
////        allRowMatrixDrop2set3_4.addAll(allRowMatrixDrop3set3_4);
//
//        allRowMatrixDrop2.addAll(allRowMatrixDrop2set3_4);
//        allRowMatrixDrop2.addAll(intervaltypes);
////        textureTypes.put(1, allRowMatrixDrop2);
//
//
//        Set set3_11 = tnTnIType.getPrimeByName("3-11");
//        Set set3_3 = tnTnIType.getPrimeByName("3-3");
//        Set set_maj7 = tnTnIType.getPrimeByName("4-20");
//        Set set_French6 = tnTnIType.getPrimeByName("4-25");
//        Set set_min7 = tnTnIType.getPrimeByName("4-26");
//        Set set_dom_halfdim = tnTnIType.getPrimeByName("4-27");
//        Set set_dim = tnTnIType.getPrimeByName("4-28");
//
//        List<DependantHarmony> setClassTypesSet = new ArrayList<>();
//
////        setClassTypesSet.add(createDependantHarmony(set.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set.tntnitype, VoicingType.DROP_2));
////        setClassTypesSet.add(createDependantHarmony(set.tntnitype, VoicingType.DROP_3));
////        setClassTypesSet.add(createDependantHarmony(set_maj7.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set_maj7.tntnitype, VoicingType.DROP_2));
////        setClassTypesSet.add(createDependantHarmony(set_French6.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set_French6.tntnitype, VoicingType.DROP_2));
//
////        setClassTypesSet.add(createDependantHarmony(set_min7.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set_min7.tntnitype, VoicingType.DROP_2));
////        setClassTypesSet.add(createDependantHarmony(set.tntnitype, VoicingType.UP_3));
//
////        setClassTypesSet.add(createDependantHarmony(set_dom_halfdim.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set3_11.tntnitype, VoicingType.DROP_2));
////        setClassTypesSet.add(createDependantHarmony(set_dom_halfdim.tntnitype, VoicingType.UP_3));
////        setClassTypesSet.add(createDependantHarmony(set_dom_halfdim.tntnitype, VoicingType.UP_2));
////        setClassTypesSet.add(createDependantHarmony(set3_3.tntnitype, VoicingType.CLOSE));
//
////        setClassTypesSet.add(createDependantHarmony(set_dim.tntnitype, VoicingType.CLOSE));
////        setClassTypesSet.add(createDependantHarmony(set_dim.tntnitype, VoicingType.DROP_2));
////        setClassTypesSet.add(createDependantHarmony(set_dim.tntnitype, VoicingType.UP_3));
//
//        List<DependantHarmony> setClasses = new ArrayList<>();
//        List<Chord> subSets = new ArrayList<>();
//        subSets.addAll(subSetCalculator.getSubSets(Scale.MAJOR_SCALE.getForteName(), "3-11"));
////        subSets.addAll(subSetCalculator.getSubSets("6-32", "3-9"));
////        subSets.addAll(subSetCalculator.getSubSets("6-32", "3-6"));
//        for (Chord chord : subSets) {
//            java.util.Set<Integer> pitchClassSet = chord.getPitchClassSet();
//            Integer[] pcs = pitchClassSet.toArray(new Integer[0]);
//            int[] pitchCls = ArrayUtils.toPrimitive(pcs);
////            setClasses.add(createDependantHarmonyComposition(pitchCls, VoicingType.CLOSE));
//            setClasses.add(createDependantHarmonyComposition(pitchCls, VoicingType.CLOSE));
//            setClasses.add(createDependantHarmonyComposition(pitchCls, VoicingType.DROP_2));
//        }
//
////        textureTypes.put(0, setClasses);
////        textureTypes.put(1, setClasses);
//    }
//
////    public List<DependantHarmony> rowMatrix(int[] setClass, VoicingType voicingType) {
////        List<DependantHarmony> setClassTypes = new ArrayList<>();
////
////        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(Collectors.toList());
////        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);
////        rowMatrix.show();
////
////        int[] row = rowMatrix.getRow(setClass.length - 1);
////        Arrays.sort(row);
////        DependantHarmony dependantHarmony = new DependantHarmony(row, voicingType);
////        dependantHarmony.setChordType(ChordType.SETCLASS);
////        setClassTypes.add(dependantHarmony);
////        System.out.println(Arrays.toString(row));
////
////        int[] column = rowMatrix.getColumn(0);
////        Arrays.sort(column);
////        DependantHarmony dependantHarmonyInversion = new DependantHarmony(column, voicingType);
////        dependantHarmonyInversion.setChordType(ChordType.SETCLASS);
////        setClassTypes.add(dependantHarmonyInversion);
////        System.out.println(Arrays.toString(column));
////        return setClassTypes;
////    }
//

//
//    private DependantHarmony createDependantHarmony(ChordType chordType){
//        DependantHarmony dependantHarmony = new DependantHarmony();
//        dependantHarmony.setChordType(chordType);
//        return  dependantHarmony;
//    }
//
//    private DependantHarmony createDependantHarmony(int[] setClass, VoicingType voicingType){
//        DependantHarmony dependantHarmony = new DependantHarmony(setClass, voicingType);
//        dependantHarmony.setChordType(ChordType.SETCLASS);
//        return dependantHarmony;
//    }
//
//    private DependantHarmony createDependantHarmonyComposition(int[] setClass, VoicingType voicingType){
//        DependantHarmony dependantHarmony = new DependantHarmony(setClass, voicingType);
//        dependantHarmony.setChordType(ChordType.SETCLASS_COMPOSITION);
//        return dependantHarmony;
//    }
//
//    private DependantHarmony createDependantHarmony(ChordType chordType, int axisPitchClassHigh, int axisPitchClassLow){
//        return new DependantHarmony(chordType, axisPitchClassHigh, axisPitchClassLow);
//    }
//
//    public List<DependantHarmony> getTextureFor(int voice){
//        return textureTypes.get(voice);
//    }
//
//    public boolean hasTexture(int voice){
//        return textureTypes.containsKey(voice);
//    }
//}
