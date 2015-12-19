package cp.generator.pitchclass;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.evaluation.FitnessEvaluationTemplateTest;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class PassingPitchClassesTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClassesTest.class);
	
	@Autowired
	private PassingPitchClasses passingPitchClasses;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdatePitchClasses() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes = passingPitchClasses.updatePitchClasses(notes, Scale.MAJOR_SCALE, 2);
		LOGGER.info("Notes: " + notes);
	}

}
