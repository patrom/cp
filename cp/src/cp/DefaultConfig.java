package cp;

import java.util.HashMap;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DefaultConfig {
	
	@Bean
	public HashMap<String, Object> parameters() {
		return new HashMap<>();
	}
	
	@Bean
	public String midiFilesPath(){
		return "C:/Users/prombouts/git/neo/neo/resources/midi";
		// "C:/Dev/git/neo/neo/resources/midi"
		// /Users/parm/git/neo/neo/resources/midi
//		return "/Users/parm/git/neo/neo/resources/midi";
	}
	
}
