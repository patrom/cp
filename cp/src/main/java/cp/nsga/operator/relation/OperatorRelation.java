package cp.nsga.operator.relation;

import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.nsga.MusicVariable;
import jmetal.core.Solution;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 24/10/2016.
 */
public class OperatorRelation {

    private int source;
    private int target;
    private int steps;
    private int offset;
    private final Operator operator;
    private TimeLine timeLine;
    private int degree;
    private double factor;

    public OperatorRelation(Operator operator) {
        this.operator = operator;
    }

    public Solution execute(Solution solution){
        Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
        MelodyBlock melodyBlockSource = motive.getRandomMelodyForVoice(source);
        MelodyBlock melodyBlockTarget = motive.getRandomMelodyForVoice(target);
        if (melodyBlockSource != null && melodyBlockTarget != null) {
            int end = melodyBlockSource.getLastMelody().getEnd();
            MelodyBlock melodyBlock = melodyBlockSource.clone(end - offset, target);
            melodyBlock.setInstrument(melodyBlockTarget.getInstrument());

            switch (operator) {
                case T:
                    melodyBlock.T(steps);
                    break;
                case I:
                    melodyBlock.I();
                    break;
                case R:
                    melodyBlock.R();
                    break;
                case M:
                    melodyBlock.M(steps);
                    break;
                case T_RELATIVE:
                    melodyBlock.Trelative(steps, timeLine);
                    break;
                case I_RELATIVE:
                    melodyBlock.Irelative(degree, timeLine);
                    break;
                case AUGMENTATION:
                    melodyBlock.augmentation(steps, timeLine);
                    melodyBlock.getMelodyBlocks().stream()
                            .filter(m -> m.getStart() < end - offset)
                            .map(m -> m.clone(end - offset, target))
                            .collect(toList());
                    break;
                case DIMINUTION:
                    melodyBlock.diminution(steps, timeLine);
                case RHYTHMIC:
                default:
                    break;
            }

//            melodyBlock.getMelodyBlocks().stream()
//                    .forEach(m -> m.setVoice(target));
//            melodyBlock.getMelodyBlocks().stream()
//                    .flatMap(m -> m.getNotes().stream())
//                    .forEach(note -> {
//                        note.setVoice(target);
//                        note.setPosition(note.getPosition() + offset);
//                    });

            melodyBlock.getMelodyBlocks().stream()
                    .flatMap(m -> m.getNotes().stream())
                    .forEach(note -> note.setPosition(note.getPosition() + offset));
            melodyBlock.updatePitchesFromContour();
            melodyBlock.updateMelodyBetween();
            melodyBlockTarget.setMelodyBlocks(melodyBlock.getMelodyBlocks());
        }
        return solution;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getSteps() {
        return steps;
    }

    public double getFactor() {
        return factor;
    }

    public Operator getOperator() {
        return operator;
    }

    public int getFunctionalDegreeCenter() {
        return degree;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public void setFunctionalDegreeCenter(int degree) {
        this.degree = degree;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
