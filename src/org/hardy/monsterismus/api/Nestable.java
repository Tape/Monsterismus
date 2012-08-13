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
    public void addChild(StatementInstance $instance);

    public void removeAllInstances(StatementInstance $instance);
}
