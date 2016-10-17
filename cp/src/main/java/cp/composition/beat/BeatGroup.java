package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BeatGroup {

	protected int length;
	protected List<RhythmCombination> rhythmCombinations;

	public BeatGroup(int length, List<RhythmCombination> rhythmCombinations) {
		this.length = length;
		this.rhythmCombinations = rhythmCombinations;
	}

	public abstract BeatGroup clone(int length);

	public abstract int getType();

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getBeatLength() {
		return getType() * length;
	}

	public List<Note> getNotes() {
		int size = rhythmCombinations.size();
		int beatLength = getBeatLength() / size;
		RhythmCombination rhythmCombination = rhythmCombinations.get(0);
		List<Note> notes = rhythmCombination.getNotes(beatLength);
		List<Note> melodyNotes = new ArrayList<>(notes);
		for (int i = 1; i < size; i++) {
			rhythmCombination = rhythmCombinations.get(i);
			int beatPosition = i * beatLength;
			notes = rhythmCombination.getNotes(beatLength);
			notes.forEach(n -> {n.setPosition(n.getPosition() + beatPosition);});
			melodyNotes.addAll(notes);
		}
		return melodyNotes;
	}

	public List<Note> getNotesRandom() {
		RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
		return rhythmCombination.getNotes(getBeatLength());
	}

	public void setRhythmCombinations(List<RhythmCombination> rhythmCombinations) {
		this.rhythmCombinations = rhythmCombinations;
	}

}
