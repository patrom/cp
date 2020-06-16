package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.harmony.Chord;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class RotationalArrayTest {

    private RotationalArray rotationalArray;

    @Autowired
    private TnTnIType type;

    @Test
    void rotationalArray() {
//        int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
       // int[] pitchClasses = {0,4,8};
        Set[] prime3 = type.prime3;
        for (int k = 0; k < prime3.length; k++) {
            Set set = prime3[k];
            int[] pitchClasses = set.tntnitype;
            System.out.println("-------------------------------------------------");
            System.out.println(set.name + ": ");

            rotationalArray = new RotationalArray(pitchClasses);
            rotationalArray.show();
            for (int i = 0; i < pitchClasses.length; i++) {
                System.out.println("Rows: ");
                System.out.println(Arrays.toString(rotationalArray.getRow(i)));
                Chord chord = new Chord(rotationalArray.getRotationRow(i), 0);
                System.out.println(chord.getForteName());
            }

            for (int i = 0; i < pitchClasses.length; i++) {
                System.out.println("Columns: ");
                System.out.println(Arrays.toString(rotationalArray.getColumn(i)));
                Chord chord = new Chord(rotationalArray.getRotationColumn(i), 0);
                System.out.println(chord.getForteName());
            }
        }
    }
}