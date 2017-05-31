package cp.composition.voice;

import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        rhythmCombinations1 = this::getBeatGroups;

        technical = Technical.LEGATO;
    }

//    private List<BeatGroup> getBeatGroups(){
//        List<BeatGroup> beatGroups = new ArrayList<>();
////        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(oneNoteEven::pos1)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(twoNoteEven::pos13)));
////        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos14)));
//        return beatGroups;
//    }
}
