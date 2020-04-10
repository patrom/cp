package cp.config;

import cp.out.orchestration.quality.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prombouts on 24/03/2017.
 */
@Component
public class ColorQualityConfig {

    @Autowired
    private BrilliantWhite brilliantWhite;
    @Autowired
    private BrightYellow brightYellow;
    @Autowired
    private PleasantGreen pleasantGreen;
    @Autowired
    private RichBlue richBlue;
    @Autowired
    private GoldenOrange goldenOrange;
    @Autowired
    private GlowingRed glowingRed;
    @Autowired
    private MellowPurple mellowPurple;
    @Autowired
    private WarmBrown warmBrown;


    @Autowired
    private High high;
    @Autowired
    private HighRange highRange;
    @Autowired
    private MediumRange mediumRange;
    @Autowired
    private LowRange lowRange;

    @Autowired
    private WoodWindHigh woodWindHigh;
    @Autowired
    private WoodWindMiddle woodWindMiddle;
    @Autowired
    private WoodWindsLow woodWindsLow;

    private Map<Integer, OrchestralQuality> orchestralQualities = new TreeMap<>();

    @PostConstruct
    public void init() {
        //voice!!!
        orchestralQualities.put(0, richBlue);
        orchestralQualities.put(1, richBlue);
        orchestralQualities.put(2, pleasantGreen);
        orchestralQualities.put(3, mediumRange);
        orchestralQualities.put(4, mediumRange);
        orchestralQualities.put(5, goldenOrange);
    }

    public OrchestralQuality getOchestralQualityForVoice(int voice){
        return orchestralQualities.get(voice);
    }

}
