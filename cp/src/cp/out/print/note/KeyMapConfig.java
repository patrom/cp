package cp.out.print.note;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyMapConfig {
	
	@Autowired
	private NoteStep C;
	@Autowired
	private NoteStep Csharp;
	@Autowired
	private NoteStep D;
	@Autowired
	private NoteStep Dsharp;
	@Autowired
	private NoteStep E;
	@Autowired
	private NoteStep Esharp;
	@Autowired
	private NoteStep F;
	@Autowired
	private NoteStep Fsharp;
	@Autowired
	private NoteStep G;
	@Autowired
	private NoteStep Gsharp;
	@Autowired
	private NoteStep A;
	@Autowired
	private NoteStep Asharp;
	@Autowired
	private NoteStep Bflat;
	@Autowired
	private NoteStep B;
	@Autowired
	private NoteStep Bsharp;
	
	
	@Bean(name="keyOfC")
	public Map<Integer, NoteStep> keyOfC() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Csharp);
		map.put(2, D);
		map.put(3, Dsharp);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Fsharp);
		map.put(7, G);
		map.put(8, Gsharp);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfD")
	public Map<Integer, NoteStep> keyOfD() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(2, D);
		map.put(3, Dsharp);
		map.put(4, E);
		map.put(5, Esharp);
		map.put(6, Fsharp);
		map.put(7, G);
		map.put(8, Gsharp);
		map.put(9, A);
		map.put(10, Asharp);
		map.put(11, B);
		map.put(0, Bsharp);
		map.put(1, Csharp);
		return map;
	}

}
