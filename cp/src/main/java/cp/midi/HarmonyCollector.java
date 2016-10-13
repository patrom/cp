package cp.midi;

import cp.model.note.Note;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class HarmonyCollector implements Collector<Note, HarmonyPosition, List<HarmonyPosition>>{

	@Override
	public Supplier<HarmonyPosition> supplier() {
		return HarmonyPosition::new;
	}

	@Override
	public BiConsumer<HarmonyPosition, Note> accumulator() {
		return (harmony, note) -> { 
				harmony.setPosition(note.getPosition());
				harmony.addNote(note);
			};
	}

	@Override
	public BinaryOperator<HarmonyPosition> combiner() {
		return (left, right) -> {
            left.setNotes(right.getNotes());
            return left;
        };
	}

	@Override
	public Function<HarmonyPosition, List<HarmonyPosition>> finisher() {
		return (harmony) -> {
				List<HarmonyPosition> list = new ArrayList<>();
				list.add(harmony);
				return list;
			};
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		return EnumSet.of(Characteristics.UNORDERED);
	}
	
	public static <T> Collector<Note, HarmonyPosition, List<HarmonyPosition>> toHarmonyCollector() {
	    return new HarmonyCollector();
	}

}
