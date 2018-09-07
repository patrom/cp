package cp.objective.transformation;

import org.springframework.stereotype.Component;

@Component(value = "voiceLeadingZoneTransformation")
public class VoiceLeadingZoneTransformation implements TransformationDissonance {

    @Override
    public double getDissonance(Transformation transformation) {
        switch (transformation) {
            case ZONE_1:
                return 1.0;
            case ZONE_2:
                return 1.0;
            case ZONE_3:
                return 0.0;
            case UNDEFINED:
                return 0.0;
        }
        return 0.0;
    }

}
