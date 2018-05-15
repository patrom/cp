package cp.out.instrument.brass.ensemble;

import cp.out.instrument.Ensemble;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trumpet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component(value = "trHnEnsemble")
public class TrHnEnsemble extends Ensemble {

    @PostConstruct
    public void init(){
        instruments.put(0, new Trumpet());
        instruments.put(1, new Trumpet());
        instruments.put(2, new FrenchHorn());
        instruments.put(3, new FrenchHorn());
    }
}
