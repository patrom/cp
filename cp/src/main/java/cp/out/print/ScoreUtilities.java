package cp.out.print;

import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.model.melody.MelodyBlock;
import cp.model.rhythm.DurationConstants;
import cp.variation.Embellisher;
import jm.JMC;
import jm.music.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ScoreUtilities implements JMC{
	
	private Logger LOGGER = LoggerFactory.getLogger(ScoreUtilities.class.getName());
	private final Random random = new Random();
	@Autowired
	private Embellisher embellisher;
	
	@Autowired
	private MusicProperties musicProperties;
	
	/**
	 * Generates random tempo between 50 - 150 bpm
	 * @return
	 */
	public float randomTempo() {
		float r = random.nextFloat();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		return r;
	}
	
	public Score createScoreMelodies(List<MelodyBlock> melodies, double tempo){
		Score score = new Score();
		Part[] scoreParts = new Part[melodies.size()];
		int voice = 0;
		for (MelodyBlock melody : melodies) {
			Phrase phrase;
			List<cp.model.note.Note> notes = melody.getMelodyBlockNotesWithRests();
//			if (melody.getVoice() == 3) {
//				List<neo.model.note.Note> embellishedNotes = embellisher.embellish(notes);
//				phrase = createPhrase(embellishedNotes);	
//			}
//			else{
				phrase = createPhrase(notes);	
//			}
			
			Part part = new Part(phrase);
			scoreParts[voice] = part;
			voice++;	
		}
//		for (CpMelody melody : melodies) {
//			List<cp.model.note.Note> notes = melody.getHarmonyNotes();
//			Phrase phrase = createPhrase(notes);	
//			Part part = new Part(phrase);
//			scoreParts[voice] = part;
//			voice++;	
//		}
		for (int i = scoreParts.length - 1; i > -1; i--) {
			score.add(scoreParts[i]);
		}
		score.setTempo(tempo);
		score.setNumerator(musicProperties.getNumerator());
		score.setDenominator(musicProperties.getDenominator());
		score.setKeySignature(musicProperties.getKeySignature());
		return score;
	}
	
	public Score createScoreFromMelodyInstrument(List<MelodyInstrument> melodies, double tempo){
		Score score = new Score();
		Part[] scoreParts = new Part[melodies.size()];
		int voice = 0;
		for (MelodyInstrument melody : melodies) {
			List<cp.model.note.Note> notes = melody.getNotes();
			Phrase phrase = createPhrase(notes);	
			Part part = new Part(phrase);
			scoreParts[voice] = part;
			voice++;	
		}
		for (int i = scoreParts.length - 1; i > -1; i--) {
			score.add(scoreParts[i]);
		}
		score.setTempo(tempo);
		score.setNumerator(musicProperties.getNumerator());
		score.setDenominator(musicProperties.getDenominator());
		score.setKeySignature(musicProperties.getKeySignature());
		return score;
	}

	public Phrase createPhrase(List<cp.model.note.Note> notes) {
		Phrase phrase = new Phrase();
		if (!notes.isEmpty()) {
			double startTime = (double)notes.get(0).getPosition()/DurationConstants.QUARTER;
			phrase.setStartTime(startTime);
			int length = notes.size();
			Note note;
			for (int i = 0; i < length; i++) {
				cp.model.note.Note notePos = notes.get(i);
				note = new Note(notePos.getPitch(),((double)notePos.getDisplayLength()/DurationConstants.QUARTER));
				note.setDuration(note.getRhythmValue());//note has DEFAULT_DURATION_MULTIPLIER = 0.9
				phrase.add(note);
				if ((i + 1) < length) {	
					cp.model.note.Note nextNotePos = notes.get(i + 1);
					int gap = (notePos.getPosition() + notePos.getDisplayLength()) - nextNotePos.getPosition();
					if (gap < 0) {
						note = new Rest((double)-gap/DurationConstants.QUARTER);
						note.setDuration(note.getRhythmValue());//note has DEFAULT_DURATION_MULTIPLIER = 0.9
						phrase.add(note);
					}
				}	
			}
		}
		return phrase;
	}
	
	public Score createMelody(List<cp.model.note.Note> notes){
		Score score = new Score();
		Phrase phrase = createPhrase(notes);	
		Part part = new Part(phrase);
		score.add(part);
		return score;
	}
	
}

