package cp.objective.register;

import cp.DefaultConfig;
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 2/01/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class RegisterObjectiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterObjectiveTest.class);

    @Autowired
    private RegisterObjective registerObjective;

    @Test
    public void getHarmonyRegisterValue() throws Exception {
        List<CpHarmony> harmonies = new ArrayList<>();
        List<Note> harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(60).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        CpHarmony harmony = new CpHarmony(harmonyNotes, 0);
        harmonies.add(harmony);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        harmony = new CpHarmony(harmonyNotes, DurationConstants.QUARTER);
        harmonies.add(harmony);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(52).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        harmony = new CpHarmony(harmonyNotes, DurationConstants.HALF);
        harmonies.add(harmony);
        double registerValue = registerObjective.getRegisterValue(harmonies);
        LOGGER.info("RegisterValue : " + registerValue);
    }

    @Test
    public void getHarmonyRegisterValueDissonant() throws Exception {
        List<CpHarmony> harmonies = new ArrayList<>();
        List<Note> harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(60).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        CpHarmony harmony = new CpHarmony(harmonyNotes, 0);
        harmonies.add(harmony);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(49).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        harmony = new CpHarmony(harmonyNotes, DurationConstants.QUARTER);
        harmonies.add(harmony);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(50).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        harmony = new CpHarmony(harmonyNotes, DurationConstants.HALF);
        harmonies.add(harmony);
        double registerValue = registerObjective.getRegisterValue(harmonies);
        LOGGER.info("RegisterValue : " + registerValue);
    }


}