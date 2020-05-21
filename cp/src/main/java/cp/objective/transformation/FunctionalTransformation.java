package cp.objective.transformation;

import org.springframework.stereotype.Component;

@Component(value = "functionalTransformation")
public class FunctionalTransformation implements TransformationDissonance {

    @Override
    public double getDissonance(Transformation transformation) {
        switch (transformation) {
            case DOM:
                return 1.0;
            case SUB:
                return 1.0;
            case P:
                return 0.99;
            case R:
                return 1.0;
            case L:
                return 1.0;
            case S:
                return 0.0;
            case H:
                return 0.0;
            case N:
                return 1.0;
            case UNDEFINED:
                return 0.0;
        }
        return 0.0;
    }
}
