package coyote.spinneret.cfg;

/**
 *
 */
public enum SlotType {
  /**
   * String
   */
  STRING("STR", (short) 3),
  /**
   * Boolean
   */
  BOOLEAN("BOL", (short) 14),
  /**
   * Byte
   */
  BYTE("S8", (short) 4),
  /**
   * Short
   */
  SHORT("S16", (short) 6),
  /**
   * Integer
   */
  INT("S32", (short) 8),
  /**
   * Long
   */
  LONG("S64", (short) 10),
  /**
   * Float
   */
  FLOAT("FLT", (short) 12),
  /**
   * Double
   */
  DOUBLE("DBL", (short) 13),
  /**
   * Date
   */
  DATE("DAT", (short) 15);

  private String name;
  private short type;

  SlotType(final String n, final short t) {
    name = n;
    type = t;
  }

  public static SlotType getSlotTypeByName(final String name) {
    if (name != null) {
      for (final SlotType type : SlotType.values()) {
        if (name.equalsIgnoreCase(type.toString())) {
          return type;
        }
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }


  public short getType() {
    return type;
  }


  @Override
  public String toString() {
    return name;
  }

}
