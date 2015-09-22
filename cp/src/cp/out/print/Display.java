package cp.out.print;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.CpApplication;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.nsga.MusicSolution;
import cp.nsga.MusicVariable;

@Component
public class Display {

	private static Logger LOGGER = LoggerFactory.getLogger(Display.class.getName());
	
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	 
	 public void view(SolutionSet solutions, double tempo) throws Exception{
		 solutions.sort(Comparator.comparing(MusicSolution::getHarmony).thenComparing(MusicSolution::getMelody));
		  Iterator<Solution> iterator = solutions.iterator();
		  String dateID = generateDateID();
		  int i = 1;
		  while (iterator.hasNext() && i < 11) {
			MusicSolution solution = (MusicSolution) iterator.next();
			String id = dateID + "_" + CpApplication.COUNTER.getAndIncrement();
			LOGGER.info(id);
			LOGGER.info(solution.toString());
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
//			printHarmonies(motive.getHarmonies());
			
			List<CpMelody> reversedMelodies = motive.getMelodies();
			
			Collections.reverse(reversedMelodies);
			viewScore(reversedMelodies, id, tempo);
			generateMusicXml(reversedMelodies, id);
			i++;
			
//			List<MusicalStructure> structures = FugaUtilities.addTransposedVoices(sentences, inputProps.getScale(), 8, 12);
//			sentences.addAll(structures);
//			MusicalStructure structure = FugaUtilities.harmonizeMelody(sentences, inputProps.getScale(), 2, 1, inputProps.getMelodyLength() * 12);
//			sentences.add(structure);
//			MusicalStructure structure2 = FugaUtilities.harmonizeMelody(sentences, inputProps.getScale(), 2, 2, inputProps.getMelodyLength() * 12);
//			sentences.add(structure2);
//			changeLengths(sentences);
//			motive.getHarmonies().stream().flatMap(h -> h.getHarmonicMelodies().stream()).forEach(harmony -> harmony.translateToPitchSpace());

//			printNotes(motive.getHarmonies());
//			motive.getMelodies().stream().forEach(melody -> melody.updateMelodies());

//			printVextab(sentences);
		  }
	  }
	  
		private void printHarmonies(List<CpHarmony> harmonies) {
			harmonies.forEach(h ->  LOGGER.info(h.getChord() + ", "));
//			harmonies.forEach(h ->  LOGGER.info(h.getChord().getPitchClassMultiSet() + ", "));
//			harmonies.forEach(h ->  LOGGER.info(h.getNotes() + ", "));
		}
		
		private void generateMusicXml(List<CpMelody> melodies, String id) throws Exception{
			musicXMLWriter.generateMusicXMLForMelodies(melodies, id);
		}

		private void viewScore(List<CpMelody> melodies, String id, double tempo) {
			melodies.forEach(h ->  LOGGER.info(h.getNotes() + ", "));
			Score score = scoreUtilities.createScoreMelodies(melodies, tempo);
			score.setTitle(id);
			Write.midi(score, "resources/midi/" + id + ".mid");	
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
