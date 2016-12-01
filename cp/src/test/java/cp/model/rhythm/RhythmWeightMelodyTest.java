package cp.model.rhythm;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.MelodyVoice;
import cp.midi.MidiInfo;
import cp.midi.MidiParser;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.objective.rhythm.RhythmObjective;
import cp.out.print.ScoreUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RhythmWeightMelodyTest extends JFrame{

	@Autowired
	private RhythmWeight rhythmWeight;
	@Autowired
	private MidiParser midiParser;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	@InjectMocks
	private RhythmObjective rhythmObjective;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	@Mock
	private Composition composition;
	@Autowired
	private MelodyVoice melodyVoice;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(composition.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
	}
	
	@Test
	public void testRhythmWeight() throws InvalidMidiDataException, IOException {
		List<File> midiFiles = Files.list(new File("C:/Users/prombouts/git/neo/neo/resources/melodies/solo/").toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			MidiInfo midiInfo = midiParser.readMidi(file);
			List<Note> notes = midiInfo.getMelodies().get(0).getNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeight();
			System.out.println(file.getName());
			CpMelody melody = new CpMelody(notes, 0, 0, 200);
			MelodyBlock melodyBlock = new MelodyBlock(5,0);
			melodyBlock.addMelodyBlock(melody);
			double profileAverage = rhythmObjective.getProfileAverage(melodyBlock);
			System.out.println(profileAverage);
//			for (Note note : notes) {
//				System.out.print(note.getPitch() + ", " + note.getPositionWeight() + "; ");
//			}
//			System.out.println();
//			Score score = new Score();
//			Phrase phrase = scoreUtilities.createPhrase(notes);	
//			Part part = new Part(phrase);
//			score.add(part);
//			View.notate(score);
//			Play.midi(score, true);
		}
		
	}

}
