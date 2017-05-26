package cp.nsga.operator.mutation.melody;

import cp.composition.voice.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.util.RandomUtil;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 27/11/2016.
 */
@Component(value="replaceMelodyBlock")
public class ReplaceMelodyBlock extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelodyBlock.class);

    @Autowired
    private ReplaceRhythmDependantMelody replaceRhythmDependantMelody;
    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TimeLine timeLine;

    @Autowired
    public ReplaceMelodyBlock(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock melodyBlock = motive.getRandomMutableMelodyBlockExcludingVoice(1);
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {
                CpMelody melody = optionalMelody.get();
                CpMelody melodyToInsert = getMelodyBlock();

                int offsetKey = melody.getStart() -  melodyToInsert.getStart();

                MelodyBlock melodyBlockToInsert = new MelodyBlock(0,0);
                melodyBlockToInsert.addMelodyBlock(melodyToInsert);

                int random = RandomUtil.random(4);
				switch (random){
					case 0:
                        int steps = RandomUtil.getRandomNumberInRange(0, 7);
                        melodyToInsert.transposePitchClasses(steps, offsetKey , timeLine);
					case 1:
                        int degree = RandomUtil.getRandomNumberInRange(1, 7);
                        melodyToInsert.inversePitchClasses(degree, offsetKey , timeLine);
					case 2:
                        PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melodyToInsert.getVoice());
                        pitchClassGenerator.updatePitchClasses(melodyToInsert.getNotes());
                    case 3:
//                        melodyBlockToInsert.augmentation(2.0, timeLine);
//                    case 4:
//                        melodyBlockToInsert.diminution(0.5, timeLine);
				}


                melodyBlock.updateMelodyBlock(melodyBlockToInsert,  melody.getStart());
            }
        }
    }

    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityReplaceMelodyBlock");
        if (probability == null) {
            Configuration.logger_.severe("probabilityReplaceMelodyBlock: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

    private CpMelody getMelodyBlock() {
        List<CpMelody> melodies = new ArrayList<>();

        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(1).len(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(1).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.HALF);
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(2 * DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
        melody = new CpMelody(notes, 0, 0, DurationConstants.HALF);
        melodies.add(melody);

        return RandomUtil.getRandomFromList(melodies);
    }

}
