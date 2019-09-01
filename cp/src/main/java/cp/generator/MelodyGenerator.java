package cp.generator;

import cp.composition.Composition;
import cp.composition.accomp.AccompGroup;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.composition.voice.Voice;
import cp.config.*;
import cp.config.map.CompositionMap;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.provider.MelodyProvider;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

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
	@Autowired
    private TextureConfig textureConfig;
	@Autowired
	private MelodyProviderConfig melodyProviderConfig;
	@Autowired
	private TimbreConfig timbreConfig;
    @Autowired
	private CompostionMapConfig compostionMapConfig;

	public MelodyBlock pickMelodies(int voice){
        int start = composition.getStart();
        int stop = composition.getEnd();
        Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
        MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
        int end = start;
        CompositionMap compositionMap = compostionMapConfig.getCompositionMapForVoice(voice);
        while (end < stop) {
            CpMelody randomMelody = compositionMap.getRandomMelody(voice);
            randomMelody.setStart(start);
            randomMelody.setEnd(start + randomMelody.getLength());
            randomMelody.updateNotePositions(start);
            randomMelody.setMutationType(MutationType.MELODY_MAP);
            randomMelody.setVoice(voice);
            melodyBlock.addMelodyBlock(randomMelody);
            start = randomMelody.getEnd();
            end = start;
        }
        return melodyBlock;
    }

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

	public MelodyBlock generateProvidedMelodyBlockConfigRandom(int voice, Voice voiceConfig, int octave, int start, int stop) {
		Timbre timbre = timbreConfig.getTimbreConfigForVoice(voice);
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
		melodyBlock.setOffset(voiceConfig.getTimeConfig().getOffset());
		MelodyProvider melodyProviderForVoice = melodyProviderConfig.getMelodyProviderForVoice(voice);
		final List<CpMelody> melodies = melodyProviderForVoice.getMelodies(voice);
		int size = melodies.size();
		int i = 0;
		CpMelody melody = RandomUtil.getRandomFromList(melodies);
		BeatGroup beatGroup = melody.getBeatGroup();
		int end = start + beatGroup.getBeatLength();
		while (end <= stop) {
			CpMelody cloneMelody = melody.clone(voice);
			cloneMelody.setStart(start);
			cloneMelody.setEnd(end);
			cloneMelody.updateNotes(timbre, start);
//			if (cloneMelody.getTonality() == Tonality.TONAL && cloneMelody.getTimeLineKey() != null) {
//				cloneMelody.convertToTimelineKey(timeLine);
//			}
			if (textureConfig.hasTexture(voice)) {
				for (Note melodyNote : cloneMelody.getNotesNoRest()) {
					if (!melodyNote.isRest()) {
                        DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, melodyNote.getPitchClass());
						melodyNote.setDependantHarmony(dependantHarmony);
					}
				}
			}

			melodyBlock.addMelodyBlock(cloneMelody);
			i++;
			melody = RandomUtil.getRandomFromList(melodies);
			beatGroup = melody.getBeatGroup();
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
//		if( voiceConfig.isMelodiesProvided()){
//			return generateProvidedMelodyBlockConfigRandom(voice, voiceConfig, octave, start, stop);
//		} else {
			return generateMelodyBlockConfig(voice, voiceConfig, octave, start, stop);
//		}
	}

	public MelodyBlock generateMelodyBlockConfig(int voice, Voice voiceConfig, int octave, int start, int stop) {
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
        if (voiceConfig.isMelodiesProvided()) {
            melodyBlock.setMutable(false);
        }
		melodyBlock.setOffset(voiceConfig.getTimeConfig().getOffset());
		Timbre timbreConfigForVoice = timbreConfig.getTimbreConfigForVoice(voice);

		int end = 0;
		switch (voiceConfig.getNumerator()){
            case 2:
            case 3:
            case 4:
            case 6:
            case 9:
            case 12:
                int i = 0;
                BeatGroup beatGroup = voiceConfig.getRandomBeatgroup();
                end = start + beatGroup.getBeatLength();
                while (end <= stop) {
                    CpMelody melody = generateMelodyConfig(voice, start, beatGroup, voiceConfig, timbreConfigForVoice);
                    melodyBlock.addMelodyBlock(melody);
                    i++;
                    beatGroup = voiceConfig.getRandomBeatgroup();
                    start = end;
                    end = start + beatGroup.getBeatLength();
                }
                break;
			case 5:
			case 7:
                List<BeatGroup> beatGroups = voiceConfig.getBeatGroups();
                int beatGroupLength = beatGroups.stream().mapToInt(BeatGroup::getBeatLength).sum();
                end = start + beatGroupLength;
                while (end <= stop) {
                    for (BeatGroup group : beatGroups) {
                        CpMelody melody = generateMelodyConfig(voice, start, group, voiceConfig, timbreConfigForVoice);
                        melodyBlock.addMelodyBlock(melody);
                        start = start + group.getBeatLength();
                    }
                    start = end;
                    beatGroupLength = beatGroups.stream().mapToInt(BeatGroup::getBeatLength).sum();
                    end = start + beatGroupLength;
                }
				break;
		}
		return melodyBlock;
	}

	public CpMelody generateMelodyConfig(int voice, int start, BeatGroup beatGroup, Voice voiceConfig, Timbre timbre) {
		NoteSizeValueObject valueObject = beatGroup.getRandomRhythmNotesForBeatgroupType();
		List<Note> melodyNotes = valueObject.getNotes();



        CpMelody melody = new CpMelody(melodyNotes, voice, start, start + beatGroup.getBeatLength());
        melody.setBeatGroup(beatGroup);
        melody.setNotesSize(valueObject.getKey());

//        if (!voiceConfig.isMelodiesProvided()) {
            List<TimeLineKey> timelineKeys = timeLine.getTimelineKeys(voice, start, start + beatGroup.getBeatLength());
            melody.setTimeLineKeys(timelineKeys);
//        }

        if (voiceConfig.isMelodiesProvided()) {
            melody.setMutable(false);
        } else {
            melody.setMutationType(RandomUtil.getRandomFromList(voiceConfig.getMutationTypes()));
        }

        melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setDynamic(timbre.getDynamic());
			n.setDynamicLevel(timbre.getDynamic().getLevel());
			n.setTechnical(timbre.getTechnical());
			n.setPosition(n.getPosition() + start);
		});
        if (beatGroup.hasPitchClassGenerators()) {
            PitchClassGenerator pcGenerator = RandomUtil.getRandomFromList(beatGroup.getPitchClassGenerators());
            melodyNotes = pcGenerator.updatePitchClasses(melody);
        } else {
            melodyNotes = voiceConfiguration.getRandomPitchClassGenerator(voice).updatePitchClasses(melody);
        }
        List<Note> melodyNotesNoRests = melodyNotes.stream().filter(n -> !n.isRest()).collect(toList());
        if (beatGroup.hasChordTypes()) {
            List<ChordType> chordTypes = beatGroup.getChordTypes();
            for (int i = 0; i < melodyNotesNoRests.size(); i++) {
                Note note = melodyNotesNoRests.get(i);
                DependantHarmony dependantHarmony = new DependantHarmony();
                dependantHarmony.setChordType(chordTypes.get(i % chordTypes.size()));
                note.setDependantHarmony(dependantHarmony);
            }
        } else if (textureConfig.hasTexture(voice)) {
            for (Note melodyNote : melodyNotesNoRests) {
				if (!melodyNote.isRest()) {
                    DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, melodyNote.getPitchClass());
					melodyNote.setDependantHarmony(dependantHarmony);
				}
			}
        }

        return melody;
	}

	public MelodyBlock generateMelodyBlockWithoutPitchClassGenerator(int voice, AccompGroup accompGroup, int octave) {
        int start = composition.getStart();
        int stop = composition.getEnd();
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);

		int i = 0;
		BeatGroup beatGroup = accompGroup.getVoice().getRandomBeatgroup();
		int end = start + beatGroup.getBeatLength();
		while (end <= stop) {
			NoteSizeValueObject valueObject = beatGroup.getRandomRhythmNotesForBeatgroupType();
			List<Note> melodyNotes = valueObject.getNotes();
			CpMelody melody = generateMelodyConfigWithoutPitchClassGenerator(voice, start, beatGroup, melodyNotes, accompGroup.getVoice().getMutationTypes());
			melody.setNotesSize(valueObject.getKey());
			melody.setContour(accompGroup.getContour());
			melodyBlock.addMelodyBlock(melody);
			i++;
			beatGroup = accompGroup.getVoice().getRandomBeatgroup();
			start = end;
			end = start + beatGroup.getBeatLength();
		}
		return melodyBlock;
	}

	public CpMelody generateMelodyConfigWithoutPitchClassGenerator(int voice, int start, BeatGroup beatGroup, List<Note> melodyNotes, List<MutationType> mutationTypes) {
		melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setPosition(n.getPosition() + start);
		});
		CpMelody melody = new CpMelody(melodyNotes, voice, start, start + beatGroup.getBeatLength());
		melody.setBeatGroup(beatGroup);
		melody.setMutationType(RandomUtil.getRandomFromList(mutationTypes));
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
