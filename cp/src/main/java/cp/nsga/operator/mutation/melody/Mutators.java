package cp.nsga.operator.mutation.melody;

import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import cp.nsga.operator.mutation.contour.ContourMutation;
import cp.nsga.operator.mutation.manipulation.MelodyInsertNoteMutation;
import cp.nsga.operator.mutation.manipulation.MelodyNoteLengthMutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 19/05/2017.
 */
@Configuration
public class Mutators {

    @Value("${probabilityRhythm}")
    private double probabilityRhythm;
    @Autowired
    public RhythmMutation rhythmMutation;

    @Value("${probabilityOneNote}")
    private double probabilityOneNote;
    @Autowired
    public OneNoteMutation oneNoteMutation;

    @Value("${probabilityOneNoteChromatic}")
    private double probabilityOneNoteChromatic;
    @Autowired
    public OneNoteChromaticMutation oneNoteChromaticMutation;

    @Value("${probabilityAllNote}")
    private double probabilityAllNote;
    @Autowired
    public AllNoteMutation allNoteMutation;

    @Value("${probabilityNoteSize}")
    private double probabilityNoteSize;
    @Autowired
    public NoteSizeMutation noteSizeMutation;

    @Value("${probabilityTexture}")
    private double probabilityTexture;
    @Autowired
    public TextureMutation textureMutation;

    @Value("${probabilityArticulation}")
    private double probabilityArticulation;
    @Autowired
    public ArticulationMutation articulationMutation;

    @Value("${probabilityDynamic}")
    private double probabilityDynamic;
    @Autowired
    public DynamicMutation dynamicMutation;

    @Value("${probabilityTechnical}")
    private double probabilityTechnical;
    @Autowired
    public TechnicalMutation technicalMutation;

    @Value("${probabilityOperatorMutation}")
    private double probabilityOperatorMutation;
    @Autowired
    public OperatorMutation operatorMutation;

    @Autowired
    public ProvidedSymmetryMutation providedSymmetryMutation;
    @Autowired
    public TwelveToneRhythmMutation twelveToneRhythmMutation;
    @Autowired
    public TwelveToneRhythmMutationSplit twelveToneRhythmMutationSplit;
    @Value("${probabilityContour}")
    private double probabilityContour;
    @Autowired
    public ContourMutation contourMutation;

    @Value("${probabilityMelodyMap}")
    private double probabilityMelodyMap;
    @Autowired
    public MelodyMapRandomMutation melodyMapRandomMutation;
    @Value("${probabilityMelodyManipulation}")
    private double probabilityMelodyManipulation;
    @Autowired
    private MelodyNoteLengthMutation melodyNoteLengthMutation;
    @Autowired
    private MelodyInsertNoteMutation melodyInsertNoteMutation;
    @Value("${probabilityOneNoteScale}")
    private double probabilityOneNoteScale;
    @Autowired
    private OneNoteScaleMutation oneNoteScaleMutation;

    public List<MutationOperator> getMutationOperators(MutationType mutationType){
        switch (mutationType){
            case PITCH:
                return pitchMutationOperators();
            case RHYTHM:
                return rhytmMutationOperators();
            case ALL:
                return mutationOperators();
            case TIMBRE:
                return timbreMutationOperators();
            case TEXTURE:
                return textureMutationOperators();
            case SYMMETRY:
                return providedSymmetryOperators();
            case OPERATOR:
                return providedMutationOperators();
            case TWELVE_TONE:
//                return twelveToneMutationOperators();
                return twelveToneMutationOperators();
            case MELODY_MAP:
                return melodyMapOperators();
        }
        throw new IllegalArgumentException("Mutation type unknown: " + mutationType);
    }

    private List<MutationOperator> twelveToneMutationOperators(){
        List<MutationOperator> mutationOperators = new ArrayList<>();
//        mutationOperators.add(twelveToneRhythmMutation);
        mutationOperators.add(twelveToneRhythmMutationSplit);
        return mutationOperators;
    }

    private List<MutationOperator> melodyMapOperators(){
        List<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityMelodyMap > 0) {
            mutationOperators.add(melodyMapRandomMutation);
        }
        if (probabilityMelodyManipulation > 0) {
            mutationOperators.add(melodyNoteLengthMutation);
            mutationOperators.add(melodyInsertNoteMutation);
        }
        if (probabilityOneNoteScale > 0) {
            mutationOperators.add(oneNoteScaleMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> mutationOperators(){
        List<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityRhythm > 0) {
            mutationOperators.add(rhythmMutation);
        }
        if (probabilityNoteSize > 0) {
            mutationOperators.add(noteSizeMutation);
        }
        if (probabilityOneNote > 0) {
            mutationOperators.add(oneNoteMutation);
        }
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        if (probabilityAllNote > 0) {
            mutationOperators.add(allNoteMutation);
        }
        if (probabilityArticulation > 0) {
            mutationOperators.add(articulationMutation);
        }
        if (probabilityDynamic > 0) {
            mutationOperators.add(dynamicMutation);
        }
        if (probabilityTechnical > 0) {
            mutationOperators.add(technicalMutation);
        }
        if (probabilityTexture > 0) {
            mutationOperators.add(textureMutation);
        }
        if (probabilityOperatorMutation > 0) {
            mutationOperators.add(operatorMutation);
        }
        if (probabilityContour > 0) {
            mutationOperators.add(contourMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> pitchMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);
        mutationOperators.add(allNoteMutation);
        mutationOperators.add(noteSizeMutation);
        mutationOperators.add(operatorMutation);
        return mutationOperators;
    }

    private List<MutationOperator> rhytmMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(rhythmMutation);
        return mutationOperators;
    }

    public List<MutationOperator> timbreMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);

        return mutationOperators;
    }

    private List<MutationOperator> textureMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(textureMutation);
        return mutationOperators;
    }

    private List<MutationOperator> providedMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        mutationOperators.add(textureMutation);
        return mutationOperators;
    }

    private List<MutationOperator> providedSymmetryOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        mutationOperators.add(textureMutation);
        mutationOperators.add(providedSymmetryMutation);
        return mutationOperators;
    }

    private List<MutationOperator> providedRhythmOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        mutationOperators.add(textureMutation);

        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        mutationOperators.add(allNoteMutation);
//        mutationOperators.add(noteSizeMutation);
        return mutationOperators;
    }
}
