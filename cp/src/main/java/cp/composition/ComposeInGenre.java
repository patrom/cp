package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.stereotype.Component;

import java.util.List;

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
