package cp;

import cp.config.ColorQualityConfig;
import cp.config.InstrumentConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.midi.MidiParser;
import cp.model.note.Note;
import cp.model.rhythm.Rhythm;
import cp.out.arrangement.Arrangement;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.print.MusicXMLWriter;
import cp.out.print.ScoreUtilities;
import cp.variation.Embellisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Import({DefaultConfig.class, VariationConfig.class})
public class PlayMidiApplication extends JFrame implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayApplication.class.getName());

    @Autowired
    private MidiParser midiParser;
    @Autowired
    private MidiDevicesUtil midiDevicesUtil;
    @Autowired
    private MusicProperties musicProperties;
    @Autowired
    private Arrangement arrangement;
    @Autowired
    private ScoreUtilities scoreUtilities;
    @Autowired
    private Embellisher embellisher;
    @Autowired
    private Rhythm rhythm;
    @Autowired
    private MelodyGenerator melodyGenerator;
    @Autowired
    private MusicXMLWriter musicXMLWriter;
    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private ColorQualityConfig colorQualityConfig;

    private final ClassicalOrchestra classicalOrchestra = new ClassicalOrchestra();

    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(PlayMidiApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
		playMidiFilesOnKontaktFor();
    }

    public void playMidiFilesOnKontaktFor() throws Exception {
//		final Resource resource = new FileSystemResource("cp/src/main/resources/orch");
        final Resource resource = new FileSystemResource("cp/src/main/resources/midi");
        File dir = resource.getFile();
        for (File midiFile : dir.listFiles()) {
            LOGGER.info(midiFile.getName());
            Sequence sequence = MidiSystem.getSequence(midiFile);
            midiDevicesUtil.playOnDevice(sequence, 0, MidiDevicePlayer.KONTAKT);
            Thread.sleep(20000);
//            MidiInfo midiInfo = midiParser.readMidi(midiFile);
//            List<MelodyInstrument> parsedMelodies = midiInfo.getMelodies();
//			musicProperties.setInstruments(Ensemble.getStringQuartet());
//			mapInstruments(parsedMelodies);
            //split
//			int size = parsedMelodies.size();
//			List<MelodyInstrument> melodies = new ArrayList<>(parsedMelodies.subList(0, size/2));
//			List<MelodyInstrument> harmonies = new ArrayList<>(parsedMelodies.subList(size/2, size));
//			arrangement.transpose(melodies.get(3).getNotes(), 12);
//			arrangement.transpose(melodies.get(4).getNotes(), 12);
//			arrangement.transpose(melodies.get(5).getNotes(), 12);
//			List<Integer> voicesForAccomp = new ArrayList<>();
//			voicesForAccomp.add(1);
//			voicesForAccomp.add(2);
//			voicesForAccomp.add(3);
//			List<MelodyInstrument> accompMelodies = filterAccompagnementMelodies(voicesForAccomp, melodies);
//			createAccompagnement(accompMelodies, melodies, midiInfo.getHarmonyPositionsForVoice(0));

//			int[] sounds = {0,12,24,36,48,60,72};
//			Integer[] texture = {1,1,1,1, 1 ,1,1,1};
//			Integer[] contour = {1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1};
//			List<Note> accompNotes = rhythm.getRhythm(midiInfo.getHarmonyPositions(), contour, 1, 1, 6);
//			List<Note> accompNotes = rhythm.getRhythm(midiInfo.getHarmonyPositions(), sounds, texture, contour, 1);
//			melodies.get(1).setNotes(accompNotes);
//			arrangement.transpose(melodies.get(1).getNotes(), -12);

//			embellish(melodies);
//			parsedMelodies = parsedMelodies.stream().filter(m -> m.getVoice() != 1 && m.getVoice() != 4).collect(Collectors.toList());
//            Collections.sort(parsedMelodies);
//            Collections.reverse(parsedMelodies);
//            Score score = scoreUtilities.createScoreFromMelodyInstrument(parsedMelodies, midiInfo.getTempo());
//            score.setTitle(midiFile.getName());
//            View.notate(score);
            //			parsedMelodies.stream().filter(m -> m.getVoice() == 4).flatMap(m -> m.getNotes().stream()).forEach(n -> n.setPitch(n.getPitch() + 12));// for miroslav string quartet
//			parsedMelodies.stream().flatMap(m -> m.getNotes().stream()).forEach(n -> n.setPitch(n.getPitch() + 12));// for miroslav string quartet


//            Sequence sequence = midiDevicesUtil.createSequenceGeneralMidi(parsedMelodies, midiInfo.getTempo(), true);

//			write(parsedMelodies , "resources/transform/" + midiFile.getName(), midiInfo.getTempo());
//			generateMusicXml(parsedMelodies, midiFile.getName());

        }
    }

    private void embellish(List<MelodyInstrument> melodies) {
        for (int i = melodies.size() - 1; i >= 0; i--) {
            MelodyInstrument melodyInstrument = melodies.get(i);
            List<Note> embellishedNotes = embellisher.embellish(melodyInstrument.getNotes());
            melodyInstrument.setNotes(embellishedNotes);
        }
    }


    private List<MelodyInstrument> filterAccompagnementMelodies(List<Integer> voicesForAccomp,
                                                                List<MelodyInstrument> melodies){
        return melodies.stream().filter(m -> voicesForAccomp.contains(m.getVoice())).collect(Collectors.toList());
    }



    private void playOnKontakt(List<MelodyInstrument> melodies, Sequence sequence,
                               int tempo) throws InvalidMidiDataException {
        midiDevicesUtil.playOnDevice(sequence, tempo, MidiDevicePlayer.KONTAKT);
    }


}

