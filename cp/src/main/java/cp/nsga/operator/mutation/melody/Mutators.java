package cp.nsga.operator.mutation.melody;

import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
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

    @Value("${probabilityAllNote}")
    private double probabilityAllNote;
    @Value("${probabilityOneNote}")
    private double probabilityOneNote;
    @Value("${probabilityOneNoteChromatic}")
    private double probabilityOneNoteChromatic;
    @Value("${probabilityRhythm}")
    private double probabilityRhythm;
    @Value("${probabilityReplaceMelody}")
    private double probabilityReplaceMelody;
    @Value("${probabilityDynamic}")
    private double probabilityDynamic;
    @Value("${probabilityTechnical}")
    private double probabilityTechnical;
    @Value("${probabilityArticulation}")
    private double probabilityArticulation;
    @Value("${probabilityTexture}")
    private double probabilityTexture;
    @Value("${probabilityOperatorMutation}")
    private double probabilityOperatorMutation;
    @Value("${probabilityProvided}")
    private double probabilityProvided;
    @Value("${probabilitySymmetryProvided}")
    private double probabilitySymmetryProvided;
    @Value("${probabilityOneNoteHarmony}")
    private double probabilityOneNoteHarmony;
    @Value("${probabilityAllNoteHarmony}")
    private double probabilityAllNoteHarmony;

    @Autowired
    public RhythmMutation rhythmMutation;
    @Autowired
    public OneNoteMutation oneNoteMutation;
    @Autowired
    public OneNoteChromaticMutation oneNoteChromaticMutation;
    @Autowired
    public AllNoteMutation allNoteMutation;
    @Autowired
    public ReplaceMelody replaceMelody;
    @Autowired
    public TextureMutation textureMutation;

    @Autowired
    public ArticulationMutation articulationMutation;
    @Autowired
    public DynamicMutation dynamicMutation;
    @Autowired
    public TechnicalMutation technicalMutation;

    @Autowired
    public OperatorMutation operatorMutation;
    @Autowired
    public ProvidedMutation providedMutation;
    @Autowired
    public AllNoteHarmonyMutation allNoteHarmonyMutation;
    @Autowired
    public OneNoteHarmonyMutation oneNoteHarmonyMutation;
    @Autowired
    private ProvidedSymmetryMutation providedSymmetryMutation;

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
            case HARMONY:
                return harmonyMutationOperators();
        }
        throw new IllegalArgumentException("Mutation type unknown: " + mutationType);
    }

    public List<MutationOperator> mutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityRhythm > 0.0) {
            mutationOperators.add(rhythmMutation);
        }
//        mutationOperators.add(operatorMutation);
        if (probabilityReplaceMelody > 0.0) {
            mutationOperators.add(replaceMelody);
        }
        if (probabilityOneNote > 0.0) {
            mutationOperators.add(oneNoteMutation);
        }
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        if (probabilityAllNote > 0.0) {
            mutationOperators.add(allNoteMutation);
        }
        if (probabilityArticulation > 0.0) {
            mutationOperators.add(articulationMutation);
        }
        if (probabilityDynamic > 0.0) {
            mutationOperators.add(dynamicMutation);
        }
        if (probabilityTechnical> 0.0) {
            mutationOperators.add(technicalMutation);
        }
        if (probabilityTexture > 0.0) {
            mutationOperators.add(textureMutation);
        }

//            mutationOperators.addAll(harmonyMutationOperators());

//        mutationOperators.add(providedMutation); //-> check if provided melody sizes are part of combinations!!
        return mutationOperators;
    }

    private List<MutationOperator> pitchMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityOneNote > 0.0) {
            mutationOperators.add(oneNoteMutation);
        }
//        mutationOperators.add(oneNoteChromaticMutation);
        if (probabilityAllNote > 0.0) {
            mutationOperators.add(allNoteMutation);
        }
        if (probabilityReplaceMelody > 0.0) {
            mutationOperators.add(replaceMelody);
        }
        return mutationOperators;
    }

    private List<MutationOperator> rhytmMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityRhythm > 0.0) {
            mutationOperators.add(rhythmMutation);
        }
        if (probabilityProvided > 0.0) {
            mutationOperators.add(providedMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> timbreMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityArticulation > 0.0) {
            mutationOperators.add(articulationMutation);
        }
        if (probabilityDynamic > 0.0) {
            mutationOperators.add(dynamicMutation);
        }
        if (probabilityTechnical> 0.0) {
            mutationOperators.add(technicalMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> textureMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityTexture > 0.0) {
            mutationOperators.add(textureMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> providedMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.addAll(timbreMutationOperators());
        if (probabilityTexture > 0.0) {
            mutationOperators.add(textureMutation);
        }
        if (probabilityProvided > 0.0) {
            mutationOperators.add(providedMutation);
        }
        return mutationOperators;
    }

    public List<MutationOperator> harmonyMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        if (probabilityAllNoteHarmony > 0.0) {
            mutationOperators.add(allNoteHarmonyMutation);
        }
//        if (probabilityOneNoteHarmony > 0.0) {
//            mutationOperators.add(oneNoteHarmonyMutation);
//        }
        return mutationOperators;
    }

    private List<MutationOperator> providedSymmetryOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.addAll(timbreMutationOperators());
        if (probabilityTexture > 0.0) {
            mutationOperators.add(textureMutation);
        }
        if (probabilitySymmetryProvided > 0.0) {
            mutationOperators.add(providedSymmetryMutation);
        }
        return mutationOperators;
    }

    private List<MutationOperator> providedRhythmOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.addAll(timbreMutationOperators());
        if (probabilityTexture > 0.0) {
            mutationOperators.add(textureMutation);
        }
        if (probabilityProvided > 0.0) {
            mutationOperators.add(providedMutation);
        }

        if (probabilityOneNote > 0.0) {
            mutationOperators.add(oneNoteMutation);
        }
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        if (probabilityAllNote > 0.0) {
            mutationOperators.add(allNoteMutation);
        }
//        mutationOperators.add(replaceMelody);
        return mutationOperators;
    }
}
