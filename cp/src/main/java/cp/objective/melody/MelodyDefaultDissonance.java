package cp.objective.melody;

import cp.model.note.Interval;
import org.springframework.stereotype.Component;

@Component
public class MelodyDefaultDissonance implements MelodyDissonance{

    @Override
    public double getMelodicValue(int difference) {
        return Interval.getEnumInterval(difference).getMelodicValue();
    }
}
