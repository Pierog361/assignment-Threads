package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.exceptions.FigureDoseNotExist;
import tymoteusz.kunicki.entities.creator.Creator;

public class SearchLight extends Figure {

    private static final int MAX_TREASURE = 3;
    private int treasureFound = 0;

    public SearchLight(Board board) {
        super(board, FigureType.SEARCHLIGHT);
    }

    @Override
    public void run() {
        isRunning = true;

        for (int i = 0; i < LIFE_TIME; i++) {
            if (!isRunning) break;
            try {
                Thread.sleep(1000);
                searchForTreasure();
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

    private void searchForTreasure() throws FigureDoseNotExist {
        if (board.getTresure(this)) {
            treasureFound++;
            IO.println("Tresure found (" + board.getCurrentLocation(this) + ")");
        }
        if (treasureFound >= MAX_TREASURE) becomeAShooter();
    }

    private void becomeAShooter() {
        Creator.transformSearchLightToShooter(board, this);
    }
}
