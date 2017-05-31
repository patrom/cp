package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.generator.provider.MelodyProvider;
import cp.model.melody.CpMelody;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.instrument.Technical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 15/05/2017.
 */
@Component
public class ProvidedVoice extends Voice {

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    private MelodyProvider melodyProvider;

    private List<CpMelody> melodies;

    @PostConstruct
    public void init(){
//        melodies = melodyProvider.getMelodies();
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());
        pitchClassGenerators.add(emptyPitchClasses::updatePitchClasses);
        technical = Technical.LEGATO;
    }

    @Override
    public List<Note> getRhythmNotesForBeatgroup(BeatGroup beatGroup) {
        Collections.shuffle(melodies);
        return melodies.stream().filter(m -> m.getBeatGroup().getSize() == beatGroup.getSize()).findFirst().get().getNotes();
    }

}
