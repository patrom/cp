package cp.midi;

import java.io.OutputStream;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;

public class MidiTempoParser {

	private int DEFAULT_TEMPO = 120;

	public int parseTempo(final MidiMessage message) {
		if (message instanceof MetaMessage) {
			return parseMetaMessage((MetaMessage) message);
		}
		return DEFAULT_TEMPO;
	}

	private int parseMetaMessage(final MetaMessage message) {
		switch (message.getType()) {
		case 0x51:
			return parse(message);
		default:
			break;
		}
		return DEFAULT_TEMPO;
	}

	private int parse(final MetaMessage message) {
		return parseMicrosecondsPerBeat(message);
	}

	private int parseMicrosecondsPerBeat(final MetaMessage message) {
		int tempo = message.getData()[0] * 16384 + message.getData()[1] * 128 + message.getData()[2];
		int beatsPerMinute = (int) convertMicrosecondsPerBeatToBPM(tempo);
		return beatsPerMinute;
	}

	private double convertMicrosecondsPerBeatToBPM(final double value) {
		double microsecondsPerMinute = 60000000.0D;
		if (value == 0.0d) {
			return 0.0d;
		}
		return microsecondsPerMinute / value;
	}
	
}
