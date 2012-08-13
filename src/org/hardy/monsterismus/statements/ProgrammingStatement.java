package org.hardy.monsterismus.statements;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class ProgrammingStatement {
  public abstract int getColor();
  /**
   * Spawns an instance of the statement type.
   * @return the spawned instance.
   */
  public abstract StatementInstance spawnInstance();
}

