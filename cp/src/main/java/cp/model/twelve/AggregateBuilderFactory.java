package cp.model.twelve;

import cp.combination.RhythmCombination;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AggregateBuilderFactory {

    public AggregateBuilder getAggregateBuilder(BuilderType type, int start, List<Integer> beats, int voice, int[] pitchClasses, RhythmCombination... rhythmCombinations) {
        switch (type) {
            case TWELVE_TONE:
                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start, beats, voice, pitchClasses, rhythmCombinations);
                twelveToneBuilder.setBuilderType(BuilderType.TWELVE_TONE);
                return twelveToneBuilder;
            case SEGMENT:
                SegmentBuilder segmentBuilder = new SegmentBuilder(start, beats, voice, pitchClasses, rhythmCombinations);
                segmentBuilder.setBuilderType(BuilderType.SEGMENT);
                return segmentBuilder;
            case PARTIAL:
                PartialTwelveToneBuilder partialTwelveToneBuilder = new PartialTwelveToneBuilder(start, beats, voice, pitchClasses, rhythmCombinations);
                partialTwelveToneBuilder.setBuilderType(BuilderType.PARTIAL);
                return partialTwelveToneBuilder;
            case RANDOM:
                RandomSegmentBuilder randomSegmentBuilder = new RandomSegmentBuilder(start, beats, voice, pitchClasses, rhythmCombinations);
                randomSegmentBuilder.setBuilderType(BuilderType.PARTIAL);
                return randomSegmentBuilder;
        }
        throw new IllegalArgumentException("Type unknown: " + type);
    }
}
