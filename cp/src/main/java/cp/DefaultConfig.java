package cp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DefaultConfig {

	@Bean
	public HashMap<String, Object> distanceParameters() {
		HashMap<String, Object> map = new HashMap<>();
		int[] distance = {2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
		map.put("distance", distance);
		return map;
	}
	
	@Bean
	public List<Integer[]> evenPulseDivisions(){
		List<Integer[]> pulseDivisions = new ArrayList<>();
		pulseDivisions.add(new Integer[]{0,1});
		pulseDivisions.add(new Integer[]{1,1});
		pulseDivisions.add(new Integer[]{1,0});
		
//		pulseDivisions.add(new Integer[]{1,0,0});//same as {1,0}
//		pulseDivisions.add(new Integer[]{0,1,0});
//		pulseDivisions.add(new Integer[]{0,0,1});
		
		pulseDivisions.add(new Integer[]{1,1,1});
		
//		pulseDivisions.add(new Integer[]{1,1,0});
//		pulseDivisions.add(new Integer[]{1,0,1});
//		pulseDivisions.add(new Integer[]{0,1,1});
		return pulseDivisions;
	}
	
	@Bean
	public List<Integer[]> oddPulseDivisions(){
		List<Integer[]> oddDivisions = new ArrayList<>();
//		oddDivisions.add(new Integer[]{0,1});
//		oddDivisions.add(new Integer[]{1,1});
//		oddDivisions.add(new Integer[]{1,0,0});
		
//		oddDivisions.add(new Integer[]{1,0,0});//same as {1,0}
//		oddDivisions.add(new Integer[]{0,1,0});
//		oddDivisions.add(new Integer[]{0,0,1});
		
//		oddDivisions.add(new Integer[]{1,1,1});
		
//		oddDivisions.add(new Integer[]{1,1,0});
		oddDivisions.add(new Integer[]{1,0,1});
//		oddDivisions.add(new Integer[]{0,1,1});
		return oddDivisions;
	}

	@Bean
	public Resource fileTestResource(){
		return new FileSystemResource("src/main/resources/rowMatrix/");
	}

	@Bean
	public Resource fileResource(){
		return new FileSystemResource("");
	}
	
}
