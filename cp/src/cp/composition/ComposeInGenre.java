package cp.composition;

import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.melody.MelodyBlock;
import cp.out.instrument.Instrument;

@Component
public class ComposeInGenre {
	
	private CompositionGenre compositionGenre;
	
	public List<MelodyBlock> composeInGenre(){
		return compositionGenre.composeInGenre();
	}
	
	public void setCompositionGenre(CompositionGenre compositionGenre) {
		this.compositionGenre = compositionGenre;
	}
}
