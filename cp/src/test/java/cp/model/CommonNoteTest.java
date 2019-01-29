package cp.model;

import cp.DefaultConfig;
import cp.model.common.CommonNote;
import cp.model.common.CommonNoteValueObject;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class CommonNoteTest {

    @Autowired
    private CommonNote commonNote;

    @Test
    public void getCommonNotes() {
        List<CommonNoteValueObject> commonNotes = commonNote.getCommonNotes("4-17", 2);
        for (CommonNoteValueObject commonNoteValueObject : commonNotes) {
            System.out.println("Common ");
            commonNoteValueObject.getCommonPitchClasses().forEach(System.out::println);
            System.out.println("Disjunct ");
            commonNoteValueObject.getDisjunctPitchClasses1().forEach(System.out::println);
            System.out.println("Disjunct ");
            commonNoteValueObject.getDisjunctPitchClasses2().forEach(System.out::println);
        }
    }

    @Test
    public void getCommonNotes2() {
        List<CommonNoteValueObject> commonNotes = commonNote.getCommonNotes("6-32", 3);
        for (CommonNoteValueObject commonNoteValueObject : commonNotes) {
            System.out.println("Common ");
            commonNoteValueObject.getCommonPitchClasses().forEach(integer -> System.out.println(integer));
            System.out.println("Disjunct ");
            commonNoteValueObject.getDisjunctPitchClasses1().forEach(integer -> System.out.println(integer));
            System.out.println("Disjunct ");
            commonNoteValueObject.getDisjunctPitchClasses2().forEach(integer -> System.out.println(integer));
        }
    }

}