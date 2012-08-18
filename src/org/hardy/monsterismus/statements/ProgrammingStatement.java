package org.hardy.monsterismus.statements;

import processing.core.PImage;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class ProgrammingStatement {

    /**
     * Spawns an instance of the statement type.
     * 
     * @return the spawned instance.
     */
    public abstract StatementInstance spawnInstance();

    public abstract PImage getImage();
}
