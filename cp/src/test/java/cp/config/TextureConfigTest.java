package cp.config;

import cp.DefaultConfig;
import cp.model.harmony.DependantHarmony;
import cp.model.harmony.VoicingType;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class TextureConfigTest {

    @Autowired
    private TextureConfig textureConfig;

    @Test
    public void getAllRowMatrix() {
        TnTnIType tnTnIType = new TnTnIType();
        Set set3_1 = tnTnIType.getPrimeByName("3-1");
        List<DependantHarmony> allRowMatrixDrop2set3_4 = textureConfig.getAllRowMatrix(set3_1.tntnitype, VoicingType.CLOSE);
        for (DependantHarmony dependantHarmony : allRowMatrixDrop2set3_4) {
            dependantHarmony.getNotes().forEach(note -> System.out.println(note.getPitch()));
            System.out.println("-------------------");
        }
    }
}