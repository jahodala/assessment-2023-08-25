package assessment20230825.database;

import assessment20230825.exceptions.AppException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/** Utility class to read files from resources directory. */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Log4j2
public class ResourceReader {
  private static final String DATABASE_RESOURCE_DIR = "/database/";

  /**
   * Read file from resources/database directory
   *
   * @param fileName file name
   * @return file content
   * @throws AppException if file not found or unable to read file content
   */
  public static String readFromResources(String fileName) throws AppException {
    return readFromResources(DATABASE_RESOURCE_DIR, fileName);
  }

  /**
   * Read file from resources directory
   *
   * @param dir directory name
   * @param fileName file name
   * @return file content
   * @throws AppException if file not found or unable to read file content
   */
  public static String readFromResources(String dir, String fileName) throws AppException {
    if (dir == null) {
      throw new AppException("Directory name cannot be null");
    }
    if (fileName == null) {
      throw new AppException("File name cannot be null");
    }

    URL resourceUrl = ResourceReader.class.getResource(dir + fileName);

    if (resourceUrl == null) {
      throw new AppException("File not found: " + fileName);
    }

    Path resourcePath;
    try {
      resourcePath = Paths.get(resourceUrl.toURI());
    } catch (java.net.URISyntaxException e) {
      log.error(e.getMessage(), e);
      throw new AppException("Invalid URI: " + e.getMessage());
    }

    try {
      return Files.readString(resourcePath);
    } catch (IOException e) {
      throw new AppException("Cannot read file content: " + e.getMessage());
    }
  }
}
