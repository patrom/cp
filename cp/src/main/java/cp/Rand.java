package cp;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rand {

	public static void main(String[] args) throws IOException {
		Resource resource = new FileSystemResource("");
		System.out.println(resource.getFile().getAbsolutePath());

		Random random = new Random();
		IntStream intStream = random.ints(0, 12);
		List<Integer> positions = intStream
				.limit(10)
				.sorted()
				.distinct()
				.map(i -> i * 12)
				.boxed()
				.collect(Collectors.toList());
		if (!positions.contains(0)) {
			positions.add(0, 0);
		}
		Integer[] pos = new Integer[positions.size()];
		pos = positions.toArray(pos);
		System.out.println(Arrays.toString(pos));
	}
}
