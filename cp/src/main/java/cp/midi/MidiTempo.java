package cp.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;

public class MidiTempo {

	private final int tempoEvent[] = new int[] { 0x00, 0xFF, 0x51, 0x03
	// ,0x0F, 0x42, 0x40 // Default 1 million usec per crotchet
	};

    private static final byte TIME_SIGNATURE_MIDI_SUBTYPE = 0x58;
    private static final byte TEMPO_MIDI_SUBTYPE = 0x51;
    private static final int MICROSECONDS_PER_MINUTE = 60000000;
    private static final byte TICKS_PER_METER_CLICK = 24;
    private static final byte THIRTY_SECOND_NOTES_PER_QUARTER = 8;
    private static final int DEFAULT_PPQN = 960;

    public static MetaMessage timeSignatureMessage(byte numerator, byte denominator) {
        MetaMessage timeSigMessage = null;
        try {
            timeSigMessage = new MetaMessage(TIME_SIGNATURE_MIDI_SUBTYPE, new byte[] {
                    numerator,
                    (byte) (Math.log(denominator) / Math.log(2)),
                    TICKS_PER_METER_CLICK,
                    THIRTY_SECOND_NOTES_PER_QUARTER
            }, 4);
        } catch (InvalidMidiDataException ignored) {
            /* Will never happen as the message type is a defined constant */
        }
        return timeSigMessage;
    }

    public static MetaMessage tempoMessage(int bpm) {
        int microSecPerBeat = MICROSECONDS_PER_MINUTE / bpm;
        MetaMessage tempoMessage = null;
        try {
            tempoMessage = new MetaMessage(TEMPO_MIDI_SUBTYPE, new byte[] {
                    (byte) ((microSecPerBeat >>> 16) & 0xFF),
                    (byte) ((microSecPerBeat >>> 8 ) & 0xFF),
                    (byte) ((microSecPerBeat       ) & 0xFF)
            }, 3);
        } catch (InvalidMidiDataException ignored) {
            /* Will never happen as the message type is a defined constant */
        }
        return tempoMessage;
    }

    public static MetaMessage portMessage() {
        MetaMessage portMessage = null;
        try {
            portMessage = new MetaMessage( 0x21, "B".getBytes(), 1);
        } catch (InvalidMidiDataException ignored) {
            /* Will never happen as the message type is a defined constant */
        }
        return portMessage;
    }

	
	public static MidiEvent getTempoMidiEvent(int tempo) throws InvalidMidiDataException{
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