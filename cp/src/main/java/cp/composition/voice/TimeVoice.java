package cp.composition.voice;

import cp.model.note.Dynamic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 20/03/2017.
 */
@Component
public class TimeVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamic = Dynamic.MF;
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//		pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        timeConfig = time24;
    }

//    private List<BeatGroup> getBeatGroups(){
//        List<BeatGroup> beatGroups = new ArrayList<>();
////        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(twoNoteEven::pos13)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos14)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos12)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(twoNoteEven::pos14)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(threeNoteEven::pos134)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(threeNoteUneven::pos123)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(twoNoteUneven::pos13)));
////        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(threeNoteEven::pos124)));
//        return beatGroups;
//    }
}
