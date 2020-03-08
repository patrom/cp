package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.harmony.Chord;
import cp.model.setclass.PcSet;
import cp.model.setclass.PcSetUnorderedProperties;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class ChordGeneratorTest {

    @Autowired
    private ChordGenerator chordGenerator;

    @Autowired
    private TnTnIType type;

    @Test
    void generateNeoRiemannTrichordalSetClass() {
//        Definition 3.1. P~(<a, b, c>) = Ia + c.
//        Definition 3.2. L~(<a, b, c>) = Ia + b if the set is prime or Ib + c if the set is inverted.
//        Definition 3.3. R~(<a, b, c>) = Ib + c if the set is prime or Ia + b if the set is inverted.
        Set[] prime3 = type.prime3;
        for (int i = 0; i < prime3.length; i++) {
            Set set = prime3[i];
            int[] pcs = set.tntnitype;

            System.out.print(set.name + ": ");
            print(pcs);
            //P
            System.out.print("P: ");
            int tranposition = pcs[0] + pcs[2];
            int[] invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("L: ");
            //L
            tranposition = pcs[0] + pcs[1];
            invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("R: ");
            //P
            tranposition = pcs[1] + pcs[2];
            invertedPcs = getInts(pcs, tranposition);
//        PcSet pcSet = new PcSet(pcs);
//        int[] ints = pcSet.invertPcSet(pcs);
            print(invertedPcs);
            System.out.println();
        }
    }

    @Test
    void generateNeoRiemannTetrachordalSetClass1() {
//        Definition 3.8. P~(<a, b, c, d>) = Ia + d.
//        Definition 3.9. L~(<a, b, c, d>) = Ia + b if the set is prime or Ic + d if the set is inverted.
//        Definition 3.10. R~(<a, b, c, d>) = Ia + c if the set is prime or Ib + d if the set is inverted.
        Set[] prime4 = type.prime4;
        for (int i = 0; i < prime4.length; i++) {
            Set set = prime4[i];
            int[] pcs = set.tntnitype;

            System.out.print(set.name + ": ");
            print(pcs);
            //P
            System.out.print("P: ");
            int tranposition = pcs[0] + pcs[3];
            int[] invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("L: ");
            //L
            tranposition = pcs[0] + pcs[1];
            invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("R: ");
            //R
            tranposition = pcs[0] + pcs[2];
            invertedPcs = getInts(pcs, tranposition);
//        PcSet pcSet = new PcSet(pcs);
//        int[] ints = pcSet.invertPcSet(pcs);
            print(invertedPcs);
            System.out.println();
        }
    }

    @Test
    void generateNeoRiemannTetrachordalSetClass2() {
//          Definition 3.11. P’(<a, b, c, d>) = Ib + c.
//          Definition 3.12. L’(<a, b, c, d>) = Ic + d if the set is prime or Ia + b if the set is inverted.
//          Definition 3.13. R’(<a, b, c, d>) = Ib + d if the set is prime or Ia + c if the set is inverted.
        Set[] prime4 = type.prime4;
        for (int i = 0; i < prime4.length; i++) {
            Set set = prime4[i];
            int[] pcs = set.tntnitype;

            System.out.print(set.name + ": ");
            print(pcs);
            //P
            System.out.print("P': ");
            int tranposition = pcs[1] + pcs[2];
            int[] invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("L': ");
            //L
            tranposition = pcs[2] + pcs[3];
            invertedPcs = getInts(pcs, tranposition);
            print(invertedPcs);

            System.out.print("R': ");
            //R
            tranposition = pcs[1] + pcs[3];
            invertedPcs = getInts(pcs, tranposition);
//        PcSet pcSet = new PcSet(pcs);
//        int[] ints = pcSet.invertPcSet(pcs);
            print(invertedPcs);
            System.out.println();
        }
    }

    private void print(int[] invertedPcs) {
        System.out.print(Arrays.toString(invertedPcs));
        int sum = Arrays.stream(invertedPcs).sum() % 12;
        System.out.println(" SUM: " + sum + " ForteName:  " +  new PcSetUnorderedProperties(invertedPcs).getForteName());
    }

    private int[] getInts(int[] pcs, int tranposition) {
        int [] invertedPcs = new int[pcs.length];
        for (int j = 0; j < pcs.length; j++) {
            int pc = pcs[j];
            int invertPc = (12 - pc) % 12;
            int inverted = (invertPc + tranposition) % 12;
            invertedPcs[j] = inverted;
        }
        return invertedPcs;
    }

    @Test
    void generateChord() {
    }

    @Test
    void generatePitchClasses() {
    }

    @Test
    void generatePcs() {
    }

    @Test
    void generatePitches() {
    }

    @Test
    void getIntervalVector() {
    }
}