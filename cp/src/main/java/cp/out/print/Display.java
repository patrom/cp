package cp.out.print;

import cp.generator.MusicProperties;
import cp.midi.MidiDevicesUtil;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.CpHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	private TimeLine timeLine;

	public void view(Motive motive, String id) throws Exception {
//        motive.getMelodyBlocks().stream().flatMap(m -> m.getMelodyBlockNotes().stream()).forEach(n -> {
//            if (n.getLength() <= DurationConstants.SIXTEENTH) {
//                n.setArticulation(Articulation.STACCATO);
//            }
//        });

        printHarmonies(motive.getHarmonies());
        viewScore(motive.getMelodyBlocks(), id);
        generateMusicXml(motive.getMelodyBlocks(), id);
        writeMidi(motive.getMelodyBlocks(), id);
        printTimeLine();
        LogTransformations(motive);

    }

    private void LogTransformations(Motive motive) {
        List<String> transformationNames = motive.getTransformations().stream().map(Enum::name).collect(Collectors.toList());
        LOGGER.info("Transformations: " + transformationNames);
    }

    private void writeMidi(List<MelodyBlock> melodyBlocks, String id) throws IOException, InvalidMidiDataException {
		Sequence sequence = midiDevicesUtil.createSequence(melodyBlocks, musicProperties.getTempo());
		Resource resource = new FileSystemResource("");
		midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/midi/" + id + ".mid");
	}

	private void printTimeLine() {
		Map<Integer, List<TimeLineKey>> keysPerVoice = timeLine.getKeysPerVoice();
        for (Integer integer : keysPerVoice.keySet()) {
            List<TimeLineKey> timeLineKeys = keysPerVoice.get(integer);
            StringBuilder stringBuilder = new StringBuilder();
            for (TimeLineKey timeLineKey : timeLineKeys) {
                stringBuilder.append(", Key: ");
                stringBuilder.append(timeLineKey.getKey().getStep());
                stringBuilder.append(", ");
                stringBuilder.append(timeLineKey.getStart());
                stringBuilder.append(", ");
                stringBuilder.append(Arrays.toString(timeLineKey.getScale().getPitchClasses()));
            }
            LOGGER.info(stringBuilder.toString());
        }
	}

	private void printHarmonies(List<CpHarmony> harmonies) {
		harmonies.forEach(h -> LOGGER.info(h.getChord()
				+ ", additive: " + h.getAdditiveChord()
				+ ", weight: " + h.getHarmonyWeight()
				+ ", pos: " + h.getPosition()
				+ ", register: " + h.getRegister(60)));
		// harmonies.forEach(h -> LOGGER.info(h.getNotes() + ", "));
	}

	private void generateMusicXml(List<MelodyBlock> melodies, String id) throws Exception {
		musicXMLWriter.generateMusicXMLForMelodies(melodies, new FileOutputStream(resource.getFile().getPath() + "src/main/resources/xml/" + id + ".xml"));
	}

	private void viewScore(List<MelodyBlock> melodies, String id)
			throws InvalidMidiDataException {

        if(melodies.stream().flatMap(melodyBlock -> melodyBlock.getMelodyBlockNotes().stream()).anyMatch(note -> !note.hasTexture())){
            System.out.println();
        }
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockContour() + ", "));
		melodies.forEach(m -> LOGGER.info(m.getMelodyBlockNotesWithRests() + ", "));
//        for (MelodyBlock melody : melodies) {
//            List<Note> melodyBlockNotes = melody.getMelodyBlockNotes();
//            for (Note melodyBlockNote : melodyBlockNotes) {
//                System.out.print(melodyBlockNote.getPitchClass());
//                System.out.print(", ");
//                System.out.print(Arrays.toString(melodyBlockNote.getDependantHarmony().getSetClass()));
//            }
//        }

		Score score = scoreUtilities.createScoreMelodies(melodies, musicProperties.getTempo());
		score.setTitle(id);
		View.notate(score);
	}

}
