package cp.nsga.operator.mutation.melody;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.melody.BeatGroupMelody;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import cp.rhythm.RandomBeatGroupRhythm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = "composition.voices=4")
public class RhythmMutationTest {

    @Autowired
    private RhythmMutation rhythmMutation;

    @MockBean
    private VoiceConfig voiceConfig;
    @Autowired
    private PassingPitchClasses passingPitchClasses;
    @MockBean
    private TimeLine timeLine;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations rhythmCombinations;
    @Autowired
    private RandomPitchClasses randomPitchClasses;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void doMutation() {
        when(voiceConfig.getRandomPitchClassGenerator(anyInt())).thenReturn(passingPitchClasses::updatePitchClasses);
        TimeLineKey timeLineKey = new TimeLineKey(keys.A, Scale.MAJOR_SCALE);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.EIGHT).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(9).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).build());
        CpMelody melody = new CpMelody(notes, 1, 0 , DurationConstants.WHOLE);
        melody.setBeatGroup(getBeatGroup());
        melody.setNotesSize(2);
        rhythmMutation.doMutation(1.0,melody);
        melody.getNotes().forEach(n -> System.out.println(n));
        Assertions.assertEquals(2, melody.getNotesNoRest().size());
    }


    public BeatGroup getBeatGroup(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups5 = new ArrayList<>();
        beatGroups5.add(rhythmCombinations.twoNoteEven::pos14);
        beatGroups5.add(rhythmCombinations.twoNoteEven::pos13);
        beatGroups5.add(rhythmCombinations.twoNoteEven::pos12);
        beatGroups5.add(rhythmCombinations.twoNoteEven::pos23);
        beatGroups5.add(rhythmCombinations.twoNoteEven::pos24);
        map.put(2, beatGroups5);
        return new BeatGroupMelody(DurationConstants.QUARTER, DurationConstants.EIGHT,  new RandomBeatGroupRhythm(map) , Collections.singletonList(randomPitchClasses::randomPitchClasses));
    }

}