package cp.generator.pitchclass;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.TimeLine;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.out.print.note.Key;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class PassingPitchClassesTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClassesTest.class);
	
	@Autowired
	@InjectMocks
	private PassingPitchClasses passingPitchClasses;
	@Mock
	private TimeLine timeLine;
	@Autowired
	private Key D;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(timeLine.getKeyAtPosition(Mockito.anyInt())).thenReturn(D);
	}

	@Test
	public void testUpdatePitchClasses() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes.add(NoteBuilder.note().pc(0).build());
		notes = passingPitchClasses.updatePitchClasses(notes, Scale.MAJOR_SCALE);
		LOGGER.info("Notes: " + notes);
	}

}
