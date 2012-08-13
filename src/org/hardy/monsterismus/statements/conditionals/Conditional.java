package org.hardy.monsterismus.statements.conditionals;

public enum Conditional {
  FOOD_ABOVE(0, "food above", new Evaluator() {
    public boolean eval() { return CheckFood.checkY(-1); }
  }),
  FOOD_BELOW(1, "food below", new Evaluator() {
    public boolean eval() { return CheckFood.checkY(1); }
  }),
  FOOD_LEFT(2, "food left", new Evaluator() {
    public boolean eval() { return CheckFood.checkX(-1); }
  }),
  FOOD_RIGHT(3, "food right", new Evaluator() {
    public boolean eval() { return CheckFood.checkX(1); }
  }),
  WALL_ABOVE(4, "wall above", new Evaluator() {
    public boolean eval() { return CheckWall.checkY(-1); }
  }),
  WALL_BELOW(5, "wall below", new Evaluator() {
    public boolean eval() { return CheckWall.checkY(1); }
  }),
  WALL_LEFT(6, "wall left", new Evaluator() {
    public boolean eval() { return CheckWall.checkX(-1); }
  }),
  WALL_RIGHT(7, "wall right", new Evaluator() {
    public boolean eval() { return CheckWall.checkX(1); }
  });

  private int _value;
  private String _label;
  private Evaluator _eval;

  Conditional(final int $value, final String $label, final Evaluator $eval)
  {
    _value = $value;
    _label = $label;
    _eval = $eval;
  }

  private static Conditional get(final int $value)
  {
    switch($value)
    {
    case 0: return FOOD_ABOVE;
    case 1: return FOOD_BELOW;
    case 2: return FOOD_LEFT;
    case 3: return FOOD_RIGHT;
    case 4: return WALL_ABOVE;
    case 5: return WALL_BELOW;
    case 6: return WALL_LEFT;
    case 7: return WALL_RIGHT;
    default: return FOOD_ABOVE;
    }
  }

  public String getLabel()
  {
    return _label;
  }

  public boolean evaluate()
  {
    return _eval.eval();
  }

  public Conditional next()
  {
    return get((_value + 1) % 8);
  }
}
