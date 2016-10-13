package cp.model.setclass;

import com.newscores.setTheory.PitchClassSet;
import com.newscores.setTheory.PitchClassSetCatalog;
import com.newscores.setTheory.PitchSequence;
import com.newscores.setTheory.PitchSet;
import org.apache.commons.lang.ArrayUtils;

public class TestSetTheory {

	public static void main(String[] args) {
		PitchSequence mySeq = new PitchSequence ("60 62 67 64 69 71 65 72");
		System.out.println(mySeq.getDescriptor());
		PitchSequence  newSet = (PitchSequence) mySeq .T(1).I().R().subSequence( 0, 4 );

		System.out.println( newSet.getMembers().toString() );
		
		PitchClassSet normalFormSet = new PitchClassSet( mySeq .T(1).I().R().subSequence( 0, 4 ).getMembers() ).getNormalForm();

		System.out.println( normalFormSet .toString() );
		
		PitchSet pitchSet = new PitchSet();
	
		pitchSet.addPitch(60);
		pitchSet.addPitch(61);
		pitchSet.addPitch(69);
//		pitchSet.addPitch(60);
		
		System.out.println(pitchSet);
		System.out.println(pitchSet.getPitchClassSet());
		System.out.println(pitchSet.range());
		System.out.println(pitchSet.T(1));
		System.out.println(pitchSet.complement());
		
		PitchClassSet pcSet = new PitchClassSet(pitchSet);
		System.out.println(pcSet);
		System.out.println("test:" + PitchClassSetCatalog.getNameByPitchClassSet(pcSet));
		System.out.println(pcSet.getDescription());
		System.out.println(ArrayUtils.toString(pcSet.icVector()));
		System.out.println(pcSet.getSignature());
		
		PitchClassSet superSet = new PitchClassSet("0 1 4 7");
		System.out.println(superSet);
		System.out.println(superSet.consecutiveIntervalVector());
		System.out.println(pcSet.isSubset(superSet));
	}
}
