package cp;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import rx.Observable;



public class TestRXJava {

	public static void main(String[] args) {
		
		Observable.just("Hello, world!")
	    	.subscribe(s -> System.out.println(s));
		
		Observable.from(new String[]{"url1", "url2", "url3"})
	    .subscribe(url -> System.out.println(url));
		
		List<Note> notes = new ArrayList<>();
		Note note = NoteBuilder.note().pc(0).pitch(60).pos(12).build();
		notes.add(note);
		note = NoteBuilder.note().pc(2).pitch(62).pos(24).build();
		notes.add(note);
		note = NoteBuilder.note().pc(3).pitch(63).pos(12).build();
		notes.add(note);
		
		Observable.just(notes)
			.flatMap(l -> Observable.from(l))
			.groupBy(n -> n.getPosition())
    		.subscribe(n -> System.out.println(n));
	}
}
