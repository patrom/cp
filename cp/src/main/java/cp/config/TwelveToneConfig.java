package cp.config;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.model.twelve.TwelveToneBuilder;
import cp.model.twelve.TwelveToneSplit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class TwelveToneConfig {

    @Resource(name = "defaultUnevenCombinations")
    protected Map<Integer, List<RhythmCombination>> defaultUnEvenCombinations;

    @Resource(name = "defaultEvenCombinations")
    protected Map<Integer, List<RhythmCombination>> defaultEvenCombinations;

    @Resource(name = "homophonicEven")
    protected  Map<Integer, List<RhythmCombination>> homophonicEven;

    @Resource(name = "homophonicUneven")
    protected Map<Integer, List<RhythmCombination>> homophonicUneven;

    private Map<Integer, List<TwelveToneBuilder>> twelveToneConfig = new TreeMap<>();
    private Map<Integer, List<ScaleConfig>> scaleConfig = new TreeMap<>();
    private Map<TwelveToneSplit, List<ScaleConfig>> scaleConfigSplit = new TreeMap<>();

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @PostConstruct
    public void init() {
        List<RhythmCombination> evenValues = defaultEvenCombinations.values().stream().flatMap(rhythmCombinations -> rhythmCombinations.stream()).collect(toList());
        RhythmCombination[] evenCombinations = evenValues.toArray(new RhythmCombination[defaultEvenCombinations.values().size()]);

        List<RhythmCombination> unevenValues = defaultUnEvenCombinations.values().stream().flatMap(rhythmCombinations -> rhythmCombinations.stream()).collect(toList());
        RhythmCombination[] unevenCombinations = unevenValues.toArray(new RhythmCombination[defaultUnEvenCombinations.values().size()]);

        List<RhythmCombination> evenHomophonicValues = homophonicEven.values().stream().flatMap(rhythmCombinations -> rhythmCombinations.stream()).collect(toList());
        RhythmCombination[] homophonicEvenCombinations = evenHomophonicValues.toArray(new RhythmCombination[homophonicEven.values().size()]);

        List<RhythmCombination> unevenHomophonicValues = homophonicUneven.values().stream().flatMap(rhythmCombinations -> rhythmCombinations.stream()).collect(toList());
        RhythmCombination[] homophonicUnevenCombination = unevenHomophonicValues.toArray(new RhythmCombination[homophonicUneven.values().size()]);

        List<Integer> durations = Stream.of(DurationConstants.QUARTER, DurationConstants.HALF).collect(toList());

//        ScaleConfig ALL_INTERVAL_TRETRACHORD1 = new ScaleConfig(
//                durations, 2, Scale.ALL_INTERVAL_TRETRACHORD1,
//                evenCombinations);
//
//        ScaleConfig ALL_INTERVAL_TRETRACHORD2 = new ScaleConfig(
//                durations, 4, Scale.ALL_INTERVAL_TRETRACHORD2,
//                evenCombinations);
//
//        //voice!!!
//        this.scaleConfig.put(0, Stream.of(ALL_INTERVAL_TRETRACHORD1, ALL_INTERVAL_TRETRACHORD2).collect(toList()));
//        this.scaleConfig.put(1, Stream.of(ALL_INTERVAL_TRETRACHORD2, ALL_INTERVAL_TRETRACHORD1).collect(toList()));


        //split
        List<Integer> splitVoices = new ArrayList<>();
        splitVoices.add(1);
//        splitVoices.add(2);

        ScaleConfig ALL_INTERVAL_TRETRACHORD2 = new ScaleConfig(
                durations, 2, Scale.VARIATIONS_FOR_ORCHESTRA_OP31_HEXA2,
                homophonicEvenCombinations);
        ALL_INTERVAL_TRETRACHORD2.setSplitVoices(splitVoices);

        ScaleConfig ALL_INTERVAL_TRETRACHORD1 = new ScaleConfig(
                durations, 2, Scale.VARIATIONS_FOR_ORCHESTRA_OP31_HEXA1,
                homophonicEvenCombinations);
        ALL_INTERVAL_TRETRACHORD1.setSplitVoices(splitVoices);

        this.scaleConfig.put(0, Stream.of(ALL_INTERVAL_TRETRACHORD2, ALL_INTERVAL_TRETRACHORD1).collect(toList()));


        List<Integer> splitVoices2 = new ArrayList<>();
        splitVoices2.add(3);
//        splitVoices2.add(4);
        ScaleConfig TRETRACHORD2 = new ScaleConfig(
                durations, 4, Scale.VARIATIONS_FOR_ORCHESTRA_OP31,
                homophonicEvenCombinations);
        TRETRACHORD2.setSplitVoices(splitVoices2);
//
//        ScaleConfig TRETRACHORD1 = new ScaleConfig(
//                durations, 2, Scale.VARIATIONS_FOR_ORCHESTRA_OP31_HEXA1,
//                rhythmCombinations.twoNoteEven::pos13,
//                rhythmCombinations.oneNoteEven::pos1,
//                rhythmCombinations.twoNoteEven::pos14,
//                rhythmCombinations.threeNoteUneven::pos123);
//        ALL_INTERVAL_TRETRACHORD1.setSplitVoices(splitVoices);
//
        this.scaleConfig.put(2, Stream.of(TRETRACHORD2).collect(toList()));

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

    public List<TwelveToneBuilder> getTwelveToneBuilders( int voice, int start){
        return twelveToneConfig.entrySet().stream()
                .filter(entry -> entry.getKey() == voice)
                .flatMap(entry -> entry.getValue().stream())
                .filter(twelveToneBuilder -> twelveToneBuilder.getStart() == start)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<ScaleConfig>> getScaleConfig() {
        return scaleConfig;
    }

    public Map<TwelveToneSplit, List<ScaleConfig>> getScaleConfigSplit() {
        return scaleConfigSplit;
    }

    public void clearConfig(){
        twelveToneConfig.clear();
    }
}
