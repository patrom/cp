package cp.nsga.operator.relation;

import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.nsga.MusicVariable;
import cp.out.instrument.Instrument;
import jmetal.core.Solution;

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
    private Instrument instrument;

    public OperatorRelation(Operator operator) {
        this.operator = operator;
    }

    public Solution execute(Solution solution){
        Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
        MelodyBlock melodyBlockSource = motive.getRandomMelodyForVoice(source);
        MelodyBlock melodyBlockTarget = motive.getRandomMelodyForVoice(target);
        if (melodyBlockSource != null && melodyBlockTarget != null) {
            int end = melodyBlockSource.getLastMelody().getEnd();
            MelodyBlock melodyBlock = null;
            switch (operator) {
                case T:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.T(steps);
                    break;
                case I:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.I();
                    break;
                case R:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.R();
                    break;
                case M:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.M(steps);
                    break;
                case T_RELATIVE:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.Trelative(steps, timeLine);
                    break;
                case I_RELATIVE:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.Irelative(degree, timeLine);
                    break;
                case AUGMENTATION:
                    melodyBlock = melodyBlockSource.clone((int) ((end - offset)/factor ), target);
                    melodyBlock.augmentation(factor, timeLine);
                    break;
                case AUGMENTATION_RETROGRADE:
                    melodyBlock = melodyBlockSource.clone((int) ((end - offset)/factor ), target);
                    melodyBlock.augmentation(factor, timeLine).retrograde();
                    break;
                case DIMINUTION:
                    melodyBlock = melodyBlockSource.clone(end - offset, target);
                    melodyBlock.diminution(factor, timeLine);
                case RHYTHMIC:
                default:
                    break;
            }

            melodyBlock.getMelodyBlocks().stream()
                    .flatMap(m -> m.getNotes().stream())
                    .forEach(note -> note.setPosition(note.getPosition() + offset));
//            melodyBlock.updatePitchesFromContour();//TODO check if can be removed??
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

    public void setInstrument(Instrument instrument){
        this.instrument = instrument;
    }
}
