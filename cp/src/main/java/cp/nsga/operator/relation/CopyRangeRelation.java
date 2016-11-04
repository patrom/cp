package cp.nsga.operator.relation;

import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.util.RandomUtil;
import jmetal.core.Solution;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 27/10/2016.
 */
public class CopyRangeRelation {

    private int source;
    private int target;
    private int endComposition;
    private TimeLine timeLine;
    private int rangeLength;
    private int minimumLength;

    public Solution execute(Solution solution){
        Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
        MelodyBlock melodyBlockSource = motive.getRandomMelodyForVoice(source);
        Optional<MelodyBlock> melodyBlockTarget = motive.getMelodyBlocks().stream().filter(b -> b.getVoice() == target).findFirst();
        if (melodyBlockSource != null && melodyBlockTarget.isPresent()) {
//            int startRange = 0;
            int startRange = getRandomStartPosition();
            int endRange = startRange + rangeLength;

            //shift position
            int startCopyMelody = getRandomStartPosition();
            List<Note> notesInRangeSelection = melodyBlockSource.getMelodyBlockNotes().stream()
                                                                .filter(n -> startRange <= n.getPosition() && n.getPosition() < endRange)
                                                                .map(n -> n.clone())
                                                                .collect(toList());
            CpMelody melody = new CpMelody(notesInRangeSelection, target, startRange, endRange);

            int endCopyMelody = startCopyMelody + rangeLength;
            int offset = startCopyMelody - startRange;
            //Tonal operators
            if(RandomUtil.toggleSelection()){
                int steps = RandomUtil.getRandomNumberInRange(0, 7);
                melody.transposePitchClasses(steps, offset , timeLine);
            }else{
                int degree = RandomUtil.getRandomNumberInRange(1, 7);
                melody.inversePitchClasses(degree, offset , timeLine);
            }

            List<Note> melodyNotes = melody.getNotes().stream()
                    .map(n -> {
                        n.setPosition(n.getPosition() + offset);
                        n.setVoice(target);
                         return n;})
                    .collect(toList());
            MelodyBlock melodyBlock = melodyBlockTarget.get();
            List<Note> targetNotes = melodyBlock.getMelodyBlockNotes().stream().filter(n -> (0 >= n.getPosition() && n.getPosition() < startCopyMelody) || n.getPosition() >= endCopyMelody).collect(toList());
            targetNotes.addAll(melodyNotes);
            targetNotes.sort(comparing(Note::getPosition));
            CpMelody targetMelody = new CpMelody(targetNotes, target, 0, endComposition);

            melodyBlock.setMelodyBlocks(Collections.singletonList(targetMelody));
        }

        return solution;
    }

    private int getRandomStartPosition() {
        int startLength = endComposition - rangeLength;
        int maxPositionIndex = startLength / minimumLength;
        return RandomUtil.random(maxPositionIndex) * minimumLength;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setEndComposition(int endComposition) {
        this.endComposition = endComposition;
    }

    public void setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public void setRangeLength(int rangeLength) {
        this.rangeLength = rangeLength;
    }

    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }
}

