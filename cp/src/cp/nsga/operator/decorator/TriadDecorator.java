package cp.nsga.operator.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import jmetal.core.Solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.util.RandomUtil;

@Component(value="TriadDecorator")
public class TriadDecorator implements Decorator {

	@Autowired 
	private MusicProperties musicProperties;
	private List<List<Integer>> majorAndMinorTriadIntervals = new ArrayList<List<Integer>>();
	
	public TriadDecorator() {
		List<Integer> major = new ArrayList<Integer>();
		major.add(3);
		major.add(7);
		majorAndMinorTriadIntervals.add(major);
		List<Integer> major1 = new ArrayList<Integer>();
		major1.add(5);
		major1.add(8);
		majorAndMinorTriadIntervals.add(major1);
		List<Integer> major2 = new ArrayList<Integer>();
		major2.add(4);
		major2.add(9);
		majorAndMinorTriadIntervals.add(major2);
		List<Integer> minor = new ArrayList<Integer>();
		minor.add(4);
		minor.add(7);
		majorAndMinorTriadIntervals.add(minor);
		List<Integer> minor1 = new ArrayList<Integer>();
		minor1.add(5);
		minor1.add(9);
		majorAndMinorTriadIntervals.add(minor1);
		List<Integer> minor2 = new ArrayList<Integer>();
		minor2.add(3);
		minor2.add(8);
		majorAndMinorTriadIntervals.add(minor2);
		
	}

	@Override
	public void decorate(Solution solution) {
		List<Integer> voices = new ArrayList<>();
		voices.add(2);
		voices.add(5);
		Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
		List<Harmony> harmonies = motive.getHarmonies();
		for (Integer voice : voices) {
			for (Harmony harmony : harmonies) {
				Optional<HarmonicMelody> harmOptional = getHarmonicMelodyForVoice(voice, harmony);
				if (harmOptional.isPresent()) {
					HarmonicMelody harmonicMelody = harmOptional.get();
//					List<Note> chordNotes = harmonicMelody.getChordNotes();
//					for (Note chordNote : chordNotes) {
//						updateChordNote(chordNote, noteConsumer);
//					}
					List<Integer> triadIntervals = selectTriad();
					Consumer<Note> noteConsumerOneStepBelow = (note -> {
						int voiceOneStepBelow = note.getVoice() - 1;
						int interval = triadIntervals.get(0);
						updateNoteAtInterval(voiceOneStepBelow, note, interval);
						});
					Consumer<Note> noteConsumerTwoStepsBelow = (note -> {
						int voiceOneStepBelow = note.getVoice() - 2;
						int interval = triadIntervals.get(1);
						updateNoteAtInterval(voiceOneStepBelow, note, interval);
						});
					HarmonicMelody decoratedHarmonicMelody = updateCopyHarmonicMelody(harmonicMelody, voice - 1, noteConsumerOneStepBelow);
					harmony.replaceHarmonicMelody(decoratedHarmonicMelody);
					HarmonicMelody decoratedHarmonicMelody2 = updateCopyHarmonicMelody(harmonicMelody, voice - 2, noteConsumerTwoStepsBelow);
					harmony.replaceHarmonicMelody(decoratedHarmonicMelody2);
				}
			}
		}
	}
	
	protected HarmonicMelody updateCopyHarmonicMelody(HarmonicMelody harmonicMelody, int voice, Consumer<Note> noteConsumer) {
		HarmonicMelody copyHarmonicMelody = harmonicMelody.copy(voice);
		copyHarmonicMelody.getMelodyNotes().forEach(noteConsumer);
		noteConsumer.accept(copyHarmonicMelody.getHarmonyNote());
		return copyHarmonicMelody;
	}
	
	protected void updateChordNote(Note chordNote, Consumer<Note> noteConsumer) {
		noteConsumer.accept(chordNote);
	}

	private Note updateNoteAtInterval(int voice, Note note, int interval) {
		int pc = ((note.getPitchClass() - interval) + 12) % 12;
		note.setPitchClass(pc);
		note.setPitch(note.getPitch() - pc);
		note.setVoice(voice);
//		note.setOctave(octave);//TODO??
		return note;
	}

	private List<Integer> selectTriad() {
		return RandomUtil.getRandomFromList(majorAndMinorTriadIntervals);
	}

	private Optional<HarmonicMelody> getHarmonicMelodyForVoice(int voice,
			Harmony harmony) {
		Optional<HarmonicMelody> harmOptional = harmony.getHarmonicMelodies().stream().filter(h -> h.getVoice() == voice).findFirst();
		return harmOptional;
	}

}
