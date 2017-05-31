package cp.out.orchestration.quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prombouts on 24/03/2017.
 */
@Component
public class OrchestralQualityConfig {

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
    private MediumRange mediumRange;
    @Autowired
    private LowRange lowRange;

    private Map<Integer, OrchestralQuality> orchestralQualities = new TreeMap<>();

    @PostConstruct
    public void init() {
        //voice!!!
        orchestralQualities.put(0, richBlue);
        orchestralQualities.put(1, richBlue);
        orchestralQualities.put(2, pleasantGreen);
        orchestralQualities.put(3, pleasantGreen);
    }

    public OrchestralQuality getOchestralQualityForVoice(int voice){
        return orchestralQualities.get(voice);
    }

}
