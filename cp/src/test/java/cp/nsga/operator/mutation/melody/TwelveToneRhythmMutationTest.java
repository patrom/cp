package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.combination.even.TwoNoteEven;
import cp.config.TwelveToneConfig;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.model.twelve.TwelveToneBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class TwelveToneRhythmMutationTest {

    @Autowired
    private TwelveToneRhythmMutation twelveToneRhythmMutation;

    @MockBean
    private TwelveToneBuilder twelveToneBuilder;
    @MockBean
    private TwelveToneConfig twelveToneConfig;
    @Autowired
    private TwoNoteEven twoNoteEven;

    private List<Integer> durations = Stream.of(DurationConstants.SIXTEENTH,
            DurationConstants.EIGHT, DurationConstants.QUARTER, DurationConstants.HALF).collect(Collectors.toList());

    @BeforeEach
    public void setUp() {
//        when(twelveToneConfig.getDurations()).thenReturn(durations);
//        twelveToneBuilder.build(DurationConstants.QUARTER, twoNoteEven::pos13, twoNoteEven::pos13, twoNoteEven::pos13, twoNoteEven::pos13);
    }

    @Test
    public void doMutation() {
//        when(twelveToneBuilder.getEnd()).thenReturn(DurationConstants.WHOLE);
//        when(twelveToneBuilder.getRhythmGrid()).thenReturn(getGrid());
//        when(twelveToneConfig.getTwelveToneConfigForVoice(anyInt())).thenReturn(Scale.ALL_INTERVAL_TRETRACHORD1);
//        when( twelveToneBuilder.getRandomPositionBeforeOrAfter(anyInt())).thenReturn(DurationConstants.QUARTER + DurationConstants.EIGHT);
//        List<Note> notes = new ArrayList<>();
//        notes.add(NoteBuilder.note().pos(DurationConstants.EIGHT).build());
//        Note noteToMove = NoteBuilder.note().pos(DurationConstants.QUARTER).len(DurationConstants.WHOLE).build();
//        notes.add(noteToMove);
//        notes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.EIGHT).len(DurationConstants.EIGHT).build());
//        notes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).build());
//        List<Note> twelveToneNotes = twelveToneRhythmMutation.getTwelveToneNotes(notes, noteToMove, Scale.ALL_INTERVAL_TRETRACHORD1);
//        twelveToneNotes.forEach(note -> System.out.println(note.getPosition()));
    }

    private List<Note> getGrid(){
        List<Note> gridNotes = new ArrayList<>();
        gridNotes.add(NoteBuilder.note().pos(0).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.EIGHT).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.QUARTER).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.HALF).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.QUARTER).build());
        gridNotes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        return gridNotes;
    }
}