package cp.model.note;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.TimeLineKey;
import cp.model.setclass.PcSet;
import cp.model.setclass.TnTnIType;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class ScaleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleTest.class);


    private Scale scale;
	@Autowired
	private Keys keys;
    @Autowired
	private TnTnIType tnTnIType;
	
	@BeforeEach
	public void setUp() {
		scale = Scale.MAJOR_SCALE;
	}

	@Test
	public void testTransposePitchClass() {
		int transposed = scale.transposePitchClass(4, 2);
        assertEquals(7, transposed);
	}

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 10, 11, 0 })
	public void testPickNextPitchFromScale(int pitchClass) {
		int next = scale.pickNextPitchFromScale(pitchClass);
        assertTrue(
                ArrayUtils.contains(scale.getPitchClasses(), next),
                () -> String.format("The scale doesn't contain the pitchClass: %s", next)
        );
	}

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 10, 11, 0 })
	public void testPickPreviousPitchFromScale(int pitchClass) {
		int previous = scale.pickPreviousPitchFromScale(pitchClass);
        assertTrue(
                ArrayUtils.contains(scale.getPitchClasses(), previous),
                () -> String.format("The scale doesn't contain the pitchClass: %s", previous)
        );
	}

	@Test
	public void testPickLowerStepFromScale() {
		int lower = scale.pickLowerStepFromScale(4, 3);
		assertEquals(11, lower);
	}
	
	@Test
	public void testInversedPitchClass() {
		int pc = scale.getInversedPitchClass(1,2);
		assertEquals(11, pc);
		pc = scale.getInversedPitchClass(1,4);
		assertEquals(9, pc);
		pc = scale.getInversedPitchClass(1,5);
		assertEquals(7, pc);
		
		pc = scale.getInversedPitchClass(2,4);
		assertEquals(0, pc);
		pc = scale.getInversedPitchClass(2,5);
		assertEquals(11, pc);
		
		pc = scale.getInversedPitchClass(7,0);
		assertEquals(9, pc);
		pc = scale.getInversedPitchClass(7,2);
		assertEquals(7, pc);
		
		scale = Scale.HARMONIC_MINOR_SCALE;
		pc = scale.getInversedPitchClass(2,3);
		assertEquals(0, pc);
		pc = scale.getInversedPitchClass(2,5);
		assertEquals(11, pc);
	}

	@Test
	public void testTransposePitchClassAll(){
		scale = Scale.MAJOR_SCALE;
		int[] transpositions = new int[scale.getPitchClasses().length];
		for (int i = 0; i < scale.getPitchClasses().length; i++) {
			int transposed = scale.transposePitchClass(4, i);
			System.out.println(transposed);
			transpositions[i] = transposed;
		}
		Arrays.sort(transpositions);
		Assertions.assertTrue(Arrays.equals(transpositions, scale.getPitchClasses()));
	}

	@Test
	public void testPickHigerStepFromScale() {
		int higher = scale.pickHigerStepFromScale(4, 3);
		assertEquals(9, higher);

		higher = scale.pickHigerStepFromScale(9, 3);
		assertEquals(2, higher);

		higher = scale.pickHigerStepFromScale(4, 2);
		assertEquals(7, higher);
	}

	@Test
	public void testInverse() {
		Scale minorScale = Scale.HARMONIC_MINOR_SCALE;
		int functionalDegreeCenter = RandomUtil.getRandomNumberInRange(1,scale.getPitchClasses().length);
		System.out.println("functionalDegreeCenter : " + functionalDegreeCenter);
		for (int pc : scale.getPitchClasses()) {
			System.out.println("pc: " + pc);
			int inversePc = minorScale.inverse(scale , pc,  functionalDegreeCenter);//key of E
			System.out.println("inverse " + inversePc);
		}
	}

	@Test
	public void getPitchClassesRandomized(){
		scale = Scale.ALL_COMBINATORIAL_HEXAHCORD_C;
		int[] pitchClassesRandomized = scale.getPitchClassesRandomized();
		Arrays.stream(pitchClassesRandomized).forEach(n -> System.out.println(n));
		assertEquals(6, pitchClassesRandomized.length);
	}

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 10, 11, 0 })
    public void testPickNextPitchFromScale2(int pitchClass) {
	    scale = Scale.PENTATONIC_SCALE_MINOR;
        int next = scale.pickNextPitchFromScale(pitchClass);
        System.out.println(next);
        assertTrue(
                ArrayUtils.contains(scale.getPitchClasses(), next),
                () -> String.format("The scale doesn't contain the pitchClass: %s", next)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 10, 11, 0 })
    public void testPickPreviousPitchFromScale2(int pitchClass) {
        scale = Scale.MAJOR_CHORD;
        int previous = scale.pickPreviousPitchFromScale(pitchClass);
        System.out.println(previous);
        assertTrue(
                ArrayUtils.contains(scale.getPitchClasses(), previous),
                () -> String.format("The scale doesn't contain the pitchClass: %s", previous)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 10, 11, 0 })
    public void testKeys(int pitchClass) {
        scale = Scale.LYDIAN_SCALE;
        TimeLineKey timeLineKey = new TimeLineKey(keys.D, scale, 0, 0);
        int next = timeLineKey.getPitchClassForKey(pitchClass);
//        LOGGER.info("Pc: " + next);
        System.out.println(next);
//        assertTrue(
//                ArrayUtils.contains(scale.getPitchClasses(), next),
//                () -> String.format("The scale doesn't contain the pitchClass: %s", next)
//        );
    }

    @Test
    public void getPitchClassesInKey(){
        scale = Scale.MAJOR_SCALE;
        List<Integer> pitchClassesInKey = scale.getPitchClassesInKey(keys.A);
        pitchClassesInKey.forEach(integer -> System.out.println(integer));
    }

    @Test
    public void getInversion(){
        PcSet pcSet = new PcSet();
        int[] tntnitype = tnTnIType.prime5[27].tntnitype;
        Arrays.stream(tntnitype).forEach(value -> System.out.print(value + ","));
        System.out.println();
        int[] ints = pcSet.invertPcSet(tntnitype);
        Arrays.stream(ints).forEach(value -> System.out.print(value+ ","));
    }

}
