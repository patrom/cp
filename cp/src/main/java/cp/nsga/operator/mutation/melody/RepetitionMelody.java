package cp.nsga.operator.mutation.melody;

import cp.composition.beat.BeatGroupTwo;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.util.RandomUtil;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 23/04/2017.
 */
@Component(value = "repetitionMelody")
public class RepetitionMelody extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(RepetitionMelody.class);

    @Autowired
    private TimeLine timeline;

    private List<Integer> melodySizes = Stream.of(2,4,6).collect(toList());//size of melody times 2

    private List<CpMelody> melodies = new ArrayList<>();

    @Autowired
    public RepetitionMelody(HashMap<String, Object> parameters) {
        super(parameters);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pitch(60).octave(5).pc(0).len(DurationConstants.EIGHT).lev(Dynamic.MF.getLevel()).voice(1).build());
        notes.add(note().pos(DurationConstants.EIGHT).pitch(61).octave(5).pc(1).len(DurationConstants.SIXTEENTH).lev(Dynamic.MF.getLevel()).voice(1).build());
        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pitch(62).octave(5).pc(2).len(DurationConstants.SIXTEENTH).lev(Dynamic.MF.getLevel()).voice(1).build());
        CpMelody melody = new CpMelody(notes, 1, 0 , DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER, null));
        melodies.add(melody);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();

            MelodyBlock melodyBlockToCopy = motive.getRandomMutableMelody();
            List<CpMelody> melodyBlocksToCopy = melodyBlockToCopy.getMelodyBlocks();
            MelodyBlock melodyBlockToReplace = motive.getRandomMutableMelody();
            List<CpMelody> melodyBlocksToReplace = melodyBlockToReplace.getMelodyBlocks();
            if (melodies.isEmpty()) {
                int maxSizeMelodies = RandomUtil.getRandomFromList(melodySizes);
                int startIndex = RandomUtil.randomInt(0, melodyBlocksToCopy.size() - maxSizeMelodies);
                int startReplaceIndex = RandomUtil.randomInt(0, melodyBlocksToReplace.size() - maxSizeMelodies);
                if(startIndex != startReplaceIndex){
                    List<CpMelody> melodiesToCopy = melodyBlocksToCopy.subList(startIndex, startIndex  + maxSizeMelodies / 2);
                    List<CpMelody> melodiesToReplace = melodyBlocksToReplace.subList(startReplaceIndex, startReplaceIndex  + maxSizeMelodies / 2);

                    int melodiesLength = melodiesToCopy.stream().mapToInt(m -> m.getBeatGroupLength()).sum();
                    int melodiesToReplaceLength = melodiesToReplace.stream().mapToInt(m -> m.getBeatGroupLength()).sum();
                    if (melodiesLength == melodiesToReplaceLength) {
                        List<CpMelody> clonedMelodies = cloneMelodies(melodiesToCopy, melodyBlockToReplace.getVoice());
                        melodyBlockToReplace.repeatMelody(clonedMelodies, melodiesToReplace, timeline);
//                        LOGGER.info("repeat melody: " + melodyBlockToReplace.getVoice() + ", " + melodyBlockToCopy.getVoice());
//                        LOGGER.info("repeat index: " + startReplaceIndex + ", " + startIndex);
//                        LOGGER.info("repeat melodiesLength: " + melodiesLength);
//                        LOGGER.info("repeat melody: " + maxSizeMelodies);
                    }
                }
            } else{
                int maxSizeMelodies = melodies.size() * 2;
                int startReplaceIndex = RandomUtil.randomInt(0, melodyBlocksToReplace.size() - maxSizeMelodies);
                List<CpMelody> melodiesToReplace = melodyBlocksToReplace.subList(startReplaceIndex, startReplaceIndex  + maxSizeMelodies / 2);

                int melodiesLength = melodies.stream().mapToInt(m -> m.getBeatGroupLength()).sum();
                int melodiesToReplaceLength = melodiesToReplace.stream().mapToInt(m -> m.getBeatGroupLength()).sum();
                if (melodiesLength == melodiesToReplaceLength) {
                    List<CpMelody> clonedMelodies = cloneMelodies(melodies, melodyBlockToReplace.getVoice());
//                    melodyBlockToReplace.repeatMelody(clonedMelodies, melodiesToReplace, timeline);

                    int positionDifference = melodiesToReplace.get(0).getStart() - clonedMelodies.get(0).getStart();
                    melodies.stream()
                            .map(m -> {
                                m.setStart(m.getStart() + positionDifference);
                                m.setEnd(m.getEnd() + positionDifference);
                                m.transposePitchClasses(0, positionDifference, timeline);
                                return m;
                            })
                            .flatMap(m -> m.getNotes().stream())
                            .forEach(n -> n.setPosition(n.getPosition() + positionDifference));
                    int i = 0;
                    for (CpMelody clonedMelody : clonedMelodies) {
                        int index =  melodyBlockToReplace.getMelodyBlocks().indexOf(melodiesToReplace.get(i));
                        melodyBlockToReplace.getMelodyBlocks().set(index, clonedMelody);
                        i++;
                    }

//                    LOGGER.info("start : " + melodiesToReplace.get(0).getStart());
//                    LOGGER.info("repeat melody: " + melodyBlockToReplace.getVoice());
//                    LOGGER.info("repeat index: " + startReplaceIndex );

//                    melodyBlockToReplace.getMelodyBlockNotesWithRests().forEach(n -> System.out.println(n));
                }
            }
        }
    }

    private List<CpMelody> cloneMelodies(List<CpMelody> melodies, int voice){
        List<CpMelody> clonedMelodies = melodies.stream().map(m -> m.clone(voice)).collect(Collectors.toList());
//        if(RandomUtil.toggleSelection()){
//            int steps = RandomUtil.getRandomNumberInRange(0, 7);
//            for (CpMelody clonedMelody : clonedMelodies) {
//                clonedMelody.transposePitchClasses(steps, 0 , timeline);
////                LOGGER.info("repeat transpose melody: " + steps);
//            }
//        }
//        else{
//            int steps = RandomUtil.getRandomNumberInRange(1, 7);
//            for (CpMelody clonedMelody : clonedMelodies) {
//                clonedMelody.inversePitchClasses(steps, 0 , timeline);
////                LOGGER.info("repeat inverse melody: " + steps);
//            }
//        }

//        if(RandomUtil.toggleSelection()){
//            for (CpMelody clonedMelody : clonedMelodies) {
//                TimeLineKey timeLineKeyAtPosition = timeline.getTimeLineKeyAtPosition(clonedMelody.getStart(), clonedMelody.getVoice());
//                int[] pitchClasses = timeLineKeyAtPosition.getScale().getPitchClasses();
//                int steps = RandomUtil.getRandomFromIntArray(pitchClasses);
//                clonedMelody.T(steps);
////                LOGGER.info("repeat inverse melody: " + steps);
//            }
//        }else {
//            for (CpMelody clonedMelody : clonedMelodies) {
//                TimeLineKey timeLineKeyAtPosition = timeline.getTimeLineKeyAtPosition(clonedMelody.getStart(), clonedMelody.getVoice());
//                int[] pitchClasses = timeLineKeyAtPosition.getScale().getPitchClasses();
//                int steps = RandomUtil.getRandomFromIntArray(pitchClasses);
//                clonedMelody.I().T(steps);
////                LOGGER.info("repeat inverse melody: " + steps);
//            }
//        }

        return clonedMelodies;
    }

    /**
     * Executes the operation
     * @param object An object containing a solution to mutate
     * @return An object containing the mutated solution
     * @throws JMException exception
     */
    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityRepetitionMelody");
        if (probability == null) {
            Configuration.logger_.severe("probabilityRepetitionMelody: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}

