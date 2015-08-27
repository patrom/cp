package cp.model.harmony;

import static cp.model.harmony.HarmonyBuilder.harmony;
import static cp.model.melody.HarmonicMelodyBuilder.harmonicMelody;
import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cp.model.dissonance.TonalDissonance;
import cp.model.melody.HarmonicMelody;
import cp.model.note.NoteBuilder;

public class HarmonyTest {

	@Test
	public void testTransposeSecond() {
		Harmony harmony = harmony().notes(0,4,7).build();
		harmony.transpose(1);
		harmony.toChord();
		assertEquals(1, harmony.getNotes().get(0).getPitchClass());
		assertEquals(5, harmony.getNotes().get(1).getPitchClass());
		assertEquals(8, harmony.getNotes().get(2).getPitchClass());
		assertTrue(harmony.getChord().getPitchClassMultiSet().contains(1));
	}
	
	@Test
	public void testTransposeSixth() {
		Harmony harmony = harmony().notes(0,4,7).build();
		harmony.transpose(8);
		harmony.toChord();
		assertEquals(8, harmony.getNotes().get(0).getPitchClass());
		assertEquals(0, harmony.getNotes().get(1).getPitchClass());
		assertEquals(3, harmony.getNotes().get(2).getPitchClass());
		assertTrue(harmony.getChord().getPitchClassMultiSet().contains(8));
	}
	
	@Test
	public void testSearchBestChord() {
		HarmonyBuilder harmonyBuilder = harmony();
		harmonyBuilder.melodyBuilder(harmonicMelody().voice(0).pos(0)
				.harmonyNote(note().pc(0).pos(0).build())
				.notes(note().pc(0).pos(0).len(6).build(),
					   note().pc(1).pos(6).len(12).build(),
					   note().pc(3).pos(18).len(6).build()).build());
		harmonyBuilder.melodyBuilder(harmonicMelody().voice(1).pos(0)
				.harmonyNote(note().pc(4).pos(0).build())
				.notes(note().pc(4).pos(0).len(12).build(),
					   note().pc(5).pos(12).len(12).build()).build());
		harmonyBuilder.melodyBuilder(harmonicMelody().voice(2).pos(0)
				.harmonyNote(note().pc(7).pos(0).build())
				.notes(note().pc(7).pos(0).len(24).build()).build());
		Harmony harmony = harmonyBuilder.build();
		harmony.searchBestChord(new TonalDissonance());
		assertTrue(harmony.getNotes().contains(NoteBuilder.note().pc(0).build()));
		assertTrue(harmony.getNotes().contains(NoteBuilder.note().pc(4).build()));
		assertTrue(harmony.getNotes().contains(NoteBuilder.note().pc(7).build()));
	}
	
	@Test
	public void testReplaceHarmonicMelody(){
		HarmonyBuilder harmonyBuilder = harmony();
		harmonyBuilder.melodyBuilder(harmonicMelody().voice(0).pos(0)
				.harmonyNote(note().pc(0).pos(0).build())
				.notes(note().pc(0).pos(0).len(6).build(),
					   note().pc(1).pos(6).len(12).build(),
					   note().pc(3).pos(18).len(6).build()).build());
		Harmony harmony = harmonyBuilder.build();
		
		HarmonicMelody harmonicMelody = harmonicMelody().voice(0).pos(0)
				.harmonyNote(note().pc(4).pos(0).build())
				.notes(note().pc(4).pos(0).len(12).build(),
					   note().pc(5).pos(12).len(12).build()).build();
		harmony.replaceHarmonicMelody(harmonicMelody);
		
		assertEquals(harmony.getHarmonicMelodies().get(0).getMelodyNotes().get(0).getPitchClass(), 4);
		assertEquals(harmony.getHarmonicMelodies().get(0).getMelodyNotes().size(), 2);
	}
	
}
