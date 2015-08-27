package cp.model.harmony;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cp.model.dissonance.Dissonance;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.PitchSpace;
import cp.model.note.Note;

public class Harmony implements Comparable<Harmony>{
	
	protected int position;
	private int length;
	private double positionWeight;
	private double innerMetricWeight;
	private List<HarmonicMelody> harmonicMelodies = new ArrayList<>();
	private PitchSpace pitchSpace;
	private Chord chord;
	private boolean rootPosition;
	
	public Harmony(int position, int length, List<HarmonicMelody> harmonicMelodies) {
		this.position = position;
		this.length = length;
		this.harmonicMelodies = harmonicMelodies;
	}
	
	public List<Note> getNotes() {
		return harmonicMelodies.stream()
				.map(harmonicMelodic -> harmonicMelodic.getHarmonyNote())
				.collect(toList());
	}
	
	public List<Integer> getPitchClasses(){
		return harmonicMelodies.stream()
				.map(harmonicMelodic -> harmonicMelodic.getHarmonyNote().getPitchClass())
				.collect(toList());
	}
	
	public void toChord(){
		chord = new Chord(getBassNote());
		harmonicMelodies.stream()
			.map(harmonicMelodic -> harmonicMelodic.getHarmonyNote())
			.forEach(note -> chord.addPitchClass(note.getPitchClass()));
	}
	
	private int getBassNote(){
		return harmonicMelodies.stream()
					.filter(h -> h.getVoice() == 0)
					.mapToInt(h -> h.getHarmonyNote().getPitchClass()).
					findFirst()
					.getAsInt();
	}

	public void translateToPitchSpace() {
		pitchSpace.translateToPitchSpace();
	}
	
	public double getPositionWeight() {
		return positionWeight;
	}

	public void setPositionWeight(double positionWeight) {
		harmonicMelodies.stream()
			.map(harmonicMelodic -> harmonicMelodic.getHarmonyNote())
			.forEach(note -> note.setPositionWeight(positionWeight));
		this.positionWeight = positionWeight;
	}
	
	public void transpose(int t){
		harmonicMelodies.stream()
			.map(harmonicMelodic -> harmonicMelodic.getHarmonyNote())
			.forEach(note -> note.setPitchClass((note.getPitchClass() + t) % 12));
		toChord();
	}

	@Override
	public int compareTo(Harmony harmony) {
		if (getPosition() < harmony.getPosition()) {
			return -1;
		} if (getPosition() > harmony.getPosition()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void replaceHarmonicMelody(HarmonicMelody harmonicMelody){
		Optional<HarmonicMelody> optional = this.harmonicMelodies.stream()
				.filter(hm -> hm.getVoice() == harmonicMelody.getVoice())
				.findFirst();
		if (optional.isPresent()) {
			this.harmonicMelodies.remove(optional.get());
		}
		this.harmonicMelodies.add(harmonicMelody);
	}

	public List<HarmonicMelody> getHarmonicMelodies() {
		return harmonicMelodies;
	}
	
	public void setHarmonicMelodies(List<HarmonicMelody> harmonicMelodies) {
		this.harmonicMelodies = harmonicMelodies;
		pitchSpace.setHarmonicMelodies(harmonicMelodies);
	}
	
	public void addHarmonicMelody(HarmonicMelody harmonicMelody){
		harmonicMelodies.add(harmonicMelody);
		pitchSpace.setHarmonicMelodies(harmonicMelodies);
	}
	
	public PitchSpace getPitchSpace() {
		return pitchSpace;
	}
	
	public void setPitchSpace(PitchSpace pitchSpace) {
		this.pitchSpace = pitchSpace;
		pitchSpace.setHarmonicMelodies(harmonicMelodies);
	}
	
	public int getPosition() {
		return position;
	}
	
	public double getBeat(int divider) {
		return Math.floor(position/divider);
	}

	public Chord getChord() {
		return chord;
	}

	public int getLength() {
		return length;
	}

	public double getInnerMetricWeight() {
		return innerMetricWeight;
	}

	public void setInnerMetricWeight(double innerMetricWeight) {
		this.innerMetricWeight = innerMetricWeight;
	}
	
	public void searchBestChord(Dissonance dissonance){
//		Set<Integer> allPositions = harmonicMelodies.stream().flatMap(m -> m.getMelodyNotes().stream())
//				.map(note -> note.getPosition())
//				.collect(toCollection(TreeSet::new));
//		int voice = 0;
		Map<Integer, List<Note>> harmonyPositions = harmonicMelodies.stream()
				.flatMap(harmonicMelody -> harmonicMelody.getMelodyNotes().stream())
				.collect(Collectors.collectingAndThen(
						groupingBy(note -> note.getPosition(), toList()),
			 			(map) -> { 
			 				Map<Integer, List<Note>> sortedMap = new TreeMap<>(map);
			 				List<Note> previous = new ArrayList<>();
			 				for (Entry<Integer, List<Note>> harmonyPosition : sortedMap.entrySet()) {
			 					List<Note> current = harmonyPosition.getValue();
			 					if (previous.isEmpty()) {//first element
			 						previous = new ArrayList<>(current);
								} else {
									List<Note> temp = new ArrayList<>(previous);
				 					for (Note currentNote : current) {
										for (Note previousNote : previous) {
											if (currentNote.getVoice() == previousNote.getVoice()) {
												temp.remove(previousNote);
												temp.add(currentNote);
											}
										}
									}
				 					temp.sort(comparing(Note::getVoice));
				 					harmonyPosition.setValue(temp);
				 					previous = new ArrayList<>(temp);
								}
							}
			 				return sortedMap;}
			 	));
		harmonyPositions.forEach((k,v) -> System.out.println(k + ":" + v));
		
		List<Note> bestChord = null;
		double max = 0;
		for (Entry<Integer, List<Note>> harmonyPosition : harmonyPositions.entrySet()) {
//			harmonyPosition.getValue().stream().map(note -> {
//				Chord chord = new Chord();
//				chord.addPitchClass(note.getPitchClass());
//				return chord;
//			}).max(Comparator.comparing(Chord::getChordType));
			Chord chord = new Chord(0);
			for (Note note : harmonyPosition.getValue()){
				chord.addPitchClass(note.getPitchClass());
			}
			double diss = dissonance.getDissonance(chord);
			if (diss > max) {
				max = diss;
				bestChord = harmonyPosition.getValue();
			}
		}
		
		//best chord to harmony notes
		for (Note note : bestChord) {
			harmonicMelodies.stream().filter(h -> h.getVoice() == note.getVoice()).forEach(h -> h.setHarmonyNote(note));
		}
	}

	public boolean isRootPosition() {
		return rootPosition;
	}

}
