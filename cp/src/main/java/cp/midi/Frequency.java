package cp.midi;

import cp.model.note.Interval;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by prombouts on 6/03/2017.
 */
public class Frequency {

    private static double concertAFreq = 440.0;

    public static void main(String[] args) {
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pitch(60).build());
        notes.add(NoteBuilder.note().pitch(76).build());
        notes.add(NoteBuilder.note().pitch(67).build());

        List<Integer> pitches = notes.stream().map(n -> n.getPitch()).sorted().collect(Collectors.toList());
        int size = pitches.size();
        int amountOfIntervals = (size * (size - 1 ))/2;
        double[] values = new double[amountOfIntervals];
        float total = 0;
        int intervalCount = 0;
        for (int j = 0; j < size - 1; j++) {
            for (int i = j + 1; i < size; i++) {
                int pitch = pitches.get(j);
                int nextPitch = pitches.get(i);
                System.out.println(pitch + "," + nextPitch);
                if(Interval.isDissonantInterval(pitch - nextPitch)){
                    float interval = Math.abs(getFrequencyDifference(pitch, nextPitch));
                    System.out.println(interval);
                    total = total + interval;
                };
                intervalCount++;
            }
        }
        float avg = total/intervalCount;
        System.out.println(avg);



//        for (int i = 1; i < 24; i++) {
//            System.out.println(i + ": " + getFrequencyDifference(48 ,48 + i));
//
//        }
        System.out.println(getFrequencyDifference(59,60));
//        System.out.println(getFrequencyDifference(60,73));
//        System.out.println(getFrequencyDifference(48,61));
//        System.out.println(getFrequencyDifference(48,59));
//        System.out.println(getFrequencyDifference(67,69));
//        System.out.println(getFrequencyDifference(48,50));
//        System.out.println(getFrequencyDifference(48,52));
//        System.out.println(getFrequencyDifference(48,62));
    }

    public static float getFrequency(final int keyNumber) {
        
        // Concert A Pitch is A4 and has the key number 69
        final int KEY_A4 = 69;
        // Returns the frequency of the given key (equal temperament)
        return (float) (concertAFreq * Math.pow(2, (keyNumber - KEY_A4) / 12d));
    }

    public static float getFrequencyDifference(int pitch, int secondPitch){
        return  getFrequency(secondPitch) - getFrequency(pitch);
    }


}
