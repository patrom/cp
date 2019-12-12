package cp.composition.accomp;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.combination.even.FourNoteEven;
import cp.composition.voice.MelodyVoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

/**
 * Created by prombouts on 19/04/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class AccompGroupTest extends AbstractTest{

    private AccompGroup accompGroup;
    @Autowired
    private FourNoteEven fourNoteEven;
    @Autowired
    private MelodyVoice melodyVoice;

    @Test
    public void getNotes() {
        ArrayList<Integer> contour = new ArrayList<>();
        contour.add(1);
        contour.add(1);
        contour.add(1);
        contour.add(-1);
//        BeatGroup beatGroup = new BeatGroupTwo(DurationConstants.QUARTER);
        accompGroup = new AccompGroup(melodyVoice, contour);
//        accompGroup.getNotes(beatGroup).forEach(n -> System.out.println(n));

//        playOnKontakt(notes, new InstrumentMapping(new ViolinSolo(),4,0), 90, 50000);
    }

}