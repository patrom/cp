package cp.out.orchestration;

import cp.model.note.Note;

import java.util.List;

/**
 * Created by prombouts on 15/11/2016.
 */
@FunctionalInterface
public interface DuplicationOrchestration {

    List<Note> duplicate(List<Note> notes);
}
