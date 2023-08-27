package assessment20230825.database;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.Test;

class ResourceReaderTest {
  @Test
  void readFromResourcesSuccess() {
    String expected = """
    This is The Test
    next line is expected as well""";
    assertDoesNotThrow(
        () -> {
          String actual = ResourceReader.readFromResources("test-file.txt");
          assertEquals(expected, actual);
        });
  }

  @Test
  void readFromResourcesExceptionNullPath() {
    assertThrows(AppException.class, () -> ResourceReader.readFromResources(null));
  }

  @Test
  void readFromResourcesExceptionFileNotFound() {
    assertThrows(AppException.class, () -> ResourceReader.readFromResources("non-existing-file"));
  }
}
