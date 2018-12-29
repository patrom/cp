package cp.composition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component(value="chordComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "-1")
public class ChordComposition extends  Composition {
}
