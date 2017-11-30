package cp.objective.melody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChromaticeMelodyDissonance implements MelodyDissonance{

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromaticeMelodyDissonance.class);

    public static final Map<Integer,Double> intervalMap = new HashMap<Integer,Double>() {{
        put(0, 0.5); //octaaf
        put(1, 1.0);
        put(2, 0.7);
        put(3, 0.5);
        put(4, 0.5);
        put(5, 0.2);
        put(6, 0.2);
        put(7, 0.2);
        put(8, 0.1);
        put(9, 0.1);
        put(10, 0.1);
        put(11, 0.1);
//	    put(12, ); //verminder melodisch gebruik
//	    put(13, ); // vermijd melodisch - harmonisch
    }};
    @Override
    public double getMelodicValue(int difference) {
        return intervalMap.get(Math.abs(difference % 12));
//        LOGGER.info("diff: " + difference + ", value: " + aDouble);
//        return aDouble;
    }
}
