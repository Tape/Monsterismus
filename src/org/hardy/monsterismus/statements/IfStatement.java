package org.hardy.monsterismus.statements;

import org.hardy.monsterismus.api.Nestable;
import org.hardy.monsterismus.statements.conditionals.Conditional;

import android.util.Log;

import processing.core.PGraphics;
import processing.core.PImage;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class IfStatement extends ProgrammingStatement {
    private static final int FILL_COLOR = 0xFFFF0000;
    private static final int BASE_WIDTH = 300;
    private static final int BASE_HEIGHT = 90;
    public static PImage image;
    private static final String STATEMENT = "Statement";

    public PImage getImage() {
        return image;
    }

    public StatementInstance spawnInstance() {
        Log.i(STATEMENT, "new if else statement");
        return new IfStatementInstance();
    }

    protected class IfStatementInstance extends StatementInstance implements Nestable {
        private Conditional _conditional = Conditional.FOOD_ABOVE;
        private StatementInstance _consequent;
        private StatementInstance _alternative;
        private StatementInstance _evaluated;
        private String _label = "If " + _conditional.getLabel() + ", then ";
        private boolean _is_done = true, _is_executed = false;

        public void update(final float $dt) {
        }

        public void draw(final PGraphics $graphics) {
            $graphics.fill(FILL_COLOR);
            $graphics.rect(_pos.x, _pos.y, BASE_WIDTH, getHeight());
            $graphics.fill(0xFFFFFFFF);
            $graphics.text(_label, _pos.x + 10, _pos.y + 16);

            if (_consequent != null) {
                $graphics.text("else", _pos.x + 10, _pos.y + _consequent.getHeight() + 45);
                _consequent.draw($graphics);
            } else
                $graphics.text("else", _pos.x + 10, _pos.y + 60);

            if (_alternative != null) {
                _alternative.draw($graphics);
            }
        }

        public void setPos(final float $x, final float $y) {
            super.setPos($x, $y);

            if (_consequent != null)
                _consequent.setPos(_pos.x + 5, _pos.y + 25);

            if (_alternative != null)
                if (_consequent != null)
                    _alternative.setPos(_pos.x + 5, _pos.y + _consequent.getHeight() + 55);
                else
                    _alternative.setPos(_pos.x + 5, _pos.y + 70);
        }

        public int getHeight() {
            int height = BASE_HEIGHT;

            if (_consequent != null) {
                height += _consequent.getHeight();
            }

            if (_alternative != null)
                height += _alternative.getHeight() - (_consequent != null ? 27 : 12);

            return height;
        }

        public int getWidth() {
            return BASE_WIDTH;
        }

        public void removeAllInstances(final StatementInstance $instance) {
            if (_consequent == $instance)
                _consequent = null;
            else if (_alternative == $instance)
                _alternative = null;
        }

        public StatementInstance instanceUnder(final float $x, final float $y) {
            if ($x > _pos.x && $y > _pos.y && $x < _pos.x + BASE_WIDTH && $y < _pos.y + getHeight()) {
                StatementInstance under = null;
                if (_consequent != null)
                    under = _consequent.instanceUnder($x, $y);
                if (under == null && _alternative != null)
                    under = _alternative.instanceUnder($x, $y);

                return under == null ? this : under;
            }
            return null;
        }

        public void addChild(StatementInstance $instance) {
            $instance.setParent(this);
            int offset = 50 + (_consequent != null ? _consequent.getHeight() - 5 : 0);

            if ($instance.getPos().y - _pos.y > offset)
                _alternative = $instance;
            else
                _consequent = $instance;
        }

        public void handleClick() {
            // Increment the conditional.
            _conditional = _conditional.next();
            _label = "If " + _conditional.getLabel() + ", then";
        }

        public boolean isDone() {
            return _is_done;
        }

        public boolean executed() {
            return _is_executed;
        }

        public void eval() {
            // If the action is done, that means we are just now executing it
            // for the first time.
            if (_is_done) {
                // Evaluate the conditional.
                _evaluated = _conditional.evaluate() ? _consequent : _alternative;

                // If the action exists, then begin to execute.
                if (_evaluated != null) {
                    _is_done = false;
                    _evaluated.eval();
                }
                // Otherwise notify that the execution is complete.
                else
                    _is_done = _is_executed = true;
            } else {
                // Action finished? reset.
                if (_evaluated.isDone()) {
                    _evaluated.reset();
                    _is_done = _is_executed = true;
                }
                // Otherwise continue to evaluate.
                else
                    _evaluated.eval();
            }
        }

        public void reset() {
            _is_done = true;
            _is_executed = false;

            if (_consequent != null)
                _consequent.reset();
        }
    }
}
