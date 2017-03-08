package cp.out.print;

import cp.generator.MusicProperties;
import cp.midi.MidiDevicesUtil;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.melody.MelodyBlock;
import cp.out.play.InstrumentConfig;
import jm.music.data.Score;
import jm.util.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Display {

	private static final Logger LOGGER = LoggerFactory.getLogger(Display.class);

	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Autowired
	private MusicProperties musicProperties;
	@javax.annotation.Resource(name="fileResource")
	private Resource resource;
	@Autowired
	private InstrumentConfig instrumentConfig;

	public void view(Motive motive, String id) throws Exception {
		printHarmonies(motive.getHarmonies());
		viewScore(motive.getMelodyBlocks(), id);
		generateMusicXml(motive.getMelodyBlocks(), id);
		// printVextab(sentences);
	}

	private void printHarmonies(List<CpHarmony> harmonies) {
		harmonies.forEach(h -> LOGGER.info(h.getChord() + ", " + h.getHarmonyWeight() + ", pos: " + h.getPosition() + ", register: " + h.getRegister()));
		// harmonies.forEach(h -> LOGGER.info(h.getNotes() + ", "));
	}

	private void generateMusicXml(List<MelodyBlock> melodies, String id) throws Exception {
//		Map<InstrumentMapping, List<Note>> melodiesForInstrument = new TreeMap<>();
//		for (Map.Entry<Integer,InstrumentMapping> entry : instrumentConfig.getInstruments().entrySet()) {
//			InstrumentMapping instrumentMapping = entry.getValue();
//			MelodyBlock melodyBlock = melodies.get(entry.getKey());
//			melodiesForInstrument.put(instrumentMapping, melodyBlock.getMelodyBlockNotesWithRests());
//		}
		musicXMLWriter.generateMusicXMLForMelodies(melodies, new FileOutputStream(resource.getFile().getPath() + "cp/src/main/resources/xml/" + id + ".xml"));

		Sequence sequence = midiDevicesUtil.createSequence(melodies, musicProperties.getTempo());
		Resource resource = new FileSystemResource("");
		midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "cp/src/main/resources/midi/" + id + ".mid");
	}

	private void viewScore(List<MelodyBlock> melodies, String id)
			throws InvalidMidiDataException, IOException {
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockContour() + ", "));
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockNotesWithRests() + ", "));
		Score score = scoreUtilities.createScoreMelodies(melodies, musicProperties.getTempo());
		score.setTitle(id);
		View.notate(score);
	}

	// private static void printVextab(List<Harmony> harmonies) {
	// String vexTab = ScoreUtilities.createVexTab(harmonies, inputProps);
	// LOGGER.info(vexTab);
	// }

}
