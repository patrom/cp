package cp.nsga.operator.mutation.melody;

import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReplaceRhythmDependantMelody {

    private PitchClassGenerator pitchClassGenerator;

    public void setPitchClassGenerator(PitchClassGenerator pitchClassGenerator) {
        this.pitchClassGenerator = pitchClassGenerator;
    }

    public void updateDependantMelodyBlockWithMelody(CpMelody melody, List<MelodyBlock> rhythmDependantMelodies) {
        for (MelodyBlock rhythmDependantMelodyBlock : rhythmDependantMelodies) {
            Optional<CpMelody> optionalDependantMelody = rhythmDependantMelodyBlock.getMelodyBlocks().stream().filter(m -> m.getStart() == melody.getStart()).findFirst();

            CpMelody dependantMelody = optionalDependantMelody.get();
            List<Note> clonedMelodyNotes = melody.getNotes().stream().map(n ->
                    {
                        Note clone = n.clone();
                        clone.setVoice(dependantMelody.getVoice());
                        return clone;
                    }
            ).collect(Collectors.toList());
            clonedMelodyNotes = pitchClassGenerator.updatePitchClasses(clonedMelodyNotes);
            dependantMelody.updateNotes(clonedMelodyNotes);
//			LOGGER.info("dependant Melody replaced: " + dependantMelody.getVoice());
        }
    }
}