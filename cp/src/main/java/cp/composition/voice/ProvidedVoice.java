package cp.composition.voice;

import cp.model.melody.CpMelody;
import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 15/05/2017.
 */
@Component
public class ProvidedVoice extends Voice {

    private List<CpMelody> melodies;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();
//        timeConfig = timeRandom;
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());

//        evenRhythmCombinationsPerNoteSize = homophonicEven;
//        unevenRhythmCombinationsPerNoteSize = homophonicUneven;

//        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
        technical = Technical.LEGATO;
    }

}
