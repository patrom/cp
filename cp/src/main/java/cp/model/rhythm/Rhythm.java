package cp.model.rhythm;

import cp.midi.HarmonyPosition;
import cp.model.note.Note;
import cp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Rhythm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Rhythm.class);
	
	private final Random random = new Random();
	
	public List<Note> getRhythm(int harmonyPosition ,List<Note> chordNotes, Integer[] positions, int voice, Integer[] texture, Integer[] contour){
		List<Note> soundNotes = getSounds(harmonyPosition, positions);
		List<Note> contourNotes = getContour(chordNotes, soundNotes, contour, voice);
        return getTexture(chordNotes, contourNotes, texture);
	}
	
	protected List<Note> getSounds(int harmonyPosition, Integer[] positions) {
		List<Note> sounds = new ArrayList<>();
		for (int i = 0; i < positions.length - 1; i++) {
			Note note = new Note();
			note.setPosition(positions[i] + harmonyPosition);
			note.setLength(positions[i + 1] - positions[i]);
			sounds.add(note);
		}
		return sounds;
	}
	
	protected List<Note> getSounds(int harmonyPosition, List<Integer> positions) {
		List<Note> sounds = new ArrayList<>();
		int size = positions.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = new Note();
			note.setPosition(positions.get(i) + harmonyPosition);
			note.setLength(positions.get(i + 1) - positions.get(i));
			sounds.add(note);
		}
		return sounds;
	}

	public List<Note> getRhythm(List<Note> chordNotes, Integer[] positions, int voice, Integer[] texture, Integer[] contour){
		List<Note> soundNotes = getSounds(0 ,positions);
		List<Note> contourNotes = getContour(chordNotes, soundNotes, contour, voice);
        return getTexture(chordNotes, contourNotes, texture);
	}
	
	public List<Note> getRhythm(List<Note> chordNotes, List<Integer> positions, int voice, Integer[] texture, Integer[] contour){
		List<Note> soundNotes = getSounds(0 ,positions);
		List<Note> contourNotes = getContour(chordNotes, soundNotes, contour, voice);
        return getTexture(chordNotes, contourNotes, texture);
	}
	
	public List<Note> getRhythmRandomContourTexture(List<Note> chordNotes, Integer[] positions, int voice, int maxTexture){
		List<Note> soundNotes = getSounds(0, positions);
		Integer[] contour = getRandomContour(chordNotes.size() - 1, positions.length - 2);
		LOGGER.debug("Contour: " + Arrays.toString(contour));
		List<Note> contourNotes = getContour(chordNotes, soundNotes, contour, voice);
		Integer[] texture = getRandomTexture(maxTexture + 1, positions.length - 1);
		LOGGER.debug("texture: " + Arrays.toString(texture));
        return getTexture(chordNotes, contourNotes, texture);
	}
	
	private Integer[] getRandomTexture(int chordSize, int soundsSize) {
		return random.ints(1, chordSize).limit(soundsSize)
				.boxed()
				.toArray(Integer[]::new);
	}

	private Integer[] getRandomContour(int chordSize, int soundsSize) {
		return random.ints(-chordSize, chordSize)
				.limit(soundsSize)
				.boxed()
				.toArray(Integer[]::new);
	}

	protected List<Note> getTexture(List<Note> chordNotes, List<Note> melody, Integer[] texture){
		List<Note> textures = new ArrayList<>();
		int size = chordNotes.size();
		for (int i = 0; i < melody.size(); i++) {
			int nextTexture = getNextTexture(texture, i);
			if (nextTexture > 1) {
				List<Note> textureNotes = new ArrayList<>();
				Note melodyNote = melody.get(i);
				textureNotes.add(melodyNote);
				int index = getIndexOfNote(melodyNote, chordNotes);
				for (int j = 1; j < nextTexture; j++) {
					 Note note = chordNotes.get(((index - j) + size) % size).clone();
					 note.setVoice(melodyNote.getVoice());
					 note.setPosition(melodyNote.getPosition());
					 note.setLength(melodyNote.getLength());
					 Note lastNote = textureNotes.get(textureNotes.size() - 1);
					 while (lastNote.getPitch() < note.getPitch()) {
						note.setPitch(note.getPitch() - 12);
						note.setOctave(note.getOctave() - 1);
					 }
					 textureNotes.add(note);
				}
				textures.addAll(textureNotes);
			} else {
				textures.addAll(Collections.singletonList(melody.get(i)));
			}
		}
		return textures;
	}
	
	protected void updateTexture(List<RhythmPosition> rhythmPositions, Integer[] texture){
		for (int i = 0; i < rhythmPositions.size(); i++) {
			RhythmPosition rhythmPosition = rhythmPositions.get(i);
			if (!rhythmPosition.getNotes().isEmpty()) {
				Note melodyNote = rhythmPosition.getNotes().get(0);
				int nextTexture = getNextTexture(texture, i);
				if (nextTexture > 1) {
					List<Note> textureNotes = new ArrayList<>();
						int size = rhythmPosition.getSelectableNotes().size();
						textureNotes.add(melodyNote);
						int index = getIndexOfNote(melodyNote, rhythmPosition.getSelectableNotes());
						for (int j = 1; j < nextTexture; j++) {
							 Note note = rhythmPosition.getSelectableNotes().get(((index - j) + size) % size).clone();
							 note.setVoice(melodyNote.getVoice());
							 note.setPosition(melodyNote.getPosition());
							 note.setLength(melodyNote.getLength());
							 if (melodyNote.getPitchClass() < note.getPitchClass()) {
								 updateNotePitch(note, melodyNote.getOctave() - 1);
							 }else{
								 updateNotePitch(note, melodyNote.getOctave());
							 }
							 textureNotes.add(note);
						}
						rhythmPosition.updateAllNotes(textureNotes);
				} 
			}
		}
	}
	
	private void updateNotePitch(Note note, int octave){
		note.setPitch(note.getPitchClass() + (12 * octave));
		note.setOctave(octave);
	}
	
	private int getIndexOfNote(Note melodyNote, List<Note> chordNotes) {
		Note noteInList = chordNotes.stream().filter(note -> note.getPitchClass() == melodyNote.getPitchClass()).findFirst().get();
		return chordNotes.indexOf(noteInList);
	}

