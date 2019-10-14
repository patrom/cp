package cp.config.map;

import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BassMap extends CompositionMap{

    @PostConstruct
    public void init(){
        compositionMap.put(0, melodyMapComposition.getBass());

        int mapSize = compositionMap.values()
                .stream()
                .mapToInt(List::size)
                .sum();
        System.out.println("size map :" + mapSize);
    }


}

