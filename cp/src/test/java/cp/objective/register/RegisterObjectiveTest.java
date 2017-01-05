package cp.objective.register;

import cp.DefaultConfig;
import cp.model.note.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 2/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RegisterObjectiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterObjectiveTest.class);

    @Autowired
    private RegisterObjective registerObjective;

    @Test
    public void getHarmonyRegisterValue() throws Exception {
        List<Note> harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(60).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        double registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(50).build());
        harmonyNotes.add(note().pc(4).pitch(64).build());
        registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);
    }

    @Test
    public void getHarmonyRegisterValue2() throws Exception {
        List<Note> harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(60).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(7).pitch(67).build());
        double registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(7).pitch(67).build());
        registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);

        harmonyNotes = new ArrayList<>();
        harmonyNotes.add(note().pc(0).pitch(48).build());
        harmonyNotes.add(note().pc(2).pitch(62).build());
        harmonyNotes.add(note().pc(0).pitch(72).build());
        registerValue = registerObjective.getHarmonyRegisterValue(harmonyNotes);
        LOGGER.info("RegisterValue : " + registerValue);
    }


}