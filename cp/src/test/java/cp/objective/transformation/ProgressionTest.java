package cp.objective.transformation;

import cp.DefaultConfig;
import cp.generator.PCGenerator;
import cp.model.harmony.Chord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class ProgressionTest {

    @Autowired
    private PCGenerator pcGenerator;

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

    @ParameterizedTest
    @MethodSource("triadTetraProvider")
    public void getTransformationTriadicTetra(Chord chord, Chord nextChord) {
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

    private static Stream triadTetraProvider() {
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

        //tetra
        Chord Cmaj7 = new Chord(0);
        Cmaj7.addPitchClass(0);
        Cmaj7.addPitchClass(4);
        Cmaj7.addPitchClass(7);
        Cmaj7.addPitchClass(10);

        Chord DFlatmaj7 = new Chord(1);
        DFlatmaj7.addPitchClass(1);
        DFlatmaj7.addPitchClass(5);
        DFlatmaj7.addPitchClass(8);
        DFlatmaj7.addPitchClass(11);

        Chord Dmaj7 = new Chord(2);
        Dmaj7.addPitchClass(2);
        Dmaj7.addPitchClass(6);
        Dmaj7.addPitchClass(9);
        Dmaj7.addPitchClass(0);

        Chord EFlatmaj7 = new Chord(3);
        EFlatmaj7.addPitchClass(3);
        EFlatmaj7.addPitchClass(7);
        EFlatmaj7.addPitchClass(10);
        EFlatmaj7.addPitchClass(1);

        Chord Emaj7 = new Chord(4);
        Emaj7.addPitchClass(4);
        Emaj7.addPitchClass(8);
        Emaj7.addPitchClass(11);
        Emaj7.addPitchClass(2);

        Chord Fmaj7 = new Chord(5);
        Fmaj7.addPitchClass(5);
        Fmaj7.addPitchClass(9);
        Fmaj7.addPitchClass(0);
        Fmaj7.addPitchClass(3);

        Chord FSharpmaj7 = new Chord(6);
        FSharpmaj7.addPitchClass(6);
        FSharpmaj7.addPitchClass(10);
        FSharpmaj7.addPitchClass(1);
        FSharpmaj7.addPitchClass(4);

        Chord Gmaj7 = new Chord(7);
        Gmaj7.addPitchClass(7);
        Gmaj7.addPitchClass(11);
        Gmaj7.addPitchClass(2);
        Gmaj7.addPitchClass(5);

        Chord AFlatmaj7 = new Chord(8);
        AFlatmaj7.addPitchClass(8);
        AFlatmaj7.addPitchClass(0);
        AFlatmaj7.addPitchClass(3);
        AFlatmaj7.addPitchClass(6);

        Chord Amaj7 = new Chord(9);
        Amaj7.addPitchClass(9);
        Amaj7.addPitchClass(1);
        Amaj7.addPitchClass(4);
        Amaj7.addPitchClass(7);

        Chord BFlatmaj7 = new Chord(10);
        BFlatmaj7.addPitchClass(10);
        BFlatmaj7.addPitchClass(2);
        BFlatmaj7.addPitchClass(5);
        BFlatmaj7.addPitchClass(8);

        Chord Bmaj7 = new Chord(11);
        Bmaj7.addPitchClass(11);
        Bmaj7.addPitchClass(3);
        Bmaj7.addPitchClass(6);
        Bmaj7.addPitchClass(9);


        Chord Cm7 = new Chord(0);
        Cm7.addPitchClass(0);
        Cm7.addPitchClass(3);
        Cm7.addPitchClass(7);
        Cm7.addPitchClass(10);

        Chord DFlatm7 = new Chord(1);
        DFlatm7.addPitchClass(1);
        DFlatm7.addPitchClass(4);
        DFlatm7.addPitchClass(8);
        DFlatm7.addPitchClass(11);

        Chord Dm7 = new Chord(2);
        Dm7.addPitchClass(2);
        Dm7.addPitchClass(5);
        Dm7.addPitchClass(9);
        Dm7.addPitchClass(0);

        Chord EFlatm7 = new Chord(3);
        EFlatm7.addPitchClass(3);
        EFlatm7.addPitchClass(6);
        EFlatm7.addPitchClass(10);
        EFlatm7.addPitchClass(1);

        Chord Em7 = new Chord(4);
        Em7.addPitchClass(4);
        Em7.addPitchClass(7);
        Em7.addPitchClass(11);
        Em7.addPitchClass(2);

        Chord Fm7 = new Chord(5);
        Fm7.addPitchClass(5);
        Fm7.addPitchClass(8);
        Fm7.addPitchClass(0);
        Fm7.addPitchClass(3);

        Chord FSharpm7 = new Chord(6);
        FSharpm7.addPitchClass(6);
        FSharpm7.addPitchClass(9);
        FSharpm7.addPitchClass(1);
        FSharpm7.addPitchClass(4);

        Chord Gm7 = new Chord(7);
        Gm7.addPitchClass(7);
        Gm7.addPitchClass(10);
        Gm7.addPitchClass(2);
        Gm7.addPitchClass(5);

        Chord AFlatm7 = new Chord(8);
        AFlatm7.addPitchClass(8);
        AFlatm7.addPitchClass(11);
        AFlatm7.addPitchClass(3);
        AFlatm7.addPitchClass(6);

        Chord Am7 = new Chord(9);
        Am7.addPitchClass(9);
        Am7.addPitchClass(0);
        Am7.addPitchClass(4);
        Am7.addPitchClass(7);

        Chord BFlatm7 = new Chord(10);
        BFlatm7.addPitchClass(10);
        BFlatm7.addPitchClass(1);
        BFlatm7.addPitchClass(5);
        BFlatm7.addPitchClass(8);

        Chord Bm7 = new Chord(11);
        Bm7.addPitchClass(11);
        Bm7.addPitchClass(2);
        Bm7.addPitchClass(6);
        Bm7.addPitchClass(9);

        return Stream.of(
                Arguments.of(Cmaj, Cmaj7),
                Arguments.of(Cmaj, DFlatmaj7),
                Arguments.of(Cmaj, Dmaj7),
                Arguments.of(Cmaj, EFlatmaj7),
                Arguments.of(Cmaj, Emaj7),
                Arguments.of(Cmaj, Fmaj7),
                Arguments.of(Cmaj, FSharpmaj7),
                Arguments.of(Cmaj, Gmaj7),
                Arguments.of(Cmaj, AFlatmaj7),
                Arguments.of(Cmaj, Amaj7),
                Arguments.of(Cmaj, BFlatmaj7),
                Arguments.of(Cmaj, Bmaj7),

                Arguments.of(Fmaj, Cmaj7),
                Arguments.of(Gmaj, Cmaj7),

                Arguments.of(Cmaj, Cm7),
                Arguments.of(Cmaj, DFlatm7),
                Arguments.of(Cmaj, Dm7),
                Arguments.of(Cmaj, EFlatm7),
                Arguments.of(Cmaj, Em7),
                Arguments.of(Cmaj, Fm7),
                Arguments.of(Cmaj, FSharpm7),
                Arguments.of(Cmaj, Gm7),
                Arguments.of(Cmaj, AFlatm7),
                Arguments.of(Cmaj, Am7),
                Arguments.of(Cmaj, BFlatm7),
                Arguments.of(Cmaj, Bm7),

                Arguments.of(Cm, Cm7),
                Arguments.of(Cm, DFlatm7),
                Arguments.of(Cm, Dm7),
                Arguments.of(Cm, EFlatm7),
                Arguments.of(Cm, Em7),
                Arguments.of(Cm, Fm7),
                Arguments.of(Cm, FSharpm7),
                Arguments.of(Cm, Gm7),
                Arguments.of(Cm, AFlatm7),
                Arguments.of(Cm, Am7),
                Arguments.of(Cm, BFlatm7),
                Arguments.of(Cm, Bm7),

                Arguments.of(Cm, Cmaj7),
                Arguments.of(Cm, DFlatmaj7),
                Arguments.of(Cm, Dmaj7),
                Arguments.of(Cm, EFlatmaj7),
                Arguments.of(Cm, Emaj7),
                Arguments.of(Cm, Fmaj7),
                Arguments.of(Cm, FSharpmaj7),
                Arguments.of(Cm, Gmaj7),
                Arguments.of(Cm, AFlatmaj7),
                Arguments.of(Cm, Amaj7),
                Arguments.of(Cm, BFlatmaj7),
                Arguments.of(Cm, Bmaj7)
        );
    }


}