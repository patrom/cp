package cp.model.humanize;

import cp.generator.MusicProperties;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HumanizerTest {

    @InjectMocks
    private Humanizer humanizer;
    @Mock
    private MusicProperties musicProperties;
    @Mock
    private Instrument instrument;

    @Before
    public void setUp(){
        when(musicProperties.getTempo()).thenReturn(100);
        when(instrument.getInstrumentGroup()).thenReturn(InstrumentGroup.BRASS);
    }

    @Test
    public void velocity() {
        Note note = NoteBuilder.note().dyn(Dynamic.MF).build();
        int velocity = humanizer.velocity(note);
        System.out.println(velocity);
        assertTrue("Error, timing is too high", -6 <= velocity);
        assertTrue("Error, timing is too low",  6  >= velocity);
    }

    @Test
    public void timing() {
        int timing = humanizer.timing(instrument);
        assertTrue("Error, timing is too high", 0 <= timing);
        assertTrue("Error, timing is too low",  11  >= timing);
    }

    @Test
    public void intonation(){
        Note note = NoteBuilder.note().dyn(Dynamic.MF).build();
        int intonation = humanizer.intonation(note, instrument, 10);
        assertTrue("Error, intonation is too high", -2 <= intonation);
        assertTrue("Error, intonation is too low",  2 >= intonation);
    }

    @Test
    public void duration(){
        when(instrument.getInstrumentGroup()).thenReturn(InstrumentGroup.ORCHESTRAL_STRINGS);
        int duration = humanizer.duration(instrument, Technical.STACCATO, DurationConstants.HALF, true, 1);
        assertEquals(DurationConstants.HALF , duration);

        when(instrument.getInstrumentGroup()).thenReturn(InstrumentGroup.WOODWINDS);
        duration = humanizer.duration(instrument, Technical.STACCATO, DurationConstants.HALF, false, 0);
        assertTrue("Error, duration is too high", DurationConstants.HALF > duration);
    }

    @Test
    public void durationLegato(){
        when(instrument.getInstrumentGroup()).thenReturn(InstrumentGroup.ORCHESTRAL_STRINGS);
        int duration = humanizer.duration(instrument, Technical.LEGATO, DurationConstants.HALF, true, 10);
        assertEquals(DurationConstants.HALF , duration);

        when(instrument.getInstrumentGroup()).thenReturn(InstrumentGroup.BRASS);
        duration = humanizer.duration(instrument, Technical.LEGATO, DurationConstants.HALF, false, 2);
        assertTrue("Error, duration is too high", DurationConstants.HALF >= duration);
    }

    @Test
    public void isLongNote() {
        boolean longNote = humanizer.isLongNote(DurationConstants.QUARTER);
        assertFalse(longNote);
        when(musicProperties.getTempo()).thenReturn(60);
        longNote = humanizer.isLongNote(DurationConstants.QUARTER);
        assertTrue(longNote);
    }

}