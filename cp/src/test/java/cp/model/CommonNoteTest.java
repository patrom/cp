package cp.model;

import cp.DefaultConfig;
import cp.model.common.CommonNote;
import cp.model.common.CommonNoteValueObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class CommonNoteTest {

    @Autowired
    private CommonNote commonNote;

    @Test
    public void getCommonNotes() {
        List<CommonNoteValueObject> commonNotes = commonNote.getCommonNotes("5-34", 2);
        for (CommonNoteValueObject commonNoteValueObject : commonNotes) {
            System.out.println("Common ");
            String collect = commonNoteValueObject.getCommonPitchClasses().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("Disjunct ");
            collect = commonNoteValueObject.getDisjunctPitchClasses1().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("Disjunct ");
            collect = commonNoteValueObject.getDisjunctPitchClasses2().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("--------------");
        }
    }

    @Test
    public void getCommonNotes2() {
        List<CommonNoteValueObject> commonNotes = commonNote.getCommonNotes("6-Z28", 3);
        for (CommonNoteValueObject commonNoteValueObject : commonNotes) {
            System.out.println("Common ");
            String collect = commonNoteValueObject.getCommonPitchClasses().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("Disjunct ");
            collect = commonNoteValueObject.getDisjunctPitchClasses1().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("Disjunct ");
            collect = commonNoteValueObject.getDisjunctPitchClasses2().stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(collect);
            System.out.println("--------------");
        }
    }

}