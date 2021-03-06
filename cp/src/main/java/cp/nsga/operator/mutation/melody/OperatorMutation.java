package cp.nsga.operator.mutation.melody;

import cp.composition.beat.BeatGroup;
import cp.config.VoiceConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 9/05/2017.
 */
@Component(value = "operatorMutation")
public class OperatorMutation implements MutationOperator<CpMelody> {

    private static Logger LOGGER = LoggerFactory.getLogger(OperatorMutation.class);

    private double probabilityOperatorMutation;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TimeLine timeLine;

    @Autowired
    public OperatorMutation(@Value("${probabilityOperatorMutation}") double probabilityOperatorMutation) {
        this.probabilityOperatorMutation = probabilityOperatorMutation;
    }

    public void doMutation(CpMelody melody) {
        if (PseudoRandom.randDouble() < probabilityOperatorMutation) {
            if (melody.hasScale()) {
                BeatGroup beatGroup = melody.getBeatGroup();
                if (beatGroup.getTonality() == Tonality.TONAL) {
                    int random = RandomUtil.getRandomNumberInRange(0, 1);
//                    int steps = RandomUtil.getRandomNumberInRange(0, 7);
                    int steps = RandomUtil.getRandomFromIntArray(Scale.MAJOR_SCALE.getPitchClasses());
                    List<Integer> pitchClasses = null;

                    TimeLineKey timeLineKey = RandomUtil.getRandomFromList(beatGroup.getTimeLineKeys());
                    switch (random) {
                        case 0:
                            int[] indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getIndexesMotivePitchClasses());
                            pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, steps, timeLineKey.getKey());
//                            LOGGER.info("transpose tonal");
                            break;
                        case 1:
                            indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getInverseIndexesMotivePitchClasses());
                            pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, steps, timeLineKey.getKey());
//                            LOGGER.info("inverse tonal");
                            break;
                        case 2:
                            indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getReversedIndexesMotivePitchClasses());
                            pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, steps, timeLineKey.getKey());
                            LOGGER.info("retrograde tonal");
                            break;
                        case 3:
                            indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getReverseInverseIndexesMotivePitchClasses());
                            pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, steps, timeLineKey.getKey());
                            LOGGER.info("retrograde inverse tonal");
                            break;
                        //                    case 5:
    //                        steps = RandomUtil.getRandomNumberInRange(0, 7);
    //                        melody.R().transposePitchClasses(steps, 0, timeLine);
    //                        break;
    //                    case 7:
    //                        degree = RandomUtil.getRandomNumberInRange(1, 7);
    //                        melody.R().inversePitchClasses(degree, 0, timeLine);
    //                        break;
    //                    case 6:
    //                        melodyBlock.augmentation(factor, timeLine);
    //                        break;
    //                    case 7:
    //                        melodyBlock.diminution(factor, timeLine);
    //                    case 3:
    //                        int steps = RandomUtil.getRandomNumberInRange(0, 7);
    //                        melodyBlock.M(steps);
                        default:
                            break;
                    }
                    int i = 0;
                    for (Note note : melody.getNotesNoRest()) {
                        note.setPitchClass(pitchClasses.get(i));
                        i++;
                    }
                } else if (beatGroup.getTonality() == Tonality.ATONAL) {
                    int random = RandomUtil.getRandomNumberInRange(0, 1);
                    int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
//                    int steps = RandomUtil.getRandomNumberInRange(0, 11);
                    int steps = RandomUtil.getRandomFromIntArray(pitchClasses);
                    switch (random) {
                        case 0:
                            melody.T(steps);
//                            LOGGER.info("T:" + steps);
                            break;
                        case 1:
                            melody.I().T(steps);
//                            LOGGER.info("IT:" + steps);
                            break;
    //                        case 2:
    //                            melody.R().T(steps);
    //                            break;
    //                        case 3:
    //                            melody.R().I().T(steps);
    //                            break;
                        default:
                            break;

                    }
                }
            }
        }
    }

    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(melody);
        return melody;
    }
}

