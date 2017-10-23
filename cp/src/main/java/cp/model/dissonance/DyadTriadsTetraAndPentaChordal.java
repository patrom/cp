package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DyadTriadsTetraAndPentaChordal {

	@Autowired
	private TonalSetClassDissonance tonalSetClassDissonance;
	
	public double getDissonance(CpHarmony harmony) {
		int size = harmony.getChord().getPitchClassSet().size();
		switch (size){
			case 2:
//				return intervals(chord);
				return 0;
			case 3:
				return triads(harmony.getChord());
			case 4:
				return tetra(harmony.getChord());
			case 5:
				return penta(harmony);
		}
		return 0;
	}

	private double penta(CpHarmony harmony) {
		return tonalSetClassDissonance.getDissonance(harmony);
	}

	private double tetra(Chord chord) {
        switch (chord.getChordType()) {
            case MAJOR7:
                return 1.0;
            case MAJOR7_1:
                return 1.0;
            case MAJOR7_2:
                return 0.98;
            case MAJOR7_3:
                return 0.98;
            case MINOR7:
                return 1.0;
            case MINOR7_1:
                return 1.0;
            case MINOR7_2:
                return 0.98;
            case MINOR7_3:
                return 0.98;
            case DOM7:
                return 1.0;
            case HALFDIM7:
                return 1.0;
            case DIM7:
                return 1.0;
        }
//		return tonalSetClassDissonance.getDissonance(chord);
		return 0;
    }

    private double triads(Chord chord){
		switch (chord.getChordType()) {
			case MAJOR:
				return 0.99;
			case MAJOR_1:
				return 0.99;
			case MAJOR_2:
				return 0.98;
			case MINOR:
				return 0.99;
			case MINOR_1:
				return 0.99;
			case MINOR_2:
				return 0.98;
			case DIM:
				return 0.99;
			case AUGM:
				return 0.97;
			case DOM:
				return 1.0;
			case KWARTEN:
				return 0.98;
			case ADD9:
				return 0.99;
			case MAJOR7_OMIT5:
				return 1.0;
			case MINOR7_OMIT5:
				return 1.0;
			case MINOR7_OMIT5_1:
				return 0.99;
		}
//		return tonalSetClassDissonance.getDissonance(chord);
		return 0;
	}

	private double intervals(Chord chord) {
		switch (chord.getChordType()) {
			case CH2_GROTE_TERTS:
				return 0.97;
			case CH2_KLEINE_TERTS:
				return 0.97;
			case CH2_GROTE_SIXT:
				return 0.97;
			case CH2_KLEINE_SIXT:
				return 0.97;
				
			case CH1://octaaf
				return 0.8;
			case CH2_KWART:
				return 0.9;
			case CH2_KWINT:
				return 0.9;
			
			case CH2_GROTE_SECONDE:
				return 0.4;
			case CH2_GROOT_SEPTIEM:
				return 0.6;
			case CH2_KLEIN_SEPTIEM:
				return 0.7;
			case CH2_KLEINE_SECONDE:
				return 0.3;
			case CH2_TRITONE:
				return 0.7;
		}
//		return tonalSetClassDissonance.getDissonance(chord);
		return 0;
	}

}


