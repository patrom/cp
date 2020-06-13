package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.harmony.Chord;
import cp.model.note.Scale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class RotationalArrayTest {

    private RotationalArray rotationalArray;

    @Test
    void rotationalArray() {
//        int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
        int[] pitchClasses = {0,3,4,6,2};
        rotationalArray = new RotationalArray(pitchClasses);
        rotationalArray.show();
        for (int i = 0; i < pitchClasses.length; i++) {
            System.out.println("Rows: ");
            System.out.println(Arrays.toString(rotationalArray.getRow(i)));
            List<Integer> integers = Arrays.stream(rotationalArray.getRow(i)).boxed().collect(Collectors.toList());
            Chord chord = new Chord(integers, integers.get(0));
            System.out.println(chord.getForteName());
        }

        for (int i = 0; i < pitchClasses.length; i++) {
            System.out.println("Columns: ");
            System.out.println(Arrays.toString(rotationalArray.getColumn(i)));
            List<Integer> integers = Arrays.stream(rotationalArray.getColumn(i)).boxed().collect(Collectors.toList());
            Chord chord = new Chord(integers, integers.get(0));
            System.out.println(chord.getForteName());
        }

    }
}