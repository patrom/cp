package cp.nsga.operator.mutation.contour;

import cp.model.melody.CpMelody;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class ContourMutation implements MutationOperator<CpMelody> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContourMutation.class);

    private double probabilityContour;

    @Autowired
    public ContourMutation(@Value("${probabilityContour}") double probabilityContour) {
        this.probabilityContour = probabilityContour;
    }

    public void doMutation(CpMelody melody) {
        if (PseudoRandom.randDouble() < probabilityContour) {
            switch (RandomUtil.getRandomNumberInRange(0,2)) {
                case 0:
                    melody.updateContour();
                    break;
                case 1:
                    melody.updateContourAscending();
                    break;
                case 2:
                    melody.updateContourDescending();
                    break;
            }
            LOGGER.debug("Contour mutated");
        }
    }

    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(melody);
        return melody;
    }
}