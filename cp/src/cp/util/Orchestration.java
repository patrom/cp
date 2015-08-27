package cp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequence;
import javax.sound.midi.InvalidMidiDataException;

import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;

public class Orchestration {

	public static void main(String[] args) throws InvalidMidiDataException, IOException {
		
		
		int[][] chords = {{60,64,67},{59,62,67},{58,63,67}};
		int length = 48;
		
		int chordSize = chords[0].length;
		List<MelodyInstrument> melodies = new ArrayList<>();
		for (int voice = 0; voice < chordSize; voice++) {
			List<Note> notes = new ArrayList<>();
			for (int i = 0; i < chords.length; i++) {
				int[] chord = chords[i];
				notes.add(NoteBuilder.note().pitch(chord[voice]).pos(i * length).len(length).build());
			}
			MelodyInstrument melodyInstrument = new MelodyInstrument(notes, voice);
			melodies.add(melodyInstrument);
		}
		//transpose
		for (int i = 0; i < chords.length; i++) {
			int[] chord = chords[i];
			for (int j = 0; j < chord.length; j++) {
				chord[j] = chord[j] + 12;
			}
		}
		
		melodies.stream().forEach(m -> System.out.println(m.getNotes()));
		
		String outputPath = "resources/orchestration.mid";
		MidiDevicesUtil midiDevicesUtil = new MidiDevicesUtil();
		Sequence seq = midiDevicesUtil.createSequence(melodies);
		midiDevicesUtil.write(seq, outputPath);
	}
}
