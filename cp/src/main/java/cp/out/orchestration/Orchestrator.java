package cp.out.orchestration;


import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import cp.config.InstrumentConfig;
import cp.model.humanize.Humanize;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.out.orchestration.notetemplate.TwoNoteTemplate;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.orchestra.StringOrchestra;
import cp.out.orchestration.orchestra.ViennaOrchestra;
import cp.out.orchestration.quality.BrilliantWhite;
import cp.out.orchestration.quality.GlowingRed;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.orchestration.quality.PleasantGreen;
import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Orchestrator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Orchestrator.class);

	@Autowired
	private OneNoteEven oneNoteEven;
	@Autowired
	private TwoNoteEven twoNoteEven;
	@Autowired
	private ThreeNoteEven threeNoteEven;
	@Autowired
	private FourNoteEven fourNoteEven;
	
	@Autowired
	private ThreeNoteTriplet threeNoteUneven;
	@Autowired
	private TwoNoteTriplet twoNoteUneven;
	@Autowired
	private OneNoteTriplet oneNoteUneven;
	@Autowired
	private ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	private FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	private FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	private SixNoteSexTuplet sixNoteSexTuplet;
	
	@Autowired
	private TwoNoteTemplate twoNoteTemplate;
	@Autowired
	private InstrumentConfig instrumentConfig;
	
	@Autowired
	private PleasantGreen pleasantGreen;
    @Autowired
    private GlowingRed glowingRed;
	@Autowired
	private BrilliantWhite brilliantWhite;
	@Autowired
	private Humanize humanize;

    public Orchestra orchestrateMelody(List<Note> notes, int length, List<Instrument> instruments, int instrumentSize){
        Orchestra orchestra = new ViennaOrchestra();
        int totalLength = 0;
        for (int i = 2; i <= instrumentSize; i++) {
            List<List<Instrument>> instrumentCombinations = Generator.combination(instruments)
                    .simple(i)
                    .stream()
                    .collect(Collectors.toList());
            for (List<Instrument> combinations : instrumentCombinations) {
                for (Instrument instrument : combinations) {
                    List<Note> clonedNotes = cloneNotes(notes, totalLength);
                    orchestra.updateInstrumentInRange(instrument, clonedNotes);
                }
                totalLength = totalLength + length;
            }
        }

        orchestra.insertRests();
        return orchestra;
    }

    public Orchestra orchestrateMelodyCloseCombinations(List<Note> notes, int length, List<Instrument> instrumentsQuality1, int instrumentSize1,
                                                        List<Instrument> instrumentsQuality2, int instrumentSize2){
        Orchestra orchestra = new ViennaOrchestra();
        List<List<Instrument>> instrumentCombinations = new ArrayList<>();
        int totalLength = 0;
        for (int i = 1; i <= instrumentSize1; i++) {
            List<List<Instrument>> instrumentCombinationsQuality1 = Generator.combination(instrumentsQuality1)
                    .simple(i)
                    .stream()
                    .collect(Collectors.toList());
            List<List<Instrument>> instrumentCombinationsQuality2 = new ArrayList<>();
            for (int j = 1; j <= instrumentSize2; j++) {
                instrumentCombinationsQuality2 = Generator.combination(instrumentsQuality2)
                        .simple(j)
                        .stream()
                        .collect(Collectors.toList());
            }
            for (int j = 0; j < instrumentCombinationsQuality1.size(); j++) {
                List<Instrument> instruments1 = instrumentCombinationsQuality1.get(j);
                for (int k = 0; k < instrumentCombinationsQuality2.size(); k++) {
                    List<Instrument> instruments2 = instrumentCombinationsQuality2.get(k);
                    List<Instrument> instrumentsCombinations = new ArrayList<>();
                    instrumentsCombinations.addAll(instruments1);
                    instrumentsCombinations.addAll(instruments2);
                    List<Instrument> distinctInstruments = instrumentsCombinations.stream().distinct().collect(Collectors.toList());
                    for (Instrument instrument : distinctInstruments) {
                        List<Note> clonedNotes = cloneNotes(notes, totalLength);
                        orchestra.updateInstrumentInRange(instrument, clonedNotes);
                    }
                    totalLength = totalLength + length;
                }
            }
        }

        orchestra.insertRests();
        return orchestra;
    }

    private List<Note> cloneNotes(List<Note> notes, int totalLength) {
        return notes.stream().map(note -> {
                        Note clone = note.clone();
                        clone.setPosition(note.getPosition() + totalLength);
                        return clone;
                    }).collect(Collectors.toList());
    }

    public Orchestra orchestrate(Map<Integer, List<Note>> notesPerVoice) {
		//configuration instruments
		StringOrchestra orchestra = new StringOrchestra();
//		orchestra.setHumanize(humanize);
		orchestra.setNotesPerVoice(notesPerVoice);
		Technical technical = Technical.CON_SORDINO   ;
		orchestra.setViolinsI(new MelodyOrchestrationBuilder()
				.setVoice(1)//order in score!!!
//				.setArticulation(Articulation.MARCATO  )
				.setTechnical(Technical.CON_SORDINO)
//				.setDynamic(Dynamic.F)
//				.setOrchestralQuality(pleasantGreen)
				.setOrchestralTechnique(null));

		orchestra.setViolinsII(new MelodyOrchestrationBuilder()
				.setVoice(2)
//				.setArticulation(Articulation.DETACHED_LEGATO   )
				.setTechnical(technical )
//				.setDynamic(Dynamic.F)
//				.setOrchestralQuality(pleasantGreen)
				.setOrchestralTechnique(null));

		orchestra.setViolas(new MelodyOrchestrationBuilder()
				.setVoice(3)
//				.setArticulation(Articulation.STACCATO)
				.setTechnical(Technical.CON_SORDINO)
				.setDynamic(null)
//				.setOrchestralQuality(pleasantGreen)
				.setOrchestralTechnique(null));

		orchestra.setCellos(new MelodyOrchestrationBuilder()
				.setVoice(4)
//				.setArticulation(Articulation.MARCATO)
				.setTechnical(Technical.CON_SORDINO)
//				.setDynamic(Dynamic.F)
//				.setOrchestralQuality(pleasantGreen)
				.setOrchestralTechnique(null));

//		orchestra.setBasses(new MelodyOrchestrationBuilder()
//				.setVoice(4)
////				.setArticulation(Articulation.STACCATO)
//				.setTechnical(Technical.PIZZ)
//				.setDynamic(null)
////				.setOrchestralQuality(pleasantGreen)
//				.setOrchestralTechnique(null));

		//execute cofiguration
		orchestra.execute();
		return orchestra;




//		for (int i = 0; i < 5; i++) {
//			Instrument instrumentToUpdate = orchestra.getRandomEmptyInstrument();
//			if(brilliantWhite.hasInstrument(instrumentToUpdate)){
//				Instrument instrumentRegister = brilliantWhite.getInstrument(instrumentToUpdate.getInstrumentName());
//				orchestra.setInstrument(orchestra.getClarinet(), instrumentToUpdate, instrumentRegister::updateInQualityRange);
//			};
//		}
//		Map<InstrumentMapping, List<Note>> melodiesForInstrument = new TreeMap<>();
//		for (Map.Entry<Integer,InstrumentMapping> entry : instrumentConfig.getInstruments().entrySet()) {
//			MelodyBlock melodyBlock = melodyBlocks.get(entry.getTimeLineKey());
//			InstrumentMapping instrumentMapping = entry.getValue();
//			melodiesForInstrument.put(instrumentMapping, melodyBlock.getMelodyBlockNotesWithRests());
//			for (InstrumentMapping dependantInstrumentMapping : instrumentMapping.getDependantInstruments()) {
//				List<Note> notes = dependantInstrumentMapping.duplicate(melodyBlock.getMelodyBlockNotesWithRests(), 0);
//				melodiesForInstrument.put(dependantInstrumentMapping, notes);
//			}
//		}
		
//		orchestra.setFlute(orchestra.duplicate(orchestra.getOboe()));
//		orchestra.setOboe(melodyBlocks.get(0).getMelodyBlockNotesWithRests());
//		orchestra.setClarinet(melodyBlocks.get(1).getMelodyBlockNotesWithRests());
//		orchestra.setClarinet(orchestra.duplicate(orchestra.getFlute()), pleasantGreen.getInstrument(InstrumentName.CLARINET.getName())::updateInQualityRange);
//		orchestra.setBassoon(orchestra.duplicate(orchestra.getOboe(), -12));
//
////		orchestra.setTrumpet(orchestra.duplicate(orchestra.getClarinet()), brilliantWhite.getInstrument(InstrumentName.TRUMPET.getName())::updateInQualityRange);
//
//		orchestra.setViolin1(orchestra.duplicate(orchestra.getClarinet()), pleasantGreen.getBasicInstrument(InstrumentName.VIOLIN_I.getName())::updateInQualityRange);
//		orchestra.setViola(orchestra.duplicate(orchestra.getClarinet()), pleasantGreen.getBasicInstrument(InstrumentName.VIOLA.getName())::updateInQualityRange);
//		orchestra.setCello(orchestra.duplicate(orchestra.getFlute(), 0), pleasantGreen.getInstrument(InstrumentName.CELLO.getName())::updateInQualityRange);
//		orchestra.setBass(notes);
//		ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48, 5);
//		map.put(new CelloSolo(0, 1), chordOrchestration.orchestrate(oneNoteEven::pos3, 12, C(4)));
//		map.put(new DoubleBass(5, 1), chordOrchestration.orchestrate(twoNoteEven::pos13, 48, C(3), E(3)));
//		map.put(new ViolaSolo(1, 2), chordOrchestration.orchestrate(twoNoteEven::pos13, 24, C(5), E(5)));
//		map.put(new ViolinSolo(2, 2), chordOrchestration.orchestrate(twoNoteEven::pos34, 12, C(6), E(6), G(6)));
//		map.put(new Flute(3, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, C(6), E(6), G(6), C(7)));
//		map.put(new Oboe(6, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, G(5), Fsharp(5)));
//		map.put(new Clarinet(7, 3), chordOrchestration.orchestrate(threeNoteUneven::pos123, 12, G(5), Fsharp(5)));
//		map.put(new Bassoon(8, 3), chordOrchestration.orchestrate(sixNoteSexTuplet::pos123456, 12, C(4), E(4)));
//		orchestra.setOboe(chordOrchestration.orchestrate(new int[]{0,4},twoNoteTemplate::note011Repetition,threeNoteUneven::pos123, 12, twoNoteUneven::pos13, 12, Articulation.STACCATO));
//		map.put(new Trumpet(9, 4), chordOrchestration.orchestrate(new int[]{4,0},twoNoteTemplate::note01,twoNoteEven::pos13, 12));
//		generateMusicXml(id, melodiesForInstrument);
//		writeMidi(id, melodiesForInstrument);
	}
	

}
