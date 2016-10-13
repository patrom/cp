package cp.out.print.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.TreeMap;

@Configuration
public class KeyMapConfig {
	
	@Autowired
	private Key C;
	@Autowired
	private Key Csharp;
	@Autowired
	private Key D;
	@Autowired
	private Key Dflat;
	@Autowired
	private Key Dsharp;
	@Autowired
	private Key E;
	@Autowired
	private Key Eflat;
	@Autowired
	private Key Esharp;
	@Autowired
	private Key F;
	@Autowired
	private Key Fsharp;
	@Autowired
	private Key G;
	@Autowired
	private Key Gflat;
	@Autowired
	private Key Gsharp;
	@Autowired
	private Key A;
	@Autowired
	private Key Aflat;
	@Autowired
	private Key Asharp;
	@Autowired
	private Key Bflat;
	@Autowired
	private Key B;
	@Autowired
	private Key Bsharp;
	
	
	@Bean(name="keyOfC")
	public Map<Integer, Key> keyOfC() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfG() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfD() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfA() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfE() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfB() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfFsharp() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfF() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfBflat() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfEflat() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfAflat() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfDflat() {
		Map<Integer, Key> map = new TreeMap<>();
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
	public Map<Integer, Key> keyOfGflat() {
		Map<Integer, Key> map = new TreeMap<>();
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
