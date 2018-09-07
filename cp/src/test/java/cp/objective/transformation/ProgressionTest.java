package cp.objective.transformation;

import cp.DefaultConfig;
import cp.model.harmony.Chord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class ProgressionTest {

    @BeforeEach
    public void setUp() {

    }

    @ParameterizedTest
    @MethodSource("triadProvider")
    public void getTransformationTriadic(Chord chord, Chord nextChord) {
        System.out.print(chord);
        System.out.print(", " + nextChord);
        Transformation transformation = Progression.getTransformation(chord, nextChord);
        System.out.println(", Transformation: " + transformation);
    }

    @ParameterizedTest
    @MethodSource("tetraProvider")
    public void getTransformationTetraChord(Chord chord, Chord nextChord) {
        System.out.print(chord);
        System.out.print(", " + nextChord);
        Transformation transformation = Progression.getTransformation(chord, nextChord);
        System.out.println(", Transformation: " + transformation);
    }

    private static Stream tetraProvider() {
        Chord Cmaj = new Chord(0);
        Cmaj.addPitchClass(0);
        Cmaj.addPitchClass(4);
        Cmaj.addPitchClass(7);
        Cmaj.addPitchClass(10);

        Chord DFlatmaj = new Chord(1);
        DFlatmaj.addPitchClass(1);
        DFlatmaj.addPitchClass(5);
        DFlatmaj.addPitchClass(8);
        DFlatmaj.addPitchClass(11);

        Chord Dmaj = new Chord(2);
        Dmaj.addPitchClass(2);
        Dmaj.addPitchClass(6);
        Dmaj.addPitchClass(9);
        Dmaj.addPitchClass(0);

        Chord EFlatmaj = new Chord(3);
        EFlatmaj.addPitchClass(3);
        EFlatmaj.addPitchClass(7);
        EFlatmaj.addPitchClass(10);
        EFlatmaj.addPitchClass(1);

        Chord Emaj = new Chord(4);
        Emaj.addPitchClass(4);
        Emaj.addPitchClass(8);
        Emaj.addPitchClass(11);
        Emaj.addPitchClass(2);

        Chord Fmaj = new Chord(5);
        Fmaj.addPitchClass(5);
        Fmaj.addPitchClass(9);
        Fmaj.addPitchClass(0);
        Fmaj.addPitchClass(3);

        Chord FSharpmaj = new Chord(6);
        FSharpmaj.addPitchClass(6);
        FSharpmaj.addPitchClass(10);
        FSharpmaj.addPitchClass(1);
        FSharpmaj.addPitchClass(4);

        Chord Gmaj = new Chord(7);
        Gmaj.addPitchClass(7);
        Gmaj.addPitchClass(11);
        Gmaj.addPitchClass(2);
        Gmaj.addPitchClass(5);

        Chord AFlatmaj = new Chord(8);
        AFlatmaj.addPitchClass(8);
        AFlatmaj.addPitchClass(0);
        AFlatmaj.addPitchClass(3);
        AFlatmaj.addPitchClass(6);

        Chord Amaj = new Chord(9);
        Amaj.addPitchClass(9);
        Amaj.addPitchClass(1);
        Amaj.addPitchClass(4);
        Amaj.addPitchClass(7);

        Chord BFlatmaj = new Chord(10);
        BFlatmaj.addPitchClass(10);
        BFlatmaj.addPitchClass(2);
        BFlatmaj.addPitchClass(5);
        BFlatmaj.addPitchClass(8);

        Chord Bmaj = new Chord(11);
        Bmaj.addPitchClass(11);
        Bmaj.addPitchClass(3);
        Bmaj.addPitchClass(6);
        Bmaj.addPitchClass(9);


        Chord Cm = new Chord(0);
        Cm.addPitchClass(0);
        Cm.addPitchClass(3);
        Cm.addPitchClass(7);
        Cm.addPitchClass(10);

        Chord DFlatm = new Chord(1);
        DFlatm.addPitchClass(1);
        DFlatm.addPitchClass(4);
        DFlatm.addPitchClass(8);
        DFlatm.addPitchClass(11);

        Chord Dm = new Chord(2);
        Dm.addPitchClass(2);
        Dm.addPitchClass(5);
        Dm.addPitchClass(9);
        Dm.addPitchClass(0);

        Chord EFlatm = new Chord(3);
        EFlatm.addPitchClass(3);
        EFlatm.addPitchClass(6);
        EFlatm.addPitchClass(10);
        EFlatm.addPitchClass(1);

        Chord Em = new Chord(4);
        Em.addPitchClass(4);
        Em.addPitchClass(7);
        Em.addPitchClass(11);
        Em.addPitchClass(2);

        Chord Fm = new Chord(5);
        Fm.addPitchClass(5);
        Fm.addPitchClass(8);
        Fm.addPitchClass(0);
        Fm.addPitchClass(3);

        Chord FSharpm = new Chord(6);
        FSharpm.addPitchClass(6);
        FSharpm.addPitchClass(9);
        FSharpm.addPitchClass(1);
        FSharpm.addPitchClass(4);

        Chord Gm = new Chord(7);
        Gm.addPitchClass(7);
        Gm.addPitchClass(10);
        Gm.addPitchClass(2);
        Gm.addPitchClass(5);

        Chord AFlatm = new Chord(8);
        AFlatm.addPitchClass(8);
        AFlatm.addPitchClass(11);
        AFlatm.addPitchClass(3);
        AFlatm.addPitchClass(6);

        Chord Am = new Chord(9);
        Am.addPitchClass(9);
        Am.addPitchClass(0);
        Am.addPitchClass(4);
        Am.addPitchClass(7);

        Chord BFlatm = new Chord(10);
        BFlatm.addPitchClass(10);
        BFlatm.addPitchClass(1);
        BFlatm.addPitchClass(5);
        BFlatm.addPitchClass(8);

        Chord Bm = new Chord(11);
        Bm.addPitchClass(11);
        Bm.addPitchClass(2);
        Bm.addPitchClass(6);
        Bm.addPitchClass(9);

        return Stream.of(
                Arguments.of(Cmaj, Cmaj),
                Arguments.of(Cmaj, DFlatmaj),
                Arguments.of(Cmaj, Dmaj),
                Arguments.of(Cmaj, EFlatmaj),
                Arguments.of(Cmaj, Emaj),
                Arguments.of(Cmaj, Fmaj),
                Arguments.of(Cmaj, FSharpmaj),
                Arguments.of(Cmaj, Gmaj),
                Arguments.of(Cmaj, AFlatmaj),
                Arguments.of(Cmaj, Amaj),
                Arguments.of(Cmaj, BFlatmaj),
                Arguments.of(Cmaj, Bmaj),

                Arguments.of(Fmaj, Cmaj),
                Arguments.of(Gmaj, Cmaj),

                Arguments.of(Cmaj, Cm),
                Arguments.of(Cmaj, DFlatm),
                Arguments.of(Cmaj, Dm),
                Arguments.of(Cmaj, EFlatm),
                Arguments.of(Cmaj, Em),
                Arguments.of(Cmaj, Fm),
                Arguments.of(Cmaj, FSharpm),
                Arguments.of(Cmaj, Gm),
                Arguments.of(Cmaj, AFlatm),
                Arguments.of(Cmaj, Am),
                Arguments.of(Cmaj, BFlatm),
                Arguments.of(Cmaj, Bm),

                Arguments.of(Cm, Cm),
                Arguments.of(Cm, DFlatm),
                Arguments.of(Cm, Dm),
                Arguments.of(Cm, EFlatm),
                Arguments.of(Cm, Em),
                Arguments.of(Cm, Fm),
                Arguments.of(Cm, FSharpm),
                Arguments.of(Cm, Gm),
                Arguments.of(Cm, AFlatm),
                Arguments.of(Cm, Am),
                Arguments.of(Cm, BFlatm),
                Arguments.of(Cm, Bm),

                Arguments.of(Cm, Cmaj),
                Arguments.of(Cm, DFlatmaj),
                Arguments.of(Cm, Dmaj),
                Arguments.of(Cm, EFlatmaj),
                Arguments.of(Cm, Emaj),
                Arguments.of(Cm, Fmaj),
                Arguments.of(Cm, FSharpmaj),
                Arguments.of(Cm, Gmaj),
                Arguments.of(Cm, AFlatmaj),
                Arguments.of(Cm, Amaj),
                Arguments.of(Cm, BFlatmaj),
                Arguments.of(Cm, Bmaj)
        );
    }

    private static Stream triadProvider() {
        Chord Cmaj = new Chord(0);
        Cmaj.addPitchClass(0);
        Cmaj.addPitchClass(4);
        Cmaj.addPitchClass(7);

        Chord DFlatmaj = new Chord(1);
        DFlatmaj.addPitchClass(1);
        DFlatmaj.addPitchClass(5);
        DFlatmaj.addPitchClass(8);

        Chord Dmaj = new Chord(2);
        Dmaj.addPitchClass(2);
        Dmaj.addPitchClass(6);
        Dmaj.addPitchClass(9);

        Chord EFlatmaj = new Chord(3);
        EFlatmaj.addPitchClass(3);
        EFlatmaj.addPitchClass(7);
        EFlatmaj.addPitchClass(10);

        Chord Emaj = new Chord(4);
        Emaj.addPitchClass(4);
        Emaj.addPitchClass(8);
        Emaj.addPitchClass(11);

        Chord Fmaj = new Chord(5);
        Fmaj.addPitchClass(5);
        Fmaj.addPitchClass(9);
        Fmaj.addPitchClass(0);

        Chord FSharpmaj = new Chord(6);
        FSharpmaj.addPitchClass(6);
        FSharpmaj.addPitchClass(10);
        FSharpmaj.addPitchClass(1);

        Chord Gmaj = new Chord(7);
        Gmaj.addPitchClass(7);
        Gmaj.addPitchClass(11);
        Gmaj.addPitchClass(2);

        Chord AFlatmaj = new Chord(8);
        AFlatmaj.addPitchClass(8);
        AFlatmaj.addPitchClass(0);
        AFlatmaj.addPitchClass(3);

        Chord Amaj = new Chord(9);
        Amaj.addPitchClass(9);
        Amaj.addPitchClass(1);
        Amaj.addPitchClass(4);

        Chord BFlatmaj = new Chord(10);
        BFlatmaj.addPitchClass(10);
        BFlatmaj.addPitchClass(2);
        BFlatmaj.addPitchClass(5);

        Chord Bmaj = new Chord(11);
        Bmaj.addPitchClass(11);
        Bmaj.addPitchClass(3);
        Bmaj.addPitchClass(6);


        Chord Cm = new Chord(0);
        Cm.addPitchClass(0);
        Cm.addPitchClass(3);
        Cm.addPitchClass(7);

        Chord DFlatm = new Chord(1);
        DFlatm.addPitchClass(1);
        DFlatm.addPitchClass(4);
        DFlatm.addPitchClass(8);

        Chord Dm = new Chord(2);
        Dm.addPitchClass(2);
        Dm.addPitchClass(5);
        Dm.addPitchClass(9);

        Chord EFlatm = new Chord(3);
        EFlatm.addPitchClass(3);
        EFlatm.addPitchClass(6);
        EFlatm.addPitchClass(10);

        Chord Em = new Chord(4);
        Em.addPitchClass(4);
        Em.addPitchClass(7);
        Em.addPitchClass(11);

        Chord Fm = new Chord(5);
        Fm.addPitchClass(5);
        Fm.addPitchClass(8);
        Fm.addPitchClass(0);

        Chord FSharpm = new Chord(6);
        FSharpm.addPitchClass(6);
        FSharpm.addPitchClass(9);
        FSharpm.addPitchClass(1);

        Chord Gm = new Chord(7);
        Gm.addPitchClass(7);
        Gm.addPitchClass(10);
        Gm.addPitchClass(2);

        Chord AFlatm = new Chord(8);
        AFlatm.addPitchClass(8);
        AFlatm.addPitchClass(11);
        AFlatm.addPitchClass(3);

        Chord Am = new Chord(9);
        Am.addPitchClass(9);
        Am.addPitchClass(0);
        Am.addPitchClass(4);

        Chord BFlatm = new Chord(10);
        BFlatm.addPitchClass(10);
        BFlatm.addPitchClass(1);
        BFlatm.addPitchClass(5);

        Chord Bm = new Chord(11);
        Bm.addPitchClass(11);
        Bm.addPitchClass(2);
        Bm.addPitchClass(6);

        return Stream.of(
                Arguments.of(Cmaj, Cmaj),
                Arguments.of(Cmaj, DFlatmaj),
                Arguments.of(Cmaj, Dmaj),
                Arguments.of(Cmaj, EFlatmaj),
                Arguments.of(Cmaj, Emaj),
                Arguments.of(Cmaj, Fmaj),
                Arguments.of(Cmaj, FSharpmaj),
                Arguments.of(Cmaj, Gmaj),
                Arguments.of(Cmaj, AFlatmaj),
                Arguments.of(Cmaj, Amaj),
                Arguments.of(Cmaj, BFlatmaj),
                Arguments.of(Cmaj, Bmaj),

                Arguments.of(Fmaj, Cmaj),
                Arguments.of(Gmaj, Cmaj),

                Arguments.of(Cmaj, Cm),
                Arguments.of(Cmaj, DFlatm),
                Arguments.of(Cmaj, Dm),
                Arguments.of(Cmaj, EFlatm),
                Arguments.of(Cmaj, Em),
                Arguments.of(Cmaj, Fm),
                Arguments.of(Cmaj, FSharpm),
                Arguments.of(Cmaj, Gm),
                Arguments.of(Cmaj, AFlatm),
                Arguments.of(Cmaj, Am),
                Arguments.of(Cmaj, BFlatm),
                Arguments.of(Cmaj, Bm),

                Arguments.of(Cm, Cm),
                Arguments.of(Cm, DFlatm),
                Arguments.of(Cm, Dm),
                Arguments.of(Cm, EFlatm),
                Arguments.of(Cm, Em),
                Arguments.of(Cm, Fm),
                Arguments.of(Cm, FSharpm),
                Arguments.of(Cm, Gm),
                Arguments.of(Cm, AFlatm),
                Arguments.of(Cm, Am),
                Arguments.of(Cm, BFlatm),
                Arguments.of(Cm, Bm),

                Arguments.of(Cm, Cmaj),
                Arguments.of(Cm, DFlatmaj),
                Arguments.of(Cm, Dmaj),
                Arguments.of(Cm, EFlatmaj),
                Arguments.of(Cm, Emaj),
                Arguments.of(Cm, Fmaj),
                Arguments.of(Cm, FSharpmaj),
                Arguments.of(Cm, Gmaj),
                Arguments.of(Cm, AFlatmaj),
                Arguments.of(Cm, Amaj),
                Arguments.of(Cm, BFlatmaj),
                Arguments.of(Cm, Bmaj)
        );
    }
}