package cp.out.orchestration.quality;

import cp.out.instrument.brass.*;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.voice.Bass;
import cp.out.instrument.woodwinds.*;
import cp.out.orchestration.OrchestraMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicCombinations {

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

    public List<OrchestraMapping> whiteCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Piccolo(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet1(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet2(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), brilliantWhite));
        return orchestralMappings;
    }

    public List<OrchestraMapping> whiteOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Piccolo(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), brilliantWhite));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), brilliantWhite));

        orchestralMappings.add(new OrchestraMapping(2, new Cello(), brilliantWhite));
        return orchestralMappings;
    }

    public List<OrchestraMapping> yellowCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Celesta(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Piano(), brightYellow));

        orchestralMappings.add(new OrchestraMapping(2, new Flute(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), brightYellow));

        orchestralMappings.add(new OrchestraMapping(2, new Piccolo(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new ClarinetEFlat(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet2(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone2(), brightYellow));
        return orchestralMappings;
    }

    public List<OrchestraMapping> yellowOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Flute(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), brightYellow));

        orchestralMappings.add(new OrchestraMapping(2, new Viola(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet2(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone2(), brightYellow));

        orchestralMappings.add(new OrchestraMapping(2, new Celesta(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Piano(), brightYellow));
        orchestralMappings.add(new OrchestraMapping(2, new Harp(), brightYellow));
        return orchestralMappings;
    }

    public List<OrchestraMapping> greenCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Celesta(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Piano(), pleasantGreen));

        orchestralMappings.add(new OrchestraMapping(2, new Flute(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), pleasantGreen));
        return orchestralMappings;
    }

    public List<OrchestraMapping> greenOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Flute(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new ClarinetEFlat(), pleasantGreen));
//        orchestralMappings.add(new OrchestraMapping(2, new Viola(), pleasantGreen));
        orchestralMappings.add(new OrchestraMapping(2, new Bassoon(), pleasantGreen));

//        orchestralMappings.add(new OrchestraMapping(2, new BassClarinet(), pleasantGreen));
//        orchestralMappings.add(new OrchestraMapping(2, new Cello(), pleasantGreen));
//        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), pleasantGreen));
//
//        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), pleasantGreen));
//        orchestralMappings.add(new OrchestraMapping(2, new Viola(), pleasantGreen));
        return orchestralMappings;
    }

    public List<OrchestraMapping> blueCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Celesta(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new Piano(), richBlue));

        orchestralMappings.add(new OrchestraMapping(2, new Flute(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), richBlue));

        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), richBlue));

        orchestralMappings.add(new OrchestraMapping(2, new BassClarinet(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new Cello(), richBlue));
        return orchestralMappings;
    }

    public List<OrchestraMapping> blueOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Clarinet(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new BassClarinet(), richBlue));

        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new Viola(), richBlue));

        orchestralMappings.add(new OrchestraMapping(2, new Viola(), richBlue));
        orchestralMappings.add(new OrchestraMapping(2, new Cello(), richBlue));
        return orchestralMappings;
    }

    public List<OrchestraMapping> orangeCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
//        orchestralMappings.add(new OrchestraMapping(2, new Harp(), goldenOrange));
//        orchestralMappings.add(new OrchestraMapping(2, new Xylophone(), goldenOrange));

        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), goldenOrange));
        return orchestralMappings;
    }

    public List<OrchestraMapping> orangeOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new AltoFlute(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), goldenOrange));

        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), goldenOrange));

        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet2(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), goldenOrange));

        orchestralMappings.add(new OrchestraMapping(2, new Trumpet2(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone2(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), goldenOrange));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), goldenOrange));
        return orchestralMappings;
    }

    public List<OrchestraMapping> redCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), glowingRed));

        orchestralMappings.add(new OrchestraMapping(2, new AltoFlute(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), glowingRed));//Sul G
        return orchestralMappings;
    }

    public List<OrchestraMapping> redOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new AltoFlute(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), glowingRed));

        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new Oboe(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), glowingRed));

        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), glowingRed));//Sul G
        orchestralMappings.add(new OrchestraMapping(2, new CorAnglais(), glowingRed));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), glowingRed));
        return orchestralMappings;
    }

    public List<OrchestraMapping> purpleCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new Piano(), mellowPurple));

        orchestralMappings.add(new OrchestraMapping(2, new Cello(), mellowPurple));
        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), mellowPurple));
        orchestralMappings.add(new OrchestraMapping(2, new Tuba(), mellowPurple));
        return orchestralMappings;
    }

    public List<OrchestraMapping> purpleOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), mellowPurple));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), mellowPurple));

        orchestralMappings.add(new OrchestraMapping(2, new Tuba(), mellowPurple));
        orchestralMappings.add(new OrchestraMapping(2, new ViolinsI(), mellowPurple));
        return orchestralMappings;
    }

    public List<OrchestraMapping> brownsCorrespondingRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new AltoFlute(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), warmBrown));

        orchestralMappings.add(new OrchestraMapping(2, new Harp(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new Trombone(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));//high

        orchestralMappings.add(new OrchestraMapping(2, new BassTrombone(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));//low

        orchestralMappings.add(new OrchestraMapping(2, new Bass(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));
        return orchestralMappings;
    }

    public List<OrchestraMapping> brownsOctaveRange() {
        List<OrchestraMapping> orchestralMappings = new ArrayList<>();
        orchestralMappings.add(new OrchestraMapping(2, new AltoFlute(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));

        orchestralMappings.add(new OrchestraMapping(2, new FrenchHorn(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));//high

        orchestralMappings.add(new OrchestraMapping(2, new Trumpet(), warmBrown));
        orchestralMappings.add(new OrchestraMapping(2, new ContraBassoon(), warmBrown));//low
        return orchestralMappings;
    }

}
