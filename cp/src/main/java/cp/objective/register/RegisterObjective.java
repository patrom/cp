package cp.objective.register;

import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 2/01/2017.
 */
@Component
public class RegisterObjective extends Objective {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterObjective.class);

    @Override
    public double evaluate(Motive motive) {
        List<CpHarmony> harmonies = motive.getHarmonies();
        if (harmonies.size() <= 1) {
            return 0.0;
        }
//        for (CpHarmony harmony : harmonies) {
//            double register = harmony.getRegister(60);
//        }
        return getRegisterValue(harmonies);
//        double totalRegisterValue = 0;
//        for (CpHarmony harmony : harmonies) {
//            totalRegisterValue = totalRegisterValue + getHarmonyRegisterValue(harmony.getNotes());
//        }
//        return totalRegisterValue/harmonies.size();
    }

    protected double getRegisterValue(List<CpHarmony> harmonies) {
        return harmonies.stream().mapToDouble(h -> h.getRegister(60)).average().getAsDouble();
    }

//    protected double getHarmonyRegisterValue(List<Note> harmonyNotes) {
//        double harmonyRegisterValue = 0;
//        int size = harmonyNotes.size() - 1 ;
//        for (int i = 0; i < size; i++) {
//            Note note = harmonyNotes.get(i);
//            Note nextNote = harmonyNotes.get(i + 1);
//            double intervalMelodicValue = getIntervalHarmonicValue(note, nextNote);
//            double averageRegisterValue = (note.getRegisterValue() + nextNote.getRegisterValue())/2;
//            harmonyRegisterValue = harmonyRegisterValue + (intervalMelodicValue * averageRegisterValue);
//        }
//        return harmonyRegisterValue;
//    }
//
//    private double getIntervalHarmonicValue(Note note, Note nextNote) {
//        int difference = 0;
//        if (note.getPitch() != 0 && nextNote.getPitch() != 0) {
//            difference = nextNote.getPitch() - note.getPitch();
//        }
//        return Interval.getEnumInterval(difference).getHarmonicValue();
//    }

}
