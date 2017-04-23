package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupTwo;
import cp.model.note.Dynamic;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class BassVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamic = Dynamic.MP;
        dynamics = dynamics = Stream.of(Dynamic.MF, Dynamic.MP).collect(toList());
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
		pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = this::getBeatGroups;
        technical = Technical.LEGATO;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(oneNoteEven::pos1)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos13)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos14)));
        return beatGroups;
    }
}
