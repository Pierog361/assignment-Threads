package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.board.CellOcupiedException;
import tymoteusz.kunicki.board.FigureDoseNotExist;

public class Arrow extends Figure {

    public final int ARROW_LIFE_TIME = 3;

    public Arrow(Board board, int direction) {
        super(board, FigureType.ARROW);
        this.direction = direction;
    }

    @Override
    public void run() {

        isRunning = true;

        for (int i = 0; i < ARROW_LIFE_TIME; i++) {
            if (!isRunning) break;
            try {
                Thread.sleep(1000);
                goForward(direction);

                if(destinedToDie) die();

            } catch (InterruptedException | IndexOutOfBoundsException | FigureDoseNotExist e) {
                break;
            }
        }

        die();
    }

    @Override
    public void goForward(int direction)  {
        if (!board.moveFigure(this, direction)) {
            handleColision();
            die();
        }
    }

    private void handleColision() {
       board.kill(board.getLoockedAtFigiures(this, direction, 1).getFirst());
    }
}
