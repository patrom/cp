package cp.objective.voiceleading;

import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.arraycopy;

public class VoiceLeading {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VoiceLeading.class.getName());
	
	private static int optimalSize = 9999;

	public static VoiceLeadingSize caculateSize(Multiset<Integer> sourceSet,
			Multiset<Integer> targetSet) {
		Multiset<Integer> intersection = Multisets.intersection(sourceSet, targetSet);
		Integer[] source = multiSetToArray(sourceSet);
		Integer[] target = multiSetToArray(targetSet);
		if (!intersection.isEmpty()) {
			int firstCommonPC = intersection.iterator().next();
			rotateArrayToFirstCommonPC(source, firstCommonPC);
			rotateArrayToFirstCommonPC(target, firstCommonPC);
			source = addFirstPitchClass(source);
			target = addFirstPitchClass(target);
			return calculateVoiceLeadingSize(target, source);
		} else {
			LOGGER.debug("non common tones");
			source = addFirstPitchClass(source);
			target = addFirstPitchClass(target);
			VoiceLeadingSize voiceLeadingSize = calculateVoiceLeadingSize(target, source);
			optimalSize = voiceLeadingSize.getSize();
			VoiceLeadingSize voiceLeadingSizeOptimal = voiceLeadingSize;
			for (int i = 0; i < target.length - 1; i++) {
            	rotateArray(target, 1); 
            	voiceLeadingSize = calculateVoiceLeadingSize(target, source);
            	if (voiceLeadingSize.getSize() < optimalSize  ) {
    				optimalSize = voiceLeadingSize.getSize();
    				voiceLeadingSizeOptimal = voiceLeadingSize;
            	}
            };
			return voiceLeadingSizeOptimal;
		}			
	}
		

	private static void rotateArrayToFirstCommonPC(Integer[] array,
			int firstCommonPC) {
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == firstCommonPC) {
				index = i;
				break;
			}
		}
		rotateArray(array, index);
	}

	private static Integer[] multiSetToArray(Multiset<Integer> multiSet) {
		Integer[] array = new Integer[multiSet.size()];
		array = multiSet.toArray(array);
		return array;
	}

	private static VoiceLeadingSize calculateVoiceLeadingSize(Integer[] target,
			Integer[] source) {
		//fill empty matrix
		Table<Integer, Integer, VoiceLeadingSize> matrix = HashBasedTable.create();	
		for (int row = 0; row < source.length; row++) {
			for (int col = 0; col < target.length; col++) {
				matrix.put(row, col, new VoiceLeadingSize());
			}
		}
		ArrayTable<Integer, Integer, VoiceLeadingSize> vlMatrix = ArrayTable.create(matrix);
		//set first
		VoiceLeadingSize voiceLeadingSize = vlMatrix.at(0, 0);
		voiceLeadingSize.addPitchClassToSource(source[0]);
		voiceLeadingSize.addPitchClassToTarget(target[0]);
		voiceLeadingSize.calculateSize();
		vlMatrix.set(0, 0, voiceLeadingSize);
		// set first row
		for (int col = 1; col < target.length; col++) {
			voiceLeadingSize = vlMatrix.at(0, col);
			VoiceLeadingSize prevVoiceLeadingSize = vlMatrix.at(0, col-1);
			voiceLeadingSize.addAllToSource(prevVoiceLeadingSize.getSource());
			voiceLeadingSize.addPitchClassToSource(source[0]);
			voiceLeadingSize.addAllToTarget(prevVoiceLeadingSize.getTarget());
			voiceLeadingSize.addPitchClassToTarget(target[col]);
			voiceLeadingSize.calculateSize();
			vlMatrix.set(0, col, voiceLeadingSize);
		}
		//set first column
		for (int row = 1; row < source.length; row++) {
			voiceLeadingSize = vlMatrix.at(row, 0);
			VoiceLeadingSize prevVoiceLeadingSize = vlMatrix.at(row-1, 0);
			voiceLeadingSize.addAllToSource(prevVoiceLeadingSize.getSource());
			voiceLeadingSize.addPitchClassToSource(source[row]);
			voiceLeadingSize.addAllToTarget(prevVoiceLeadingSize.getTarget());
			voiceLeadingSize.addPitchClassToTarget(target[0]);
			voiceLeadingSize.calculateSize();
			vlMatrix.set(row, 0, voiceLeadingSize);
		}
		//set rows (except first)
		for (int row = 1; row < source.length; row++) {
			for (int col = 1; col < target.length; col++) {
				voiceLeadingSize = vlMatrix.at(row, col);
				VoiceLeadingSize prevVoiceLeadingSize = findPreviousLowestVoiceLeadingSize(vlMatrix, row, col);
				voiceLeadingSize.addAllToSource(prevVoiceLeadingSize.getSource());
				voiceLeadingSize.addPitchClassToSource(source[row]);
				voiceLeadingSize.addAllToTarget(prevVoiceLeadingSize.getTarget());
				voiceLeadingSize.addPitchClassToTarget(target[col]);
				voiceLeadingSize.calculateSize();
				vlMatrix.set(row, col, voiceLeadingSize);
			}	
		}
		return vlMatrix.at(source.length - 1, target.length - 1) ;
	}
	
	 private static Integer[] addFirstPitchClass(Integer[] array) {
		Integer[] temp = new Integer[array.length + 1];
		System.arraycopy( array, 0, temp, 0, array.length );
		temp[array.length] = array[0];
		return temp;
	}

	private static VoiceLeadingSize findPreviousLowestVoiceLeadingSize(ArrayTable<Integer, Integer, VoiceLeadingSize> vlMatrix, int row, int column) {
		 VoiceLeadingSize voiceLeadingSize = vlMatrix.at(row - 1, column - 1);
		 int size = voiceLeadingSize.getSize();
		 if (vlMatrix.at(row, column - 1).getSize() < size) {
			 voiceLeadingSize = vlMatrix.at(row, column - 1);
		} else if(vlMatrix.at(row - 1, column).getSize() < size){
			 voiceLeadingSize = vlMatrix.at(row - 1, column);
		}
		return voiceLeadingSize;
	}

	public static void rotateArray(Integer[] array, int index){
		Integer[] result = new Integer[array.length];
	    arraycopy(array, index, result, 0, array.length - index);
	    arraycopy(array, 0, result, array.length - index, index);
	    arraycopy(result, 0, array, 0, array.length);
	}
	
	
	private static Table<Integer, Integer, Integer> createTMatrix(
			Multiset<Integer> chordA, Multiset<Integer> chordB) {
		Table<Integer, Integer, Integer> TMatrix = HashBasedTable.create();
		int i = 0, j = 0;
		for (Integer noteA : chordA) {
			for (Integer noteB : chordB) {
				TMatrix.put(i, j, (noteA - noteB) % 12);
				j++;
			}
			i++;
		}
		return TMatrix;
	}
}
