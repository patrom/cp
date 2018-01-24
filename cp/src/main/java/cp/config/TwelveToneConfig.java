package cp.config;

import cp.combination.RhythmCombinations;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.model.twelve.TwelveToneBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class TwelveToneConfig {

    private Map<Integer, List<TwelveToneBuilder>> twelveToneConfig = new TreeMap<>();
    private Map<Integer, List<ScaleConfig>> scaleConfig = new TreeMap<>();

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @PostConstruct
    public void init() {
        ScaleConfig scaleConfig = new ScaleConfig(
                DurationConstants.HALF, 2, Scale.ALL_INTERVAL_TRETRACHORD1,
                rhythmCombinations.threeNoteUneven::pos123,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos34,
                rhythmCombinations.threeNoteUneven::pos123);

        //voice!!!
        this.scaleConfig.put(0, Collections.singletonList(scaleConfig));

        scaleConfig = new ScaleConfig(
                DurationConstants.QUARTER, 4, Scale.ALL_INTERVAL_TRETRACHORD2,
                rhythmCombinations.threeNoteUneven::pos123,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos34,
                rhythmCombinations.threeNoteUneven::pos123);
        this.scaleConfig.put(1, Collections.singletonList(scaleConfig));

    }

    public List<TwelveToneBuilder> getTwelveToneConfigForVoice(int voice){
        return twelveToneConfig.get(voice);
    }

    public List<ScaleConfig> getScaleConfigForVoice(int voice){
        return scaleConfig.get(voice);
    }

    public Set<Integer> getVoices(){
        return scaleConfig.keySet();
    }

    public Map<Integer,List<TwelveToneBuilder>> getTwelveToneConfig() {
        return twelveToneConfig;
    }

    public void addTwelveToneBuilder(int voice, TwelveToneBuilder twelveToneBuilder){
        twelveToneConfig.compute(voice, (k, v) -> {
                if (v == null) {
                    ArrayList<TwelveToneBuilder> twelveToneBuilders = new ArrayList<>();
                    twelveToneBuilders.add(twelveToneBuilder);
                    return twelveToneBuilders;
                } else {
                    v.add(twelveToneBuilder);
                    return v;
                }
            }
        );
    }

    public TwelveToneBuilder getTwelveToneBuilder(int voice , int start){
        Optional<TwelveToneBuilder> builder = twelveToneConfig.entrySet().stream()
                .filter(entry -> entry.getKey() == voice)
                .flatMap(entry -> entry.getValue().stream())
                .filter(twelveToneBuilder -> twelveToneBuilder.getStart() == start)
                .findFirst();
        if (builder.isPresent()) {
            return builder.get();
        }
        throw new IllegalStateException("No builder found");
    }

    public Map<Integer, List<ScaleConfig>> getScaleConfig() {
        return scaleConfig;
    }
}
