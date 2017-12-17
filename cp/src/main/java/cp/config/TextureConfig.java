package cp.config;

import cp.composition.Composition;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        chordTypes.add(createDependantHarmony(ChordType.DOM));
        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_1));
        chordTypes.add(createDependantHarmony(ChordType.DOM_CHR_2));

//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        chordTypes.add(createDependantHarmony(ChordType.NO_INTERVALS));


        List<DependantHarmony> intervaltypes = new ArrayList<>();
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWART));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KWINT));
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE)); == kwart diatonic
//        intervaltypes.add(createDependantHarmony(ChordType.CH2_TRITONE_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        intervaltypes.add(createDependantHarmony(ChordType.NO_INTERVALS));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
        intervaltypes.add(createDependantHarmony(ChordType.CH2_KLEINE_TERTS_CHR));

//        textureTypes.put(1, intervaltypes);
//        textureTypes.put(0, chordTypes);

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
    }

    private DependantHarmony createDependantHarmony(ChordType chordType){
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(chordType);
        return  dependantHarmony;
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
