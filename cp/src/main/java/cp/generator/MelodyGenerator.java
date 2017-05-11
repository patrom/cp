package cp.generator;

import cp.composition.Composition;
import cp.composition.accomp.AccompGroup;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Structure;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentConfig;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MelodyGenerator {
	
	private final Random random = new Random();

	@Autowired
	private Composition composition;
	@Autowired
	private VoiceConfig voiceConfiguration;
    @Autowired
	private InstrumentConfig instrumentConfig;
    @Autowired
    private TimeLine timeLine;

	public MelodyBlock generateDependantMelodyBlock(final int voice, int octave, MelodyBlock dependingMelodyBlock){
		int start = composition.getStart();
		int stop = composition.getEnd();
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);

		int end = start;
		while (end < stop) {
			CpMelody clonedMelody = getCpMelody(voice, start, dependingMelodyBlock);
			melodyBlock.addMelodyBlock(clonedMelody);
			start = clonedMelody.getEnd();
			end = start;
		}
		return melodyBlock;
	}

	public MelodyBlock generateEmptyBlock(final Instrument instrument, int voice){
		return new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
	}

	private CpMelody getCpMelody(int voice, int start,  MelodyBlock dependingMelodyBlock) {
		CpMelody randomMelody = RandomUtil.getRandomFromList(dependingMelodyBlock.getMelodyBlocks());
		CpMelody clonedMelody = randomMelody.clone(dependingMelodyBlock.getVoice());

		//TODO pass operator?
		clonedMelody.getNotes().forEach(n -> {
            n.setPosition(n.getPosition() + start - randomMelody.getStart());
        });
		clonedMelody.setStart(start);
		clonedMelody.setEnd(start + randomMelody.getBeatGroupLength());
		return clonedMelody;
	}

    public MelodyBlock generateMelodyBlockConfig(final int voice, List<CpMelody> melodies){
        int start = composition.getStart();
        int stop = composition.getEnd();
        Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
        Voice voiceConfig = voiceConfiguration.getVoiceConfiguration(voice);
        return generateMelodyBlockConfig(voice, voiceConfig, instrument.pickRandomOctaveFromRange(), start, stop, melodies);
    }

    public MelodyBlock generateMelodyBlockConfig(int voice, Voice voiceConfig, int octave, int start, int stop, List<CpMelody> melodies) {
        MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
        melodyBlock.setOffset(voiceConfig.getTimeConfig().getOffset());

        int size = melodies.size();
        int i = 0;
        BeatGroup beatGroup = melodies.get(i).getBeatGroup();
        int end = start + beatGroup.getBeatLength();
        while (end <= stop) {
            CpMelody melody = melodies.get(i % size).clone(voice);
            melody.setStart(start);
            melody.setEnd(end);
            melody.updateNotes(voice, voiceConfig, start);
			if (melody.getStructure() == Structure.TONAL && melody.getKey() != null) {
				melody.convertToKey(melody.getKey(), timeLine);
			}
			melodyBlock.addMelodyBlock(melody);
            i++;
            beatGroup = melodies.get(i % size).getBeatGroup();
            start = end;
            end = start + beatGroup.getBeatLength();
        }
        return melodyBlock;
    }

    public MelodyBlock generateMelodyBlockConfig(final int voice){
        int start = composition.getStart();
        int stop = composition.getEnd();
        Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
        return generateMelodyBlockConfig(voice, instrument.pickRandomOctaveFromRange(), start, stop);
    }

	public MelodyBlock generateMelodyBlockConfig(final int voice, int octave){
		int start = composition.getStart();
		int stop = composition.getEnd();
		return generateMelodyBlockConfig(voice, octave, start, stop);
	}

	public MelodyBlock generateMelodyBlockConfig(int voice, int octave, int start, int stop) {
		Voice voiceConfig = voiceConfiguration.getVoiceConfiguration(voice);
		return generateMelodyBlockConfig(voice, voiceConfig, octave, start, stop);
	}

	public MelodyBlock generateMelodyBlockConfig(int voice, Voice voiceConfig, int octave, int start, int stop) {
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
		melodyBlock.setOffset(voiceConfig.getTimeConfig().getOffset());

		int i = 0;
		BeatGroup beatGroup = voiceConfig.getTimeConfig().getBeatGroup(i);
		int end = start + beatGroup.getBeatLength();
		while (end <= stop) {
			CpMelody melody = generateMelodyConfig(voice, start, beatGroup, voiceConfig);
			melodyBlock.addMelodyBlock(melody);
			i++;
			beatGroup = voiceConfig.getTimeConfig().getBeatGroup(i);
			start = end;
			end = start + beatGroup.getBeatLength();
		}
		return melodyBlock;
	}

	public CpMelody generateMelodyConfig(int voice, int start, BeatGroup beatGroup, Voice voiceConfig) {
		List<Note> melodyNotes = voiceConfig.getNotes(beatGroup);
		melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setDynamic(voiceConfig.getDynamic());
			n.setDynamicLevel(voiceConfig.getDynamic().getLevel());
			n.setTechnical(voiceConfig.getTechnical());
			n.setPosition(n.getPosition() + start);
		});
		melodyNotes = voiceConfiguration.getRandomPitchClassGenerator(voice).updatePitchClasses(melodyNotes);
		CpMelody melody = new CpMelody(melodyNotes, voice, start, start + beatGroup.getBeatLength());
		melody.setBeatGroup(beatGroup);
		return melody;
	}

	public MelodyBlock generateMelodyBlockWithoutPitchClassGenerator(int voice, AccompGroup accompGroup, int octave) {
        int start = composition.getStart();
        int stop = composition.getEnd();
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);

		int i = 0;
		BeatGroup beatGroup = accompGroup.getVoice().getTimeConfig().getBeatGroup(i);
		int end = start + beatGroup.getBeatLength();
		while (end <= stop) {
			List<Note> melodyNotes = accompGroup.getVoice().getNotes(beatGroup);
			CpMelody melody = generateMelodyConfigWithoutPitchClassGenerator(voice, start, beatGroup, melodyNotes);
			melody.setContour(accompGroup.getContour());
			melodyBlock.addMelodyBlock(melody);
			i++;
			beatGroup = accompGroup.getVoice().getTimeConfig().getBeatGroup(i);
			start = end;
			end = start + beatGroup.getBeatLength();
		}
		return melodyBlock;
	}

	public CpMelody generateMelodyConfigWithoutPitchClassGenerator(int voice, int start, BeatGroup beatGroup, List<Note> melodyNotes) {
		melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setPosition(n.getPosition() + start);
		});
		CpMelody melody = new CpMelody(melodyNotes, voice, start, start + beatGroup.getBeatLength());
		melody.setBeatGroup(beatGroup);
		return melody;
	}
	
	public int[] generateMelodyPositions(int[] harmony, int minimumLength, int maxMelodyNotes){
		int[] pos;
		int limit = generateLimit(harmony[0], harmony[1], minimumLength);
		int from = ((harmony[0])/minimumLength) + 1;
		int toExlusive = (int)Math.ceil(harmony[1]/(double)minimumLength);
		IntStream intStream = random.ints(limit,from,toExlusive);
		List<Integer> positions = intStream
				.distinct()
				.map(i -> i * minimumLength)
				.boxed()
				.collect(Collectors.toList());
		int max = (maxMelodyNotes > positions.size())?positions.size():maxMelodyNotes;
		positions = positions.subList(0, max);
		positions.sort(Comparator.naturalOrder());
		pos = new int[positions.size() + 2];
		pos[0] = harmony[0];
		pos[pos.length - 1] = harmony[1];
		for (int j = 1; j < pos.length - 1; j++) {
			pos[j] = positions.get(j - 1);
		}
		return pos;
	}

	protected int generateLimit(int begin, int end, int minimumLength) {
		int positionsInHarmony = ((end - begin)/minimumLength) - 1;//minus first position
		int limit = random.nextInt(positionsInHarmony + 1);
		while (limit < 2) {
			limit = random.nextInt(positionsInHarmony + 1);
		}
		return limit;
	}

}
