package cp;

import cp.config.BeatGroupConfig;
import cp.config.InstrumentConfig;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.midi.MidiParser;
import cp.model.note.Note;
import cp.musicxml.XMLParser;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.out.orchestration.Orchestrator;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.print.MusicXMLWriter;
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
import java.util.List;
import java.util.Map;

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


    private final ClassicalOrchestra classicalOrchestra = new ClassicalOrchestra();

    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(OrchestrateApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        final Resource resource = new FileSystemResource("cp/src/main/resources/xml");
        File dir = resource.getFile();
        for (File xmlFile : dir.listFiles()) {
            XMLParser xmlParser = new XMLParser();
            xmlParser.setInstrumentConfig(instrumentConfig);
            xmlParser.startParsing(xmlFile.getPath());
            ComplexElement partList = xmlParser.getScore().getPartList();
            xmlParser.setInstrumentNames(partList);
            ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
            xmlParser.traverse(body);
            Map<Integer, List<Note>> notesPerInstrument = xmlParser.getNotesPerVoice();

            Orchestra orchestra = orchestrator.orchestrate(notesPerInstrument);

            String id = removeExtension(xmlFile.getName()) + "_orchestrated";
            //XML
            Resource resourceOrch = new FileSystemResource("");
            String path = resourceOrch.getFile().getPath() + "cp/src/main/resources/orch/";
            musicXMLWriter.createXML(new FileOutputStream(path  + id + ".xml"), orchestra.getOrchestra());
            //midi
            Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), xmlParser.getBpm());
            midiDevicesUtil.write(sequence, path + id + ".mid");
            //kontakt
            midiDevicesUtil.playOnDevice(sequence, xmlParser.getBpm(), MidiDevicePlayer.KONTAKT);

            //            Score score = scoreUtilities.createScoreFromMelodyInstrument(melodyInstruments, xmlParser.getBpm());
//            score.setTitle(xmlFile.getName());
//            View.notate(score);
            Thread.sleep(26000);
        }
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
