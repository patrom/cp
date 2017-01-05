package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 4/01/2017.
 */
@Component(value = "torkeDissonance")
public class TorkeDissonance implements Dissonance {

    @Override
    public double getDissonance(Chord chord) {
        switch (chord.getForteName()) {
            case "4-11":
                return 1.0;
            case "4-14":
                return 1.0;
            case "4-16":
                return 1.0;
            case "4-19":
                return 1.0;
            case "4-20":
                return 1.0;
            case "4-21":
                return 1.0;
            case "4-22":
                return 1.0;
            case "4-23":
                return 1.0;
            case "4-26":
                return 1.0;
            case "4-27":
                return 1.0;
            case "4-Z29":
                return 1.0;


            case "5-27":
                return 1.0;
            case "5-23":
                return 1.0;
            case "5-25":
                return 1.0;
            case "5-35":
                return 1.0;
        }
        return 0;
    }

}