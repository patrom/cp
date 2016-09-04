package cp;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import rx.Observable;



public class TestRXJava {

	public static void main(String[] args) {
		
		Observable.just("Hello, world!")
	    	.subscribe(s -> System.out.println(s));
		
		Observable.from(new String[]{"url1", "url2", "url3"})
	    .subscribe(url -> System.out.println(url));
		
		List<Note> notes = new ArrayList<>();
		Note note = NoteBuilder.note().pc(0).pitch(60).pos(DurationConstants.QUARTER).build();
		notes.add(note);
		note = NoteBuilder.note().pc(2).pitch(62).pos(DurationConstants.HALF).build();
		notes.add(note);
		note = NoteBuilder.note().pc(3).pitch(63).pos(DurationConstants.QUARTER).build();
		notes.add(note);
		
		Observable.just(notes)
			.flatMap(l -> Observable.from(l))
			.groupBy(n -> n.getPosition())
    		.subscribe(n -> System.out.println(n));
	}
}
