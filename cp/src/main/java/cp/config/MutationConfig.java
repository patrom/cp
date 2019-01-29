package cp.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Boolean.TRUE;

@Component
public class MutationConfig {

    private Map<Integer, Boolean> mutationConfiguration = new TreeMap<>();

    @PostConstruct
    public void initVoiceConfig() {
        mutationConfiguration.put(0, TRUE);
        mutationConfiguration.put(1, TRUE);
        mutationConfiguration.put(2, Boolean.FALSE);
        mutationConfiguration.put(3, TRUE);
        mutationConfiguration.put(4, TRUE);
        mutationConfiguration.put(5, TRUE);
    }

    public boolean isVoiceMutable(int voice) {
        return mutationConfiguration.get(voice);
    }

}
