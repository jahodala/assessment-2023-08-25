package assessment20230825.command;

/** Supported commands with supporting values */
public enum CommandsActionEnum {
  // ADD: base on example: (1, "a1", "Robert")
  // ^\(\s*\d+\s*,\s*"[a-zA-Z0-9]+"\s*,\s*"[a-zA-Z]+"\\s*)$
  ADD("^\\(\\s*(\\d+)\\s*,\\s*\"([a-zA-Z0-9]+)\"\\s*,\\s*\"([a-zA-Z]+)\"\\s*\\)$"),
  DELETE_ALL("DeleteAll"),
  PRINT_ALL("PrintAll"),
  EXIT("EXIT");
  public final String supportValue;

  CommandsActionEnum(String supportValue) {
    this.supportValue = supportValue;
  }
}
