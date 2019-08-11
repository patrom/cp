package cp.config.map;

import cp.composition.MelodyMapComposition;
import cp.generator.SingleMelodyGenerator;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

@Component
public class MelodyMap implements CompositionMap{

    private Map<Integer, List<CpMelody>> melodyMap = new HashMap<>();
    @Autowired
    private MelodyMapComposition melodyMapComposition;

    @PostConstruct
    public void init(){
        melodyMap.put(0, melodyMapComposition.getOneNote());
        melodyMap.put(1, melodyMapComposition.getTwoNotes());

        int mapSize = melodyMap.values()
                .stream()
                .mapToInt(List::size)
                .sum();
        System.out.println("size map :" + mapSize);
    }

    public CpMelody getRandomMelody(){
        int randomIndex = RandomUtil.randomInt(0, melodyMap.size());
        List<CpMelody> melodies = melodyMap.get(randomIndex);
        return RandomUtil.getRandomFromList(melodies).clone();
    }
}
