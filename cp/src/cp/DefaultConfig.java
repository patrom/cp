package cp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cp.variation.nonchordtone.Variation;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DefaultConfig {
	
	@Bean
	public HashMap<String, Object> parameters() {
		HashMap<String, Object> probabilityParamters = new HashMap<>();
		probabilityParamters.put("probabilityAddRhythm", 1.0);
		probabilityParamters.put("probabilityRemoveRhythm", 0.0);
		probabilityParamters.put("probabilityOneNote", 1.0);
		probabilityParamters.put("probabilityArticulation", 0.2);
		return probabilityParamters;
	}
	
	@Bean
	public HashMap<String, Object> distanceParameters() {
		HashMap<String, Object> map = new HashMap<>();
		int[] distance = {2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
		map.put("distance", distance);
		return map;
	}
	
	@Bean
	public String midiFilesPath(){
		return "C:/Users/prombouts/git/cp/cp/resources/midi";
		// "C:/Dev/git/neo/neo/resources/midi"
		// /Users/parm/git/neo/neo/resources/midi
//		return "/Users/parm/git/neo/neo/resources/midi";
	}
	
}
