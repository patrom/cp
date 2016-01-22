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
	private NoteStep Dflat;
	@Autowired
	private NoteStep Dsharp;
	@Autowired
	private NoteStep E;
	@Autowired
	private NoteStep Eflat;
	@Autowired
	private NoteStep Esharp;
	@Autowired
	private NoteStep F;
	@Autowired
	private NoteStep Fsharp;
	@Autowired
	private NoteStep G;
	@Autowired
	private NoteStep Gflat;
	@Autowired
	private NoteStep Gsharp;
	@Autowired
	private NoteStep A;
	@Autowired
	private NoteStep Aflat;
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
	
	@Bean(name="keyOfG")
	public Map<Integer, NoteStep> keyOfG() {
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
		map.put(10, Asharp);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfD")
	public Map<Integer, NoteStep> keyOfD() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, Bsharp);
		map.put(1, Csharp);
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
		return map;
	}
	
	@Bean(name="keyOfA")
	public Map<Integer, NoteStep> keyOfA() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, Bsharp);
		map.put(1, Csharp);
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
		return map;
	}
	
	@Bean(name="keyOfE")
	public Map<Integer, NoteStep> keyOfE() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, Bsharp);
		map.put(1, Csharp);
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
		return map;
	}
	
	@Bean(name="keyOfB")
	public Map<Integer, NoteStep> keyOfB() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, Bsharp);
		map.put(1, Csharp);
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
		return map;
	}
	
	@Bean(name="keyOfFsharp")
	public Map<Integer, NoteStep> keyOfFsharp() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, Bsharp);
		map.put(1, Csharp);
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
		return map;
	}
	
	@Bean(name="keyOfF")
	public Map<Integer, NoteStep> keyOfF() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Csharp);
		map.put(2, D);
		map.put(3, Eflat);
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
	
	@Bean(name="keyOfBflat")
	public Map<Integer, NoteStep> keyOfBflat() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Csharp);
		map.put(2, D);
		map.put(3, Eflat);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Fsharp);
		map.put(7, G);
		map.put(8, Aflat);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfEflat")
	public Map<Integer, NoteStep> keyOfEflat() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Dflat);
		map.put(2, D);
		map.put(3, Eflat);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Fsharp);
		map.put(7, G);
		map.put(8, Aflat);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfAflat")
	public Map<Integer, NoteStep> keyOfAflat() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Dflat);
		map.put(2, D);
		map.put(3, Eflat);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Gflat);
		map.put(7, G);
		map.put(8, Aflat);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfDflat")
	public Map<Integer, NoteStep> keyOfDflat() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Dflat);
		map.put(2, D);
		map.put(3, Eflat);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Gflat);
		map.put(7, G);
		map.put(8, Aflat);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	
	@Bean(name="keyOfGflat")
	public Map<Integer, NoteStep> keyOfGflat() {
		Map<Integer, NoteStep> map = new TreeMap<>();
		map.put(0, C);
		map.put(1, Dflat);
		map.put(2, D);
		map.put(3, Eflat);
		map.put(4, E);
		map.put(5, F);
		map.put(6, Gflat);
		map.put(7, G);
		map.put(8, Aflat);
		map.put(9, A);
		map.put(10, Bflat);
		map.put(11, B);
		return map;
	}
	

}
