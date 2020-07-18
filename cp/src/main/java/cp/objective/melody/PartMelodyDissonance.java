package cp.objective.melody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PartMelodyDissonance implements MelodyDissonance{

    private static final Logger LOGGER = LoggerFactory.getLogger(ChordalMelodyDissonance.class);

    public static final Map<Integer,Double> intervalMap = new HashMap<Integer,Double>() {{
        put(0, 0.0); //octaaf
        put(1, 0.0);
        put(2, 0.5);
        put(3, 0.5);
        put(4, 0.5);
        put(5, 1.0);
        put(6, 0.5);
        put(7, 1.0);
        put(8, 0.5);
        put(9, 0.5);
        put(10, 0.0);
        put(11, 0.0);
//	    put(12, ); //verminder melodisch gebruik
//	    put(13, ); // vermijd melodisch - harmonisch
    }};
    @Override
    public double getMelodicValue(int difference) {
        return intervalMap.get(Math.abs(difference % 12));
    }
}
