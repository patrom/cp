package cp.nsga.operator.mutation.melody;

import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 19/05/2017.
 */
@Configuration
public class Mutators {

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
    public ProvidedSymmetryMutation providedSymmetryMutation;
    @Autowired
    public TwelveToneRhythmMutation twelveToneRhythmMutation;

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
                return twelveToneMutationOperators();
        }
        throw new IllegalArgumentException("Mutation type unknown: " + mutationType);
    }

    private List<MutationOperator> twelveToneMutationOperators(){
        List<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(twelveToneRhythmMutation);
        return mutationOperators;
    }

    private List<MutationOperator> mutationOperators(){
        List<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(rhythmMutation);
//        mutationOperators.add(operatorMutation);
        mutationOperators.add(replaceMelody);
        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        mutationOperators.add(allNoteMutation);
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        mutationOperators.add(textureMutation);
//        mutationOperators.add(providedMutation); //-> check if provided melody sizes are part of combinations!!
        return mutationOperators;
    }

    private List<MutationOperator> pitchMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);
        mutationOperators.add(allNoteMutation);
        mutationOperators.add(replaceMelody);
        return mutationOperators;
    }

    private List<MutationOperator> rhytmMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(rhythmMutation);
        mutationOperators.add(providedMutation);
        return mutationOperators;
    }

    private List<MutationOperator> timbreMutationOperators(){
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
        mutationOperators.add(providedMutation);
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
        mutationOperators.add(providedMutation);

        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);//no timeline
        mutationOperators.add(allNoteMutation);
//        mutationOperators.add(replaceMelody);
        return mutationOperators;
    }
}
