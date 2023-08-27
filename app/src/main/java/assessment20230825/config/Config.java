package assessment20230825.config;

import assessment20230825.database.ResourceReader;
import assessment20230825.exceptions.AppException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Utility class to read application.properties file. */
public class Config {

  private static final String APPLICATION_PROPERTIES = "application.properties";
  private static final AtomicReference<Config> INSTANCE = new AtomicReference<>();

  public final Properties properties = new Properties();
  /**
   * Read application.properties file and replace all ${ENV_VAR} with the value of the environment
   * variable ENV_VAR
   *
   * @throws AppException if unable to read application.properties
   */
  private Config() throws AppException {
    String config = ResourceReader.readFromResources("/", APPLICATION_PROPERTIES);
    try (InputStream input = new ByteArrayInputStream(config.getBytes())) {
      properties.load(input);
      properties
          .stringPropertyNames()
          .forEach(
              key -> {
                String value = properties.getProperty(key);
                Matcher matcher = Pattern.compile(value).matcher(value);
                while (matcher.find()) {
                  String envVarName = matcher.group(1);
                  String envValue = System.getenv(envVarName);
                  if (envValue != null) {
                    value = value.replace(matcher.group(0), envValue);
                  }
                }
                properties.setProperty(key, value);
              });
    } catch (IOException e) {
      throw new AppException("Unable to load application.properties");
    }
  }

  /**
   * Get instance of Config
   *
   * @return Config instance
   * @throws AppException if unable to read application.properties
   */
  public static Config getInstance() throws AppException {
    Config instance = INSTANCE.get();
    if (instance == null) {
      instance = new Config();
      if (INSTANCE.compareAndSet(null, instance)) {
        return instance;
      } else {
        return INSTANCE.get();
      }
    }
    return instance;
  }
}
