package assessment20230825.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Functional interface for processing ResultSet */
@FunctionalInterface
public interface ResultSetProcessor {
  void process(ResultSet resultSet) throws SQLException;
}
