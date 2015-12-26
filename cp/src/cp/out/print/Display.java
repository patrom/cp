package cp.out.print;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import jm.midi.MidiParser;
import jm.music.data.Score;
import jm.util.View;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.CpApplication;
import cp.midi.MidiDevicesUtil;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicSolution;
import cp.nsga.MusicVariable;

@Component
public class Display {

	private static Logger LOGGER = LoggerFactory.getLogger(Display.class);
	
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	 
	 public void view(SolutionSet solutions, double tempo) throws Exception{
//		 solutions.sort(Comparator.comparing(MusicSolution::getMelody).thenComparing(MusicSolution::getHarmony));
		 solutions.sort(Comparator.comparing(MusicSolution::getResolution).thenComparing(MusicSolution::getHarmony).thenComparing(MusicSolution::getMelody));
		  Iterator<Solution> iterator = solutions.iterator();
		  String dateID = generateDateID();
		  int i = 1;
		  while (iterator.hasNext() && i < 11) {
			MusicSolution solution = (MusicSolution) iterator.next();
			String id = dateID + "_" + CpApplication.COUNTER.getAndIncrement();
			LOGGER.info(id);
			LOGGER.info(solution.toString());
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			printHarmonies(motive.getHarmonies());
			viewScore(motive.getMelodyBlocks(), id, tempo);
			generateMusicXml(motive.getMelodyBlocks(), id);
			i++;
			
//			printVextab(sentences);
		  }
	  }
	  
		private void printHarmonies(List<CpHarmony> harmonies) {
			harmonies.forEach(h ->  LOGGER.info(h.getChord() + ", " + h.getHarmonyWeight()));
//			harmonies.forEach(h ->  LOGGER.info(h.getNotes() + ", "));
		}
		
		private void generateMusicXml(List<MelodyBlock> melodies, String id) throws Exception{
			musicXMLWriter.generateMusicXMLForMelodies(melodies, id);
		}

		private void viewScore(List<MelodyBlock> melodies, String id, double tempo) throws InvalidMidiDataException, IOException {
			melodies.forEach(m ->  LOGGER.info(m.getMelodyBlockContour() + ", "));
			melodies.forEach(m ->  LOGGER.info(m.getMelodyBlockNotesWithRests() + ", "));
			Score score = scoreUtilities.createScoreMelodies(melodies, tempo);
			score.setTitle(id);
			Sequence sequence = midiDevicesUtil.createSequence(melodies, (int)tempo);
			midiDevicesUtil.write(sequence, "resources/midi/" + id + ".mid");
			View.notate(score);	
		}
		
		private String generateDateID(){
			LocalDateTime currentDateTime = LocalDateTime.now();
			return currentDateTime.format(DateTimeFormatter.ofPattern("ddMM_HHmm"));
		}
		
//		private static void printVextab(List<Harmony> harmonies) {
//			String vexTab = ScoreUtilities.createVexTab(harmonies, inputProps);
//			LOGGER.info(vexTab);
//		}

}
