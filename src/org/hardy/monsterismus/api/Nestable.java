package org.hardy.monsterismus.api;

import org.hardy.monsterismus.statements.StatementInstance;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Nestable {
    /**
     * Adds a child StatementInstance to this nestable.
     * 
     * @param $instance
     *            The instance to be added.
     */
    public void addChild(StatementInstance $instance);

    /**
     * Removes all instances of the StatementInstance from this nestable.
     * 
     * @param $instance
     *            The instance to be removed.
     */
    public void removeAllInstances(StatementInstance $instance);
}
