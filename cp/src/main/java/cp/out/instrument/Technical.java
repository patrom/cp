package cp.out.instrument;

/**
 * Created by prombouts on 5/04/2017.
 */
public enum Technical {

    LEGATO("legato"),
    STACCATO("staccato"),
    SUL_TASTO("sul tasto"),
    PIZZ("pizz"),
    CON_SORDINO("con sordino"),
    ARCO("arco"),
    COL_LEGNO("col legno"),
    SUL_PONTICELLO("sul ponticello"),
    DETACHE_SHORT("short"),
    SLAP("slap"),//flute/saxophones
    PORTATO("portato"),//detache short
    FLUTTER_TONGUE("flutter tongue"),
    VIBRATO("vibrato"),
    SENZA_VIBRATO ("senza vibrato");

    private String technical;

    Technical(String technical) {
        this.technical = technical;
    }

    public String getTechnical() {
        return technical;
    }
}
