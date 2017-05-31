package cp.model.harmony;

import cp.model.note.Interval;
import cp.model.note.Note;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CpHarmony implements Comparable<CpHarmony>{

	private final List<Note> notes;
	private Chord chord;
	private final int position;
	private int end;

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
	
	private int getBassNote(){
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
	
	public int beat(int beat){
		return position/beat;
	}
	
	public List<Note> getNotes() {
		return notes;
	}
	
	public Chord getChord() {
		return chord;
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

	public double getRegister(int cutOffPitch){
		List<Integer> pitches = notes.stream()
				.sorted()
				.map(n -> n.getPitch())
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
