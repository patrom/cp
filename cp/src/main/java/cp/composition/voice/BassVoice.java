package cp.composition.voice;

import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

        evenRhythmCombinationsPerNoteSize = homophonicEven;
        unevenRhythmCombinationsPerNoteSize = homophonicUneven;

        dynamic = Dynamic.MP;
        dynamics = Stream.of(Dynamic.MF, Dynamic.MP).collect(toList());
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

        technical = Technical.LEGATO;
    }


}
