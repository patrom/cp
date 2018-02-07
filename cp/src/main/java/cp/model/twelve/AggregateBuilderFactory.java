package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.note.Scale;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AggregateBuilderFactory {

    public AggregateBuilder getAggregateBuilder(BuilderType type, int start, List<Integer> beats, int voice, Scale scale, RhythmCombination... rhythmCombinations) {
        switch (type) {
            case TWELVE_TONE:
                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start, beats, voice, scale, rhythmCombinations);
                twelveToneBuilder.setBuilderType(BuilderType.TWELVE_TONE);
                return twelveToneBuilder;
            case SEGMENT:
                SegmentBuilder segmentBuilder = new SegmentBuilder(start, beats, voice, scale, rhythmCombinations);
                segmentBuilder.setBuilderType(BuilderType.SEGMENT);
                return segmentBuilder;
            case PARTIAL:
                PartialTwelveToneBuilder partialTwelveToneBuilder = new PartialTwelveToneBuilder(start, beats, voice, scale, rhythmCombinations);
                partialTwelveToneBuilder.setBuilderType(BuilderType.PARTIAL);
                return partialTwelveToneBuilder;
        }
        throw new IllegalArgumentException("Type unknown: " + type);
    }
}
