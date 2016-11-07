package cp.out.print;

import cp.model.melody.MelodyBlock;

import java.util.Comparator;

public class MelodyVoiceComparator implements Comparator<MelodyBlock> {

	@Override
	public int compare(MelodyBlock mel1, MelodyBlock mel2) {
		if (mel1.getVoice() > mel2.getVoice()) {
			return 1;
		} else if (mel1.getVoice() < mel2.getVoice()) {
			return -1;
		}
		return 0;
	}

}
