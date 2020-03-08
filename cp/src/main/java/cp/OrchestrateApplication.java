package cp;

import cp.composition.MelodyMapComposition;
import cp.config.BeatGroupConfig;
import cp.config.InstrumentConfig;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.midi.MidiParser;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.musicxml.XMLParser;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.Orchestrator;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.*;
import cp.out.print.MusicXMLWriter;
import cp.out.print.MusicXMLWriterDorico;
import cp.out.print.ScoreUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

@Import({DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
public class OrchestrateApplication extends JFrame implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrchestrateApplication.class.getName());

    @Autowired
    private MidiParser midiParser;
    @Autowired
    private MidiDevicesUtil midiDevicesUtil;
    @Autowired
    private ScoreUtilities scoreUtilities;
    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private Orchestrator orchestrator;
    @Autowired
    private MusicXMLWriter musicXMLWriter;
    @Autowired
    private MelodyMapComposition melodyMapComposition;

    @Autowired
    private BrilliantWhite brilliantWhite;
    @Autowired
    private BrightYellow brightYellow;
    @Autowired
    private PleasantGreen pleasantGreen;
    @Autowired
    private RichBlue richBlue;
    @Autowired
    private GoldenOrange goldenOrange;
    @Autowired
    private GlowingRed glowingRed;
    @Autowired
    private MellowPurple mellowPurple;
    @Autowired
    private WarmBrown warmBrown;


    private final ClassicalOrchestra classicalOrchestra = new ClassicalOrchestra();

    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(OrchestrateApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

//    @Override
//    public void run(String... arg0) throws Exception {
//        final Resource resource = new FileSystemResource("src/main/resources/xml");
//        File dir = resource.getFile();
//        for (File xmlFile : dir.listFiles()) {
//            XMLParser xmlParser = new XMLParser();
//            xmlParser.setInstrumentConfig(instrumentConfig);
//            xmlParser.startParsing(xmlFile.getPath());
//            ComplexElement partList = xmlParser.getScore().getPartList();
//            xmlParser.setInstrumentNames(partList);
//            ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
//            xmlParser.traverse(body);
//            Map<Integer, List<Note>> notesPerInstrument = xmlParser.getNotesPerVoice();
//
//            Orchestra orchestra = orchestrator.orchestrate(notesPerInstrument);
//
//            String id = removeExtension(xmlFile.getName()) + "_orchestrated";
//            //XML
//            Resource resourceOrch = new FileSystemResource("");
//            String path = resourceOrch.getFile().getPath() + "src/main/resources/orch/";
//            musicXMLWriter.createXML(new FileOutputStream(path  + id + ".xml"), orchestra.getOrchestra());
//            //midi
//            Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), xmlParser.getBpm());
//            midiDevicesUtil.write(sequence, path + id + ".mid");
//            //kontakt
//            midiDevicesUtil.playOnDevice(sequence, xmlParser.getBpm(), MidiDevicePlayer.KONTAKT);
//
//            //            Score score = scoreUtilities.createScoreFromMelodyInstrument(melodyInstruments, xmlParser.getBpm());
////            score.setTitle(xmlFile.getName());
////            View.notate(score);
//            Thread.sleep(26000);
//        }
//    }

    @Override
    public void run(String... arg0) throws Exception {
//        final Resource resource = new FileSystemResource("src/main/resources/xml");
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
        notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
//        File dir = resource.getFile();
        OrchestralQuality orchestralQuality = pleasantGreen;
//        List<Instrument> instruments = orchestralQuality.getBasicInstruments();
//        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(Arrays.asList(InstrumentGroup.WOODWINDS, InstrumentGroup.ORCHESTRAL_STRINGS));
            Orchestra orchestra = orchestrator.orchestrateMelody(notes, DurationConstants.WHOLE * 2, instruments, instruments.size());

//            String id = removeExtension(xmlFile.getName()) + "_orchestrated";
            //XML
            Resource resourceOrch = new FileSystemResource("");
            String path = resourceOrch.getFile().getPath() + "src/main/resources/orch/";
            musicXMLWriter.createXML(new FileOutputStream(path  + 1 + ".xml"), orchestra.getOrchestra());
            //midi
//            Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), xmlParser.getBpm());
//            midiDevicesUtil.write(sequence, path + id + ".mid");
//            //kontakt
//            midiDevicesUtil.playOnDevice(sequence, xmlParser.getBpm(), MidiDevicePlayer.KONTAKT);

            //            Score score = scoreUtilities.createScoreFromMelodyInstrument(melodyInstruments, xmlParser.getBpm());
//            score.setTitle(xmlFile.getName());
//            View.notate(score);
//            Thread.sleep(26000);

    }

    private String removeExtension(String filename) {
        int extensionPos = filename.lastIndexOf('.');
        if (extensionPos == -1) {
            return filename;
        } else {
            return filename.substring(0, extensionPos);
        }
    }

}
