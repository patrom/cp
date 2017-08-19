package cp.nsga.operator.mutation.melody;

import cp.nsga.operator.mutation.MutationOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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


    @Bean
    public List<MutationOperator> mutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
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

    @Bean
    public List<MutationOperator> pitchMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(oneNoteMutation);
//        mutationOperators.add(oneNoteChromaticMutation);
        mutationOperators.add(allNoteMutation);
        mutationOperators.add(replaceMelody);
        return mutationOperators;
    }

    @Bean
    public List<MutationOperator> rhytmMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(rhythmMutation);
        return mutationOperators;
    }

    @Bean
    public List<MutationOperator> timbreMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        return mutationOperators;
    }

    @Bean
    public List<MutationOperator> textureMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(textureMutation);
        return mutationOperators;
    }

    @Bean
    public List<MutationOperator> providedMutationOperators(){
        ArrayList<MutationOperator> mutationOperators = new ArrayList<>();
        mutationOperators.add(articulationMutation);
        mutationOperators.add(dynamicMutation);
        mutationOperators.add(technicalMutation);
        mutationOperators.add(textureMutation);
//        mutationOperators.add(providedMutation);
        return mutationOperators;
    }

    @Bean
    public List<MutationOperator> providedRhythmOperators(){
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