//	protected List<Note> getContour(List<Note> chordNotes, List<Note> sounds, Integer[] contour, int voice){
//		List<Note> contours = new ArrayList<>();
//		int index = 0;
//		int nextContour = 0;
//		int size = sounds.size();
//		for (int i = 0; i < size; i++) {
//			Note sound = sounds.get(i);
//			index = (index + nextContour) % chordNotes.size();
//			Note note = getNextNote(chordNotes, index);
//			note.setPosition(sound.getPosition());
//			note.setLength(sound.getLength());
//			note.setVoice(voice);
//			if (!contours.isEmpty()) {
//				Note lastAddedNote = contours.get(contours.size() - 1);
//				if (lastAddedNote.getPitchClass() < note.getPitchClass()) {
//					updateNotePitch(note, lastAddedNote.getOctave() - 1);
//				}else if (lastAddedNote.getPitchClass() > note.getPitchClass()) {
//					updateNotePitch(note, lastAddedNote.getOctave() + 1);
//				}
//				if (nextContour == 0){
//					if (lastAddedNote.getPitchClass() == note.getPitchClass()){
//						updateNotePitch(note, lastAddedNote.getOctave());
//					} else if(lastAddedNote.getPitchClass() != note.getPitchClass()) {
//						int interval = ((lastAddedNote.getPitchClass() - note.getPitchClass()) + 12) % 12;
//						if (interval > 6) {
//							updateNotePitch(note, lastAddedNote.getOctave() - 1);
//						}else{
//							updateNotePitch(note, lastAddedNote.getOctave());
//						}
//					}
//				} 
//			}
//			nextContour = getNextContour(contour, i);
//			contours.add(note);
//		}
//		return contours;
//	}
	
	protected List<Note> getContour(List<Note> chordNotes, List<Note> sounds, Integer[] contour, int voice){
		List<Note> contourNotes = new ArrayList<>();
		int index = 0;
		int nextContour = 0;
		int size = sounds.size();
		int chordSize = chordNotes.size();
		for (int i = 0; i < size; i++) {
			Note sound = sounds.get(i);
			index = index + nextContour;
			index = Math.abs((index + chordSize) % chordSize);
			Note note = getNextNote2(chordNotes, index);
			note.setPosition(sound.getPosition());
			note.setLength(sound.getLength());
			note.setVoice(voice);
//			if (!contours.isEmpty()) {
//				Note lastNote = contours.get(contours.size() - 1);
//				if (nextContour > 0) {
//					while (lastNote.getPitch() > note.getPitch()) {
//						note.setPitch(note.getPitch() + 12);
//						note.setOctave(note.getOctave() + 1);
//					}
//				} else if (nextContour < 0) {
//					while (lastNote.getPitch() < note.getPitch()) {
//						note.setPitch(note.getPitch() - 12);
//						note.setOctave(note.getOctave() - 1);
//					}
//				} else if (nextContour == 0) {
//					note.setPitch(lastNote.getPitch());
//					note.setOctave(lastNote.getOctave());
//				}  
//			}
			nextContour = getNextContour(contour, i);
			contourNotes.add(note);
		}
		updatePitchesFromContour(contourNotes, contour);
		return contourNotes;
	}
	
	public void updatePitchesFromContour(List<Note> notes, Integer[] contour){
		int startOctave = notes.get(0).getOctave();
		int size = notes.size() - 1;
		Note firstNote = notes.get(0);
		firstNote.setPitch((startOctave * 12) + firstNote.getPitchClass());
		firstNote.setOctave(startOctave);
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitchClass() - note.getPitchClass();
			int direction = contour[i];
			int interval = Util.calculateInterval(direction, difference);
			nextNote.setPitch(note.getPitch() + interval);
			nextNote.setOctave(nextNote.getPitch()/12);
		}
	}
	
	protected void updateContour(List<RhythmPosition> sounds, Integer[] contour, int voice){
		List<Note> contours = new ArrayList<>();
		int index = voice;
		int nextContour = 0;
		int size = sounds.size();
		for (int i = 0; i < size; i++) {
			RhythmPosition rhythmPosition = sounds.get(i);
			if (!rhythmPosition.getNotes().isEmpty()) {
				Note sound = rhythmPosition.getNotes().get(0);
				//TODO if not rest
				index = (index + nextContour) % rhythmPosition.getSelectableNotes().size();
				Note note = getNextNote(rhythmPosition.getSelectableNotes(), index);
				note.setPosition(sound.getPosition());
				note.setLength(sound.getLength());
				note.setVoice(voice);
				if (!contours.isEmpty()) {
					Note lastAddedNote = contours.get(contours.size() - 1);
					//smooth voice leading
					if ( lastAddedNote.getPitchClass() != note.getPitchClass() && Math.abs(lastAddedNote.getPitchClass() - note.getPitchClass()) > 7) {
						note.setPitch(note.getPitchClass() + (12 * (lastAddedNote.getOctave() - 1)));
						note.setOctave(lastAddedNote.getOctave() - 1);
					} else {
						note.setPitch(note.getPitchClass() + (12 * lastAddedNote.getOctave()));
						note.setOctave(lastAddedNote.getOctave());
					}
					if (nextContour > 0) {
						while (lastAddedNote.getPitch() > note.getPitch()) {
							note.setPitch(note.getPitch() + 12);
							note.setOctave(note.getOctave() + 1);
						}
					} else if (nextContour < 0) {
						while (lastAddedNote.getPitch() < note.getPitch()) {
							note.setPitch(note.getPitch() - 12);
							note.setOctave(note.getOctave() - 1);
						}
					} 
				}
				nextContour = getNextContour(contour, i);
				contours.add(note);
				rhythmPosition.updateNote(note);
			}
		}
	}
	
	private int getNextTexture(Integer[] texture, int i) {
		return texture[i % texture.length];
	}
	
	private Note getNextNote2(List<Note> notes, int index) {
		return notes.get(index).clone();
	}

	private Note getNextNote(List<Note> notes, int index) {
		int size = notes.size();
		return notes.get(Math.abs((index + size) % size)).clone();
	}
	
	protected List<Note> getNextNotes(List<Note> notes, int index, int texture) {
		int size = notes.size();
		List<Note> textureNotes = new ArrayList<>();
		Note firstNote = notes.get((index + size) % size).clone();
		textureNotes.add(firstNote);
		for (int i = 1; i < texture; i++) {
			 Note note = notes.get(((index - i) + size) % size).clone();
//			 note.setVoice(note.getVoice() - 1);
			 Note lastNote = textureNotes.get(textureNotes.size() - 1);
			 while (lastNote.getPitch() < note.getPitch()) {
				note.setPitch(note.getPitch() - 12);
				note.setOctave(note.getOctave() - 1);
			 }
			 textureNotes.add(note);
		}
		return textureNotes;
	}
	
	private int getNextContour(Integer[] contour, int i) {
		return contour[i % contour.length];
	}
	
	public Map<Integer, List<Integer>> splitRhythmPositionsOverHarmonies(int[] rhythmPositions, List<HarmonyPosition> harmonyPositions){
		List<Integer[]> harmonyRanges = new ArrayList<>();
		int harmonyPositionsSize = harmonyPositions.size() - 1;
		for (int i = 0; i < harmonyPositionsSize; i++) {
			HarmonyPosition harmonyPosition = harmonyPositions.get(i);
			HarmonyPosition nextHarmonyPosition = harmonyPositions.get(i + 1);
			Integer[] harmonyLength = new Integer[2];
			harmonyLength[0] = harmonyPosition.getPosition();
			harmonyLength[1] = nextHarmonyPosition.getPosition();
			harmonyRanges.add(harmonyLength);
		}
		return splitRhythmPositionsOverHarmonyRanges(rhythmPositions, harmonyRanges);
	}
	
	public List<Note> getRhythm(List<HarmonyPosition> harmonyPositions, int[] sounds, Integer[] texture, Integer[] contour, int voice){
		List<RhythmPosition> rhythmPositions = getRhythmPositions(harmonyPositions, sounds);
		updateSounds(rhythmPositions);
		updateContour(rhythmPositions, contour, voice);
		updateTexture(rhythmPositions, texture);
		return rhythmPositions.stream().flatMap(r -> r.getNotes().stream()).collect(Collectors.toList());
	}
	
	
	public List<Note> getRhythm(List<HarmonyPosition> harmonyPositions, int[] sounds, Integer[] contour, int voice, int textureSize){
		Integer[] texture = IntStream.generate(() -> textureSize)
				.limit(sounds.length)
				.boxed()
				.toArray(Integer[]::new);
		return getRhythm(harmonyPositions, sounds, texture, contour, voice);
	}
	
	public List<Note> getRhythm(List<HarmonyPosition> harmonyPositions, Integer[] contour, int voice, int textureSize, int soundValue){
		int lastPosition = harmonyPositions.get(harmonyPositions.size() - 1).getPosition();
		int[] sounds = IntStream.range(0, (lastPosition / soundValue)).map(i -> i * soundValue).toArray();
		return getRhythm(harmonyPositions, sounds, contour, voice, textureSize);
	}

	private void updateSounds(List<RhythmPosition> rhythmPositions) {
		int size = rhythmPositions.size() - 1;
		for (int i = 0; i < size; i++) {
			RhythmPosition rhythmPosition = rhythmPositions.get(i);
			Note note = new Note();
			note.setPosition(rhythmPosition.getPosition());
			note.setLength(rhythmPositions.get(i + 1).getPosition() - rhythmPosition.getPosition());
			rhythmPosition.updateNote(note);
		}
	}
	
	protected List<RhythmPosition> getRhythmPositions(List<HarmonyPosition> harmonyPositions, int[] sounds){
		Map<Integer, List<Integer>> soundsForHarmonies = splitRhythmPositionsOverHarmonies(sounds, harmonyPositions);
		List<RhythmPosition> rhythmicPositions = new ArrayList<>();
		for (HarmonyPosition harmonyPosition : harmonyPositions) {
			int position = harmonyPosition.getPosition();
			List<Integer> soundPositions = soundsForHarmonies.get(position);
			if (soundPositions != null) {
				for (Integer soundPosition : soundPositions) {
					RhythmPosition rhythmPosition = new RhythmPosition(soundPosition, harmonyPosition.getNotes());
					rhythmicPositions.add(rhythmPosition);
				}
			}
			
		}
		return rhythmicPositions;
	}
	
	public Map<Integer, List<Integer>> splitRhythmPositionsOverHarmonyRanges(int[] rhythmPositions, List<Integer[]> ranges){
		Map<Integer, List<Integer>> rhythmPositionsInRange = new TreeMap<>();
		for (Integer[] range : ranges) {
			for (int index = 0; index < rhythmPositions.length; index++) {
				int position = rhythmPositions[index];
				if (range[0] <= position && position < range[1]) {
					rhythmPositionsInRange.compute(range[0], (k, v) -> {
						if (v == null) {
							List<Integer> list = new ArrayList<>();
							list.add(position);
							return list;
						}else {
							v.add(position);
							return v;
						}
					});
				}
			}
		}
		return rhythmPositionsInRange;
	}
	
}
