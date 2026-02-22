package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.board.FigureDoseNotExist;
import tymoteusz.kunicki.entities.creator.Creator;

public class Shooter extends Figure {

    private static final int RANGE = 3;

    public Shooter(Board board) {
        super(board, FigureType.SHOOTER );
    }


    @Override
    public void run() {
        isRunning = true;

        for (int i = 0; i < LIFE_TIME; i++) {
            if (!isRunning) break;
            try {
                Thread.sleep(1000);
                shoot();
                doRandomAction();

                if(destinedToDie) die();

            } catch (InterruptedException | IndexOutOfBoundsException | FigureDoseNotExist e) {
                break;
            }
        }

        die();
    }

    private void doRandomAction() throws FigureDoseNotExist {
        switch (random.nextInt(0, 3)) {
            case 0 -> goForward(direction);
            case 1 -> turnRight();
            case 2 -> turnLeft();
        }
    }

    private void shoot() {
        if(board.getLoockedAtFigiures(this, direction, RANGE).isEmpty()) return;
        Creator.spawnArrow(board,board.getNextLocation(this,direction,1),direction);
    }
}
