package cp.config.map;

import cp.composition.MelodyMapComposition;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HarmonyMap extends CompositionMap{

    @PostConstruct
    public void init(){
        List<CpMelody> melodies = singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.HALF, keys.C);
        melodies.addAll(singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.WHOLE, keys.C));
        compositionMap.put(0, melodies);

        int mapSize = compositionMap.values()
                .stream()
                .mapToInt(List::size)
                .sum();
        System.out.println("size map :" + mapSize);
    }


}

