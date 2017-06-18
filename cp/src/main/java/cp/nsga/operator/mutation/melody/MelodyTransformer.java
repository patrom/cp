package cp.nsga.operator.mutation.melody;

import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by prombouts on 15/06/2017.
 */
@Component
public class MelodyTransformer {

    private static Logger LOGGER = LoggerFactory.getLogger(ProvidedMutation.class);

    @Autowired
    private TimeLine timeLine;

    public void transform(CpMelody melody){
//        LOGGER.info("melody :" + melody.getVoice());
        if (melody.getTonality() == Tonality.TONAL) {
            int random = RandomUtil.getRandomNumberInRange(0, 1);
            int steps;
            int degree;
            switch (random) {
                case 0:
                    melody.transposePitchClasses(timeLine);
//                    LOGGER.info("transform transpose tonal");
                    break;
                case 1:
                    melody.inversePitchClasses(timeLine);
//                    LOGGER.info("transform inverse tonal");
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
        } else if (melody.getTonality() == Tonality.ATONAL) {
            int random = RandomUtil.getRandomNumberInRange(0, 1);
            int steps = RandomUtil.getRandomNumberInRange(0, 11);
            switch (random) {
                case 0:
                    melody.T(steps);
                    LOGGER.info("transform T:" + steps);
                    break;
                case 1:
                    melody.I().T(steps);
                    LOGGER.info("transform IT:" + steps);
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
