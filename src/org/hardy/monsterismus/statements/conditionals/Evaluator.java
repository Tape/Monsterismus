package org.hardy.monsterismus.statements.conditionals;

/**
 * Helper class to wrap a statement to be evaluated.
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Evaluator {
    /**
     * Evaluates the nested statement.
     * 
     * @return true if successful, false if not.
     */
    public boolean eval();
}
