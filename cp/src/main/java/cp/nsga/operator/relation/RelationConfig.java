package cp.nsga.operator.relation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 24/10/2016.
 */
@Component
public class RelationConfig {

    private List<Relation> relations = new ArrayList<>();

    public List<Relation> getRelations() {
        return relations;
    }

    public void addOperatorRelations(Relation operatorRelation) {
        this.relations.add(operatorRelation);
    }
}
