package assessment20230825.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConfigTest {
  @Test
  void config_singleton_pass() throws Exception {
    Config config = Config.getInstance();
    config.properties.put("test", "test-test");
    Config config2 = Config.getInstance();
    assertEquals(config, config2);
    assertEquals(config.properties.get("test"), config2.properties.get("test"));
  }
}
