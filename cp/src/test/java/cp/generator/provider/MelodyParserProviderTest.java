package cp.generator.provider;

import cp.DefaultConfig;
import cp.VariationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MelodyParserProviderTest {

    @Autowired
    @Qualifier(value = "melodyParserProvider")
    private MelodyParserProvider melodyParserProvider;

    @Test
    public void parse() throws IOException, XMLStreamException {
        melodyParserProvider.parse();
    }

}