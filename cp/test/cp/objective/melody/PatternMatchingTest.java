package cp.objective.melody;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.midi.MelodyInstrument;
import cp.midi.MidiInfo;
import cp.midi.MidiParser;
import cp.model.harmony.Chord;
import cp.model.note.Interval;
import cp.model.note.Note;
import cp.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class PatternMatchingTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PatternMatchingTest.class);
	
	@Autowired
	private MidiParser midiParser;
	
	@Test
	public void readIntervals() throws InvalidMidiDataException, IOException{
		File dir = new File("resources/test");
		for(File melodyFile : dir.listFiles()){
			MidiInfo midiInfo = midiParser.readMidi(melodyFile);
			LOGGER.info(melodyFile.getName());
			List<MelodyInstrument> melodies = midiInfo.getMelodies();
			List<Note> notes = melodies.get(0).getNotes();
			StringBuilder intervalBuilder = new StringBuilder();
			StringBuilder intervalClassBuilder = new StringBuilder();
			int size = notes.size() - 1;
			for (int i = 0; i < size; i++) {
				Note note = notes.get(i);
				Note nextNote = notes.get(i + 1);
				Interval interval = Interval.getEnumInterval(note.getPitch() - nextNote.getPitch());
				intervalBuilder.append(interval.getIntervalName());
				intervalClassBuilder.append(Util.intervalClass(interval.getInterval()));
			}
			LOGGER.info(intervalBuilder.toString());
			LOGGER.info(intervalClassBuilder.toString());
			findPatterns(intervalBuilder.toString());
			findPatterns(intervalClassBuilder.toString());
		}
	}
	
	@Test
	public void findSetClasses() throws InvalidMidiDataException, IOException{
		File dir = new File("resources/test");
		
		for(File melodyFile : dir.listFiles()){
			MidiInfo midiInfo = midiParser.readMidi(melodyFile);
			LOGGER.info(melodyFile.getName());
			List<MelodyInstrument> melodies = midiInfo.getMelodies();
			List<Note> notes = melodies.get(0).getNotes();
			notes.forEach(n -> System.out.print(n.getPitch() + ", "));
			System.out.println();
			findSetClassesForLength(notes, 3);
			findSetClassesForLength(notes, 4);
		}
	}

	private void findSetClassesForLength(List<Note> notes, int length) {
		LOGGER.info("-----Length: " + length  + "----------");
		int size = notes.size() - length + 1;
		for (int j = 0; j < length; j++) {
			List<Chord> chords = new ArrayList<>();
			for (int i = 0; j + i < size; i = i + length) {
				List<Note> subListNotes = notes.subList(j + i, j + i + length);
				chords.add(new Chord(subListNotes.get(0).getPitchClass(), subListNotes));
			}
			Map<String, Integer> map  = countDuplicateSetClass(chords);
			LOGGER.info("SetClasses: " + map);
			chords.forEach(ch -> LOGGER.info("Chord type: " + ch.getForteName() + ", notes: "+ ch.getPitchClassMultiSet()));
			LOGGER.info("--------------------");
		}
	}
	
	private Map<String, Integer> countDuplicateSetClass(List<Chord> chords){
		List<String> list = chords.stream().map(ch -> ch.getForteName()).collect(toList());
		Set<String> uniqueSet = new HashSet<String>(list);
		return uniqueSet.stream().collect(toMap(k -> k, v -> Collections.frequency(list, v)));
	}

	private void findPatterns(String melodyIntervals) {
		LOGGER.info(melodyIntervals);
		List<String> patterns = splitString(melodyIntervals, 3);
		for (String pattern : patterns) {
			int count = countSubstring(pattern, melodyIntervals);
			if (count > 1) {
				LOGGER.info(pattern + ": " + count);
			}
		}
	}
	
	private static List<String> splitString(String input, int length){
		List<String> strings = new ArrayList<String>();
		int size = input.length() - length + 1;
		for (int i = 0; i < size; i++) {
			strings.add(input.substring(i, i + length));
		}
		return strings;
	}
	
	
	public static void main(String[] args) {
		List<String> strings = splitString("acskimksdfl", 4);
		System.out.println(strings);
	}
	
	public static int countSubstring(String subStr, String str){
		// the result of split() will contain one more element than the delimiter
		// the "-1" second argument makes it not discard trailing empty strings
		return str.split(Pattern.quote(subStr), -1).length - 1;
	}
 
}
	
	