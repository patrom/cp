package cp.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;

public class MidiTempo {

	private final int tempoEvent[] = new int[] { 0x00, 0xFF, 0x51, 0x03
	// ,0x0F, 0x42, 0x40 // Default 1 million usec per crotchet
	};
	
	public MidiEvent getTempoMidiEvent(int tempo) throws InvalidMidiDataException{
		MetaMessage mt = new MetaMessage();
		int mMPQN = 60000000 / tempo;
//		0x0F, 0x42, 0x40 = 60
//        byte[] data = {0x0F, 0x42, 0x40};
        byte[] data = intToBytes(mMPQN, 3);
		mt.setMessage(0x51 ,data, 3);
		return new MidiEvent(mt,(long)00);
	}

	public static byte[] intToBytes(int val, int byteCount) {
		byte[] buffer = new byte[byteCount];
		int[] ints = new int[byteCount];
		for (int i = 0; i < byteCount; i++) {
			ints[i] = val & 0xFF;
			buffer[byteCount - i - 1] = (byte) ints[i];
			val = val >> 8;
			if (val == 0) {
				break;
			}
		}
		return buffer;
	}
}