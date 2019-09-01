package cp.objective.transformation;

import org.springframework.stereotype.Component;

@Component(value = "neoRiemannTransformation")
public class NeoRiemannTransformation implements TransformationDissonance {

    @Override
    public double getDissonance(Transformation transformation) {
        switch (transformation) {
            //triads
            case P:
                return 1.0;
            case R:
                return 1.0;
            case L:
                return 1.0;
            case S:
                return 1.0;
            case H:
                return 1.0;
            case N:
                return 1.0;
            case RP_sm:
                return 1.0;
            case PL_sm:
                return 1.0;
            case LP_m:
                return 1.0;
            case PR_m:
                return 1.0;
            case PRP:
                return 1.0;
            case T6_SUBV:
                return 1.0;
            //tetrachords
            case ZONE_1:
                return 1.0;
            case ZONE_2:
                return 1.0;

            case UNDEFINED:
                return 0.0;
        }
        return 0.0;
    }

}
