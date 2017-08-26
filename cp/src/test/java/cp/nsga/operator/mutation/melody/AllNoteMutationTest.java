package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 21/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class AllNoteMutationTest {

    @Autowired
    @InjectMocks
    private AllNoteMutation allNoteMutation;

    @Mock
    private VoiceConfig voiceConfig;
    @Autowired
    private PassingPitchClasses passingPitchClasses;

    @Before
    public void setUp(){
        ReflectionTestUtils.setField(allNoteMutation, "probabilityAllNote", 1.0);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doMutation() {
        when(voiceConfig.getRandomPitchClassGenerator(anyInt())).thenReturn(passingPitchClasses::updatePitchClasses);
        MelodyBlock melodyBlock = new MelodyBlock(5,1);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.EIGHT).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(9).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).build());
        CpMelody melody = new CpMelody(notes, 1, 0 , DurationConstants.WHOLE);
        melodyBlock.addMelodyBlock(melody);
        allNoteMutation.doMutation(melodyBlock);
        melodyBlock.getMelodyBlockNotes().forEach(n -> System.out.println(n));
    }

}