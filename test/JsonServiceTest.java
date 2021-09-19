import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.JsonService;

import java.io.File;
import java.io.IOException;

public class JsonServiceTest {

    private ObjectMapper mapper;
    private JsonService jsonService = new JsonService();

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testFlattenOneLevelJson() throws IOException {
        String result = "{\"name\":\"Pepe\",\"lastName\":\"Perez\"}";
        File oneLevelFile = new File(this.getClass().getClassLoader().getResource("1Level.json").getFile());
        JsonNode oneLevelJson = mapper.readValue(oneLevelFile, JsonNode.class);
        Assert.assertEquals(result, jsonService.flattenJson(oneLevelJson).toString());
    }

    @Test
    public void testFlatten2LevelJson() throws IOException {
        String result = "{\"address\":{\"street\":\"Street 1\",\"city\":\"Rotterdam\",\"zipcode\":\"1234AA\"}}";
        File twoLevelFile = new File(this.getClass().getClassLoader().getResource("2Level.json").getFile());
        JsonNode twoLevelJson = mapper.readValue(twoLevelFile, JsonNode.class);
        Assert.assertEquals(result, jsonService.flattenJson(twoLevelJson).toString());
    }
}
