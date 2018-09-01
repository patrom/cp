package cp.model.harmony;

import cp.model.note.Interval;
import cp.model.note.Note;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CpHarmony implements Comparable<CpHarmony>{

	private final List<Note> notes;
	private Chord chord;
	private final int position;
	private int end;
	private Chord lowestChord;

	public CpHarmony(List<Note> notes, int position) {
		this.notes = notes;
		this.position = position;
	}
	
	public double getHarmonyWeight(){
		return notes.stream().mapToDouble(n -> n.getPositionWeight()).sum();
	}
	
	public void toChord(){
		chord = new Chord(getBassNote(), notes);
	}
	
	public void transpose(int t){
		notes.forEach(note -> note.setPitchClass((note.getPitchClass() + t) % 12));
		toChord();
	}
	
	protected int getBassNote(){
		int minimumPitch = notes.stream()
					.mapToInt(n -> n.getPitch())
					.min()
					.getAsInt();
		return notes.stream()
				.filter(n -> n.getPitch() == minimumPitch)
				.findFirst()
				.get()
				.getPitchClass();
	}

	public void toChord(int size){
		List<Integer> lowestNotes = notes.stream()
				.mapToInt(n -> n.getPitch())
				.sorted()
				.limit(size)
				.boxed()
				.sorted()
				.collect(Collectors.toList());
		List<Note> lowestChordNotes = notes.stream().filter(note -> lowestNotes.contains(note.getPitch())).sorted(Comparator.comparing(note -> note.getPitch())).collect(Collectors.toList());
		lowestChord = new Chord(lowestChordNotes.get(0).getPitchClass(), lowestChordNotes);
	}
	
	public int beat(int beat){
		return position/beat;
	}
	
	public List<Note> getNotes() {
		return notes;
	}
	
	public Chord getChord() {
		return chord;
	}

	public Chord getLowestChord() {
		toChord(3);
		return lowestChord;
	}

	public int getPosition() {
		return position;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public int compareTo(CpHarmony harmony) {
		if (getPosition() < harmony.getPosition()) {
			return -1;
		} if (getPosition() > harmony.getPosition()) {
			return 1;
		} else {
			return 0;
		}
	}

//	public float getRegister(){
//		int size = notes.size();
//		float total = 0;
//		int intervalCount = 0;
//		for (int j = 0; j < size - 1; j++) {
//			for (int i = j + 1; i < size; i++) {
//				int pitch = notes.get(j).getPitch();
//				int nextPitch = notes.get(i).getPitch();
//				if(Interval.isDissonantInterval(pitch - nextPitch)){
//					float interval = Math.abs(Frequency.getFrequencyDifference(pitch, nextPitch));
//					total = total + interval;
//				}
//				intervalCount++;
//			}
//		}
////		float avg = total/intervalCount;
//		return total;
//	}

//	public float getRegister(int cutOffPitch){
//		List<Integer> pitches = notes.stream()
//				.sorted()
//				.map(n -> n.getPitch())
//				.filter(n -> n <= cutOffPitch)
//				.collect(toList());
//		int size = pitches.size();
//		List<Float> values = new ArrayList<>();
//		float total = 0;
//		for (int j = 0; j < size - 1; j++) {
//			for (int i = j + 1; i < size; i++) {
//				int pitch = pitches.get(j);
//				int nextPitch = pitches.get(i);
//				if(Interval.isDissonantInterval(pitch - nextPitch)){
//					values.add(Math.abs(Frequency.getFrequencyDifference(pitch, nextPitch)));
//				}
//			}
//		}
//		return Collections.min(values);
//	}

    public ChordType getAdditiveChord(){
		Map<Integer, Integer> uniquePitches = new HashedMap<>();
		for (Note note : notes) {
			if(!uniquePitches.containsKey(note.getPitchClass())){
				uniquePitches.put(note.getPitchClass(), note.getPitch());
			}
		}
		List<Integer> pitches = uniquePitches.values().stream().sorted().collect(Collectors.toList());
	    if(uniquePitches.size() > 2 && !containsMinorSecond(pitches) && !containsConsecutiveSeconds(pitches)){
            List<Integer> lowerPitches = pitches.stream()
                    .limit(4)
                    .collect(Collectors.toList());
            int lowestPitch = lowerPitches.get(0);
            int size = lowerPitches.size();
            for (int i = 1; i < size; i++) {
                int nextPitch = lowerPitches.get(i);
                Interval interval = Interval.getEnumInterval(nextPitch - lowestPitch);
                switch (interval.getInterval()){
                    case 7:
                        return ChordType.ANCHOR_7;
                    case 10:
                        return ChordType.ANCHOR_10;
                    case 11:
                        return ChordType.ANCHOR_11;
                }
            }

            if(uniquePitches.size() >= 3){
				Integer firstLowestPitch = pitches.get(0);
				Integer secondLowestPitch = pitches.get(1);
				Integer thirdLowestPitch = pitches.get(2);
				Interval firstInterval = Interval.getEnumInterval(firstLowestPitch - secondLowestPitch);
				Interval secondInterval = Interval.getEnumInterval(firstLowestPitch - thirdLowestPitch);
				if((firstInterval.getInterval() == 3 && secondInterval.getInterval() == 8)
						|| (firstInterval.getInterval() == 8 && secondInterval.getInterval() == 3)){
					return ChordType.ANCHOR_38_MAJ;
				} else if((firstInterval.getInterval() == 5 && secondInterval.getInterval() == 9)
						|| (firstInterval.getInterval() == 9 && secondInterval.getInterval() == 5)){
					return ChordType.ANCHOR_59_MAJ;
				} else if((firstInterval.getInterval() == 4 && secondInterval.getInterval() == 9)
						|| (firstInterval.getInterval() == 9 && secondInterval.getInterval() == 4)){
					return ChordType.ANCHOR_49_MIN;
				} else if((firstInterval.getInterval() == 5 && secondInterval.getInterval() == 8)
						|| (firstInterval.getInterval() == 8 && secondInterval.getInterval() == 5)){
					return ChordType.ANCHOR_58_MIN;
				} else if((firstInterval.getInterval() == 6 && secondInterval.getInterval() == 8)
						|| (firstInterval.getInterval() == 8 && secondInterval.getInterval() == 6)){
					return ChordType.ANCHOR_68_DOM;
				} else if((firstInterval.getInterval() == 3 && secondInterval.getInterval() == 5)
						|| (firstInterval.getInterval() == 5 && secondInterval.getInterval() == 3)){
					return ChordType.ANCHOR_35_DOM;
				} else if((firstInterval.getInterval() == 2 && secondInterval.getInterval() == 6)
						|| (firstInterval.getInterval() == 6 && secondInterval.getInterval() == 2)){
					return ChordType.ANCHOR_26_DOM;
				} else if((firstInterval.getInterval() == 2 && secondInterval.getInterval() == 9)
						|| (firstInterval.getInterval() == 9 && secondInterval.getInterval() == 2)){
					return ChordType.ANCHOR_29_DOM;
				} else if((firstInterval.getInterval() == 4 && secondInterval.getInterval() == 6)
						|| (firstInterval.getInterval() == 6 && secondInterval.getInterval() == 4)){
					return ChordType.ANCHOR_46;
				} else if((firstInterval.getInterval() == 2 && secondInterval.getInterval() == 5)
						|| (firstInterval.getInterval() == 5 && secondInterval.getInterval() == 2)){
					return ChordType.ANCHOR_25;
				} else if((firstInterval.getInterval() == 2 && secondInterval.getInterval() == 8)
						|| (firstInterval.getInterval() == 8 && secondInterval.getInterval() == 2)){
					return ChordType.ANCHOR_28;
				} else if((firstInterval.getInterval() == 8 && secondInterval.getInterval() == 7)){
					return ChordType.ANCHOR_87_MAJ7;
				} else if((firstInterval.getInterval() == 5 && secondInterval.getInterval() == 4)){
					return ChordType.ANCHOR_54_MAJ7;
				} else if((firstInterval.getInterval() == 3 && secondInterval.getInterval() == 6)
						|| (firstInterval.getInterval() == 6 && secondInterval.getInterval() == 3)){
					return ChordType.ANCHOR_36_DIM;
				} else if((firstInterval.getInterval() == 3 && secondInterval.getInterval() == 9)
						|| (firstInterval.getInterval() == 9 && secondInterval.getInterval() == 3)){
					return ChordType.ANCHOR_39_DIM;
				} else if((firstInterval.getInterval() == 6 && secondInterval.getInterval() == 9)
						|| (firstInterval.getInterval() == 9 && secondInterval.getInterval() == 6)){
					return ChordType.ANCHOR_69_DIM;
				}
			}
        }
        return null;
    }

	protected boolean contains2NoteAnchor(){
		List<Integer> pitches = notes.stream()
				.map(n -> n.getPitch())
				.limit(4)
				.sorted()
				.collect(Collectors.toList());
		int lowestPitch = pitches.get(0);
		int size = pitches.size();
		for (int i = 1; i < size; i++) {
			int nextPitch = pitches.get(i);
			Interval interval = Interval.getEnumInterval(nextPitch - lowestPitch);
			switch (interval.getInterval()){
				case 7:
				case 10:
				case 11:
					return true;
			}
		}
		return false;
	}

	protected boolean containsMinorSecond(List<Integer> pitches){
		int size = pitches.size() - 1;
		//root b9 allowed
		Integer root = pitches.get(0);
		Integer next = pitches.get(1);
		int intervalRoot = next - root;
		if(intervalRoot == 1){
			return true;
		}
		for (int i = 1; i < size; i++) {
			int pitch = pitches.get(i);
			int nextPitch = pitches.get(i + 1);
			Interval interval = Interval.getEnumInterval(nextPitch - pitch);
			if(interval.getInterval() == 1){
				return true;
			}
		}
		return false;
	}

    protected boolean containsConsecutiveSeconds(List<Integer> pitches){
        int size = pitches.size() - 1;
        boolean containsOne = false;
        for (int i = 0; i < size; i++) {
            int pitch = pitches.get(i);
            int nextPitch = pitches.get(i + 1);
            int interval = nextPitch - pitch;
            if(interval == 2){
                if (!containsOne){
                    containsOne = true;
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    public double getRegister(int cutOffPitch){
		List<Integer> pitches = notes.stream()
				.map(n -> n.getPitch())
				.sorted()
				.filter(n -> n <= cutOffPitch)
				.collect(toList());
		int size = pitches.size();
		for (int j = 0; j < size - 1; j++) {
			for (int i = j + 1; i < size; i++) {
				int pitch = pitches.get(j);
				int nextPitch = pitches.get(i);
				Interval interval = Interval.getEnumInterval(pitch - nextPitch);
				switch (interval.getInterval()){
					case 1:
						return 1.0;
					case 2:
						return 1.0;
					case 10:
						return 0.5;
					case 11:
						return 0.9;
					case 6:
						return 0.7;
				}
			}
		}
		return 0;
	}

}
