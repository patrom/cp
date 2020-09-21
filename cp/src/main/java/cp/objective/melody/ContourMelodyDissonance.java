package cp.objective.melody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContourMelodyDissonance implements MelodyDissonance{

    private static final Logger LOGGER = LoggerFactory.getLogger(ContourMelodyDissonance.class);

    //Carl Ruggles
    public static final Map<Integer,Double> intervalMap = new HashMap<Integer,Double>() {{
        put(-1, 1.0);
        put(-2, 1.0);
        put(-3, 0.0);
        put(-4, 0.5);
        put(-5, 0.99);
        put(-6, 0.99);
        put(-7, 0.0);
        put(-8, 0.0);
        put(-9, 0.0);
        put(-10, 0.0);
        put(-11, 0.0);
        put(-12, 0.0);
        put(-13, 0.0);
        put(-14, 0.0);

        put(1, 0.7);
        put(2, 0.7);
        put(3, 0.0);
        put(4, 0.5);
        put(5, 0.9);
        put(6, 0.9);
        put(7, 0.9);
        put(8, 0.1);
        put(9, 0.1);
        put(10, 0.9);
        put(11, 1.0);
        put(12, 0.1);
        put(13, 1.0);
        put(14, 0.1);

    }};
    @Override
    public double getMelodicValue(int difference) {
        Double value = intervalMap.get(difference);
        if (value != null){
            return value;
        }
        return 0.0;
    }
}
