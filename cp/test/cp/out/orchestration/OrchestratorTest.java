package cp.out.orchestration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class OrchestratorTest {
	
	@Autowired
	private Orchestrator ochestrator;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrchestrate() throws Exception {
		ochestrator.orchestrate();
	}

}
