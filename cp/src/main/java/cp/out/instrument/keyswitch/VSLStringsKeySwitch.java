package cp.out.instrument.keyswitch;

import cp.out.instrument.Articulation;

/**
 * Created by prombouts on 5/12/2016.
 */
public class VSLStringsKeySwitch implements KeySwitch {

    @Override
    public int getArticulation(Articulation articulation) {
        switch (articulation){
            case LEGATO:
                return 26;
            case PORTATO:
                return 28;
            case MARCATO:
                return 27;
            case STACCATO:
                return 24;
            case STACCATISSIMO:
                return 24;
            case TENUTO:
                return 25;
            case SPICCATO:
                return 24;
        }
        return 26;
    }
}
