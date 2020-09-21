package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ComposeInGenre {
	
	private CompositionGenre compositionGenre;

    @Value("#{${group.objective.melodicharmonic}}")
    private Map<Integer,List<Integer>> groupingMelodicHarmonic = new HashMap<>();
	
	public List<MelodyBlock> composeInGenre(){
        List<MelodyBlock> melodyBlocks = compositionGenre.composeInGenre();
        for (Map.Entry<Integer, List<Integer>> entry : groupingMelodicHarmonic.entrySet()) {
            List<Integer> voices = entry.getValue();
            melodyBlocks.stream().filter(melodyBlock -> voices.contains(melodyBlock.getVoice()))
                    .forEach(melodyBlock -> melodyBlock.dependsOn(entry.getKey()));
        }
		return melodyBlocks;
	}
	
	public void setCompositionGenre(CompositionGenre compositionGenre) {
		this.compositionGenre = compositionGenre;
	}
}
