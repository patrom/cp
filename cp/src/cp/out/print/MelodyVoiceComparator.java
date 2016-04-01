package cp.out.print;

import java.util.Comparator;

import cp.model.melody.MelodyBlock;

public class MelodyVoiceComparator implements Comparator<MelodyBlock> {

	@Override
	public int compare(MelodyBlock mel1, MelodyBlock mel2) {
		if (mel1.getVoice() > mel2.getVoice()) {
			return -1;
		} else if (mel1.getVoice() < mel2.getVoice()) {
			return 1;
		}
		return 0;
	}

}
