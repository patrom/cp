package cp.objective.melody;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.objective.Objective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MelodicObjective extends Objective {

    @Autowired
	private MelodyBlockObjective melodyBlockObjective;

    @Value("${skip.objective.melodic}")
    private List<Integer> skipVoices = new ArrayList<>();
	
	@Override
	public double evaluate(Motive motive) {
        double totalMelodySum = 0;
        List<MelodyBlock> melodyBlocks = motive.getMelodyBlocks();
        int melodyCount = 0;
        for(MelodyBlock melodyBlock: melodyBlocks){
            if (!skipVoices.contains(melodyBlock.getVoice())){
                double melodyValue = melodyBlockObjective.evaluate(melodyBlock);
                totalMelodySum = totalMelodySum + melodyValue;
                melodyCount++;
            }
        }
		return totalMelodySum/melodyCount;
	}

}
