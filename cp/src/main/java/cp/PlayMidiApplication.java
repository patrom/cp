package cp;

import cp.config.BeatGroupConfig;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.File;

@Import({DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
public class PlayMidiApplication extends JFrame implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayApplication.class.getName());

    @Autowired
    private MidiDevicesUtil midiDevicesUtil;

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
//		final Resource resource = new FileSystemResource("src/main/resources/orch");
        final Resource resource = new FileSystemResource("src/main/resources/midi");
        File dir = resource.getFile();
        for (File midiFile : dir.listFiles()) {
            LOGGER.info(midiFile.getName());
            Sequence sequence = MidiSystem.getSequence(midiFile);
            midiDevicesUtil.playOnDevice(sequence, 0, MidiDevicePlayer.KONTAKT);
            Thread.sleep(18000);
        }
    }

}

