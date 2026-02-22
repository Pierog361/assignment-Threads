package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.board.CellOcupiedException;
import tymoteusz.kunicki.board.FigureDoseNotExist;

import java.util.List;
import java.util.Random;

public class Bulldozer extends Figure {

    public Bulldozer(Board board) {
        super(board, FigureType.BULLDOSER);
    }

    @Override
    public void run() {
        isRunning = true;

        for (int i = 0; i < LIFE_TIME; i++) {
            if (!isRunning) break;
            try {
                Thread.sleep(1000);
                doRandomAction();

                if (destinedToDie) die();

            } catch (InterruptedException | IndexOutOfBoundsException | FigureDoseNotExist e) {
                break;
            }
        }

        die();
    }

    @Override
    public void goForward(int direction) throws FigureDoseNotExist {
        if (!board.moveFigure(this, direction)) {
            pushFigure();
            super.goForward(direction);
        }
    }

    private void doRandomAction() throws FigureDoseNotExist {
        switch (random.nextInt(0, 3)) {
            case 0 -> goForward(direction);
            case 1 -> turnRight();
            case 2 -> turnLeft();
        }
    }

    private void pushFigure() throws FigureDoseNotExist {
        List<Figure> figuresInRange = board.getLoockedAtFigiures(this, direction, 2);
        if (figuresInRange.size() >= 2) return;
        figuresInRange.getFirst().goForward(direction);
    }
}
