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
        List<DependantHarmony> types = new ArrayList<>();
//        types.add(createDependantHarmony(ChordType.CH2_KWART));
//		types.add(createDependantHarmony(ChordType.CH2_KWINT));
//        types.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
//        types.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        types.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        types.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS));
//        types.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_CHR));//if no timeline available for voice (provided)
//        types.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
//        types.add(createDependantHarmony(ChordType.CH2_KLEINE_TERTS_CHR));
//        types.add(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR));
//        types.add(ChordType.CH2_KWART_CHR);
//        types.add(ChordType.CH2_KWINT_CHR);
//        types.add(ChordType.MAJOR);


//        types.add(ChordType.CH2_GROTE_TERTS);
//        types.add(ChordType.CH2_GROTE_TERTS_CHR);
//        types.add(ChordType.CH2_KLEINE_TERTS_CHR);
//        types.add(ChordType.CH2_KWART);
//		types.add(ChordType.CH2_KWINT);
//		types.add(ChordType.ALL);
//        types.add(ChordType.CH2_GROTE_SIXT);
//        types.add(ChordType.CH2_GROTE_SIXT_CHR);
//        types.add(ChordType.CH2_KLEINE_SIXT_CHR);

//		types.add(ChordType.MAJOR);//major and minor
//		types.add(createDependantHarmony(ChordType.MAJOR_CHR));
//		types.add(ChordType.MAJOR_1);//major and minor
//		types.add(createDependantHarmony(ChordType.MAJOR_1_CHR));
//        types.add(ChordType.MAJOR_2);//major and minor
//        types.add(ChordType.MAJOR_2_CHR);
        types.add(createDependantHarmony(ChordType.MINOR_CHR));
        types.add(createDependantHarmony(ChordType.MINOR_1_CHR));
        types.add(createDependantHarmony(ChordType.MINOR_2_CHR));

//        types.add(ChordType.DOM);

        List<DependantHarmony> types2 = new ArrayList<>();
        types2.add(createDependantHarmony(ChordType.CH2_KWART));
        types2.add(createDependantHarmony(ChordType.CH2_KWINT));
        types2.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT));
        types2.add(createDependantHarmony(ChordType.CH2_GROTE_SIXT_CHR));
        types2.add(createDependantHarmony(ChordType.NO_INTERVALS));
        types2.add(createDependantHarmony(ChordType.NO_INTERVALS));
        types2.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS));
        types2.add(createDependantHarmony(ChordType.CH2_GROTE_TERTS_CHR));
//        types.add(ChordType.MAJOR);

//        textureTypes.put(0, types2);
//        textureTypes.put(1, types);

        List<DependantHarmony> symmetryChords = new ArrayList<>();
        symmetryChords.add(createDependantHarmony(ChordType.SYMMEETRY, composition.axisHigh,composition.axisLow));
        symmetryChords.add(createDependantHarmony(ChordType.NO_INTERVALS));
//        textureTypes.put(0, symmetryChords);
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
