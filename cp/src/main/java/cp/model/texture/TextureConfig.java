package cp.model.texture;

import cp.model.harmony.ChordType;
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

    private Map<Integer, List<ChordType>> textureTypes = new HashMap<>();

    @PostConstruct
    public void ini(){
        ArrayList<ChordType> types = new ArrayList<>();
        types.add(ChordType.CH2_KWART);
		types.add(ChordType.CH2_KWINT);
        types.add(ChordType.CH2_GROTE_SIXT);
        types.add(ChordType.NO_INTERVALS);
//        types.add(ChordType.NO_INTERVALS);
        types.add(ChordType.CH2_GROTE_TERTS);
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
		types.add(ChordType.MAJOR);
		types.add(ChordType.MAJOR_1);
      types.add(ChordType.MAJOR_2);
      types.add(ChordType.DOM);

//        textureTypes.put(0, types);
        textureTypes.put(0, types);
    }

    public List<ChordType> getTextureFor(int voice){
        return textureTypes.get(voice);
    }

    public boolean hasTexture(int voice){
        return textureTypes.containsKey(voice);
    }
}
