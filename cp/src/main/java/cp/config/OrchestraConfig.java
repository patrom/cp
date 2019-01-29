package cp.config;

import cp.midi.MelodyInstrument;
import cp.out.instrument.strings.*;
import cp.out.orchestration.OrchestraMapping;
import cp.out.orchestration.quality.*;
import cp.out.play.InstrumentMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrchestraConfig {

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
    private InstrumentConfig instrumentConfig;

    private List<OrchestraMapping> orchestralMappings = new ArrayList<>();

    @Autowired
    private BasicCombinations basicCombinations;

    @PostConstruct
    public void init() {

//        List<Instrument> basicInstrumentsWoodwinds = goldenOrange.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
//        List<Instrument> basicInstrumentsBrass = goldenOrange.getBasicInstrumentsByGroup(InstrumentGroup.BRASS);
//        basicInstrumentsWoodwinds.addAll(basicInstrumentsBrass);
//        List<Instrument> instruments = basicInstrumentsWoodwinds.stream().filter(instrument -> instrumentConfig.contains(instrument)).collect(Collectors.toList());


//        for (Instrument instrument : instruments) {
//            orchestralMappings.add(new OrchestraMapping(2, instrument, goldenOrange));
//        }
//        List<Instrument> basicInstrumentsBrass = richBlue.getBasicInstrumentsByGroup(InstrumentGroup.BRASS);
//        for (int i = 0; i < 3; i++) {
//            Instrument instrument = RandomUtil.getRandomFromList(basicInstrumentsWoodwinds);
//            orchestralMappings.put(i, new OrchestraMapping(2, instrument, richBlue));
//            basicInstrumentsWoodwinds.remove(instrument);
//        }

//        orchestralMappings = basicCombinations.orangeCorrespondingRange();
        //voice!!!
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), brightYellow));
        orchestralMappings.add( new OrchestraMapping(2, new ViolinsII(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Cello(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new DoubleBass(), lowRange));
//        orchestralMappings.add(new OrchestraMapping(2, new Flute(), pleasantGreen));
    }

    public List<OrchestraMapping> getOrchestralMappings() {
        return orchestralMappings;
    }

    public List<MelodyInstrument> getMelodyInstruments() {
        List<MelodyInstrument> melodyInstruments = new ArrayList<>();
        for (OrchestraMapping orchestraMapping : orchestralMappings) {
            InstrumentMapping instrumentMapping = instrumentConfig.getInstrumentMappingByBame(orchestraMapping.getInstrument().getInstrumentName());
            MelodyInstrument melodyInstrument = new MelodyInstrument();
            melodyInstrument.setNotes(orchestraMapping.getNotes());
            melodyInstrument.setInstrumentMapping(instrumentMapping);
            melodyInstruments.add(melodyInstrument);
        }
        return  melodyInstruments;
    }
}
