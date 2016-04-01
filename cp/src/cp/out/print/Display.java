package cp.out.print;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.CpApplication;
import cp.generator.MusicProperties;
import cp.midi.MidiDevicesUtil;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.melody.MelodyBlock;
import jm.music.data.Score;
import jm.util.View;

@Component
public class Display {

	private static Logger LOGGER = LoggerFactory.getLogger(Display.class);

	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Autowired
	private MusicProperties musicProperties;

	public void view(Motive motive, String id) throws Exception {
		printHarmonies(motive.getHarmonies());
		viewScore(motive.getMelodyBlocks(), id, musicProperties.getTempo());
		generateMusicXml(motive.getMelodyBlocks(), id);
		// printVextab(sentences);
	}

	private void printHarmonies(List<CpHarmony> harmonies) {
		harmonies.forEach(h -> LOGGER.info(h.getChord() + ", " + h.getHarmonyWeight()));
		// harmonies.forEach(h -> LOGGER.info(h.getNotes() + ", "));
	}

	private void generateMusicXml(List<MelodyBlock> melodies, String id) throws Exception {
		musicXMLWriter.generateMusicXMLForMelodies(melodies, id);
	}

	private void viewScore(List<MelodyBlock> melodies, String id, double tempo)
			throws InvalidMidiDataException, IOException {
		Collections.sort(melodies, new MelodyVoiceComparator());
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockContour() + ", "));
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockNotesWithRests() + ", "));
		Score score = scoreUtilities.createScoreMelodies(melodies, tempo);
		score.setTitle(id);
		Sequence sequence = midiDevicesUtil.createSequence(melodies, (int) tempo);
		midiDevicesUtil.write(sequence, "resources/midi/" + id + ".mid");
		View.notate(score);
	}

	// private static void printVextab(List<Harmony> harmonies) {
	// String vexTab = ScoreUtilities.createVexTab(harmonies, inputProps);
	// LOGGER.info(vexTab);
	// }

}
