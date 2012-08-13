package org.hardy.monsterismus.statements.conditionals;

/**
 * Conditional logic enumerator to help simplify if statement conditionals.
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public enum Conditional {
    FOOD_ABOVE(0, "food above", new Evaluator() {
        public boolean eval() {
            return CheckFood.checkY(-1);
        }
    }), FOOD_BELOW(1, "food below", new Evaluator() {
        public boolean eval() {
            return CheckFood.checkY(1);
        }
    }), FOOD_LEFT(2, "food left", new Evaluator() {
        public boolean eval() {
            return CheckFood.checkX(-1);
        }
    }), FOOD_RIGHT(3, "food right", new Evaluator() {
        public boolean eval() {
            return CheckFood.checkX(1);
        }
    }), WALL_ABOVE(4, "wall above", new Evaluator() {
        public boolean eval() {
            return CheckWall.checkY(-1);
        }
    }), WALL_BELOW(5, "wall below", new Evaluator() {
        public boolean eval() {
            return CheckWall.checkY(1);
        }
    }), WALL_LEFT(6, "wall left", new Evaluator() {
        public boolean eval() {
            return CheckWall.checkX(-1);
        }
    }), WALL_RIGHT(7, "wall right", new Evaluator() {
        public boolean eval() {
            return CheckWall.checkX(1);
        }
    });

    private int _value;
    private String _label;
    private Evaluator _eval;

    /**
     * Creates a new conditional enumeration.
     * 
     * @param $value
     *            The value of the conditional.
     * @param $label
     *            The label that can be applied to the conditional.
     * @param $eval
     *            The statement that can be evaluated to a boolean result.
     */
    Conditional(final int $value, final String $label, final Evaluator $eval) {
        _value = $value;
        _label = $label;
        _eval = $eval;
    }

    /**
     * Gets the enumeration provided the given value.
     * 
     * @param $value
     *            The value of the conditional to get.
     * @return the conditional enumeration from the provided value.
     */
    private static Conditional get(final int $value) {
        switch ($value) {
        case 0:
            return FOOD_ABOVE;
        case 1:
            return FOOD_BELOW;
        case 2:
            return FOOD_LEFT;
        case 3:
            return FOOD_RIGHT;
        case 4:
            return WALL_ABOVE;
        case 5:
            return WALL_BELOW;
        case 6:
            return WALL_LEFT;
        case 7:
            return WALL_RIGHT;
        default:
            return FOOD_ABOVE;
        }
    }

    /**
     * Gets the label of the conditional.
     * 
     * @return the label.
     */
    public String getLabel() {
        return _label;
    }

    /**
     * Evaluates the conditional.
     * 
     * @return true if the conditional is true, false otherwise.
     */
    public boolean evaluate() {
        return _eval.eval();
    }

    /**
     * Gets the next conditional, which is used for iteration.
     * 
     * @return the next conditional.
     */
    public Conditional next() {
        return get((_value + 1) % 8);
    }
}
