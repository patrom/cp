package cp.out.orchestration.notetemplate;

import org.springframework.stereotype.Component;

@Component
public class TwoNoteTemplate {

	public int[] getNoteTemplate(){
		return new int[] {0,1, 1};
	}
}
