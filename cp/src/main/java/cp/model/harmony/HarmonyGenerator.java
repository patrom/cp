package cp.model.harmony;

import cp.model.melody.MelodyBlock;
import cp.model.note.Note;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by prombouts on 7/12/2016.
 */
public class HarmonyGenerator {

    private final Map<Integer, List<CpHarmony>> harmoniesPerVoice = new TreeMap<>();

    public List<Note> generateHarmony(List<CpHarmony> harmonies, MelodyBlock melodyBlock) {
        List<Note> harmonyNotes = new ArrayList<>();
        int size = harmonies.size() - 1;
        for (int i = 0; i < size; i++) {
             CpHarmony harmony = harmonies.get(i);
             CpHarmony nextHarmony = harmonies.get(i + 1);
             List<Note> notesInHarmonyRange = melodyBlock.getMelodyBlockNotesWithRests().stream()
                    .filter(n -> harmony.getPosition() <= n.getPosition() && n.getPosition() < nextHarmony.getPosition())
                    .collect(Collectors.toList());

        }
        return harmonyNotes;
    }

    public CpHarmony getHarmonyAtPosition(int position, int voice){
        List<CpHarmony> harmonies = harmoniesPerVoice.get(voice);
        Optional<CpHarmony> optional = harmonies.stream().filter(h -> h.getPosition() <= position && position <= h.getPosition()).findFirst();
        if(optional.isPresent()){
            return optional.get();
        }
        throw new IllegalArgumentException("No harmony found at position; " + position + " for voice: " + voice);
    }

    public void addKeysForVoice(List<CpHarmony> harmonies, int voice){
        this.harmoniesPerVoice.put(voice, harmonies);
    }
}
