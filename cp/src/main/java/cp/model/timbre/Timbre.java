package cp.model.timbre;

import cp.model.note.Dynamic;
import cp.out.instrument.Articulation;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class Timbre {

    private Articulation articulation;
    private Dynamic dynamic = Dynamic.MF;
    private Technical technical = Technical.LEGATO;

    public Timbre(Articulation articulation, Technical technical, Dynamic dynamic) {
        this.articulation = articulation;
        this.technical = technical;
        this.dynamic = dynamic;
    }

    public Timbre() {
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());

        stringArticulations = Stream.of(
                Articulation.MARCATO,
                Articulation.STRONG_ACCENT,
                Articulation.STACCATO,
                Articulation.TENUTO,
                Articulation.DETACHED_LEGATO,//a tenuto line and staccato dot
                Articulation.STACCATISSIMO,
                Articulation.SPICCATO
        ).collect(toList());
        woodwindArticulations = Stream.of(
                Articulation.MARCATO,
                Articulation.STRONG_ACCENT,
                Articulation.STACCATO,
                Articulation.TENUTO,
                Articulation.DETACHED_LEGATO,//a tenuto line and staccato dot
                Articulation.STACCATISSIMO
        ).collect(toList());

        stringTechnicals = Stream.of(
                Technical.LEGATO,
                Technical.PIZZ,
                Technical.VIBRATO,
                Technical.PORTATO,
                Technical.SENZA_VIBRATO,
                Technical.STACCATO
//                Technical.SUL_PONTICELLO
        ).collect(toList());

        woodwindTechnicals = Stream.of(
                Technical.LEGATO,
                Technical.VIBRATO,
                Technical.PORTATO,
                Technical.SENZA_VIBRATO,
                Technical.STACCATO
//                Technical.FLUTTER_TONGUE
        ).collect(toList());

        dynamics = Stream.of(
                Dynamic.F,
                Dynamic.MF,
                Dynamic.MP,
                Dynamic.P
        ).collect(toList());
    }

    protected List<Articulation> stringArticulations = new ArrayList<>();
    protected List<Articulation> woodwindArticulations = new ArrayList<>();
    protected List<Dynamic> dynamics;

    protected List<Technical> woodwindTechnicals = new ArrayList<>();
    protected List<Technical> stringTechnicals = new ArrayList<>();

    public List<Articulation> getArticulations(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
                return stringArticulations;
            case WOODWINDS:
            case BRASS:
                return woodwindArticulations;
        }
        return emptyList();
    }

    public List<Technical> getTechnicals(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
                return stringTechnicals;
            case WOODWINDS:
            case BRASS:
                return woodwindTechnicals;
        }
        return emptyList();
    }

    public List<Dynamic> getDynamics() {
        return dynamics;
    }

    public List<Dynamic> getDynamics(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
            case WOODWINDS:
            case BRASS:
                return Arrays.asList(Dynamic.values());
        }
        return emptyList();
    }

    public Articulation getArticulation() {
        return articulation;
    }

    public Technical getTechnical() {
        return technical;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamics(List<Dynamic> dynamics) {
        this.dynamics = dynamics;
    }
}
