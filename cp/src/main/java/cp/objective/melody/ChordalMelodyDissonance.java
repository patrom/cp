package cp.objective.melody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChordalMelodyDissonance implements MelodyDissonance{

    private static final Logger LOGGER = LoggerFactory.getLogger(ChordalMelodyDissonance.class);

    public static final Map<Integer,Double> intervalMap = new HashMap<Integer,Double>() {{
        put(0, 1.0); //octaaf
        put(1, 0.2);
        put(2, 0.2);
        put(3, 1.0);
        put(4, 1.0);
        put(5, 1.0);
        put(6, 0.9);
        put(7, 0.9);
        put(8, 1.0);
        put(9, 1.0);
        put(10, 0.8);
        put(11, 0.8);
//	    put(12, ); //verminder melodisch gebruik
//	    put(13, ); // vermijd melodisch - harmonisch
    }};
    @Override
    public double getMelodicValue(int difference) {
        return intervalMap.get(Math.abs(difference % 12));
    }
}