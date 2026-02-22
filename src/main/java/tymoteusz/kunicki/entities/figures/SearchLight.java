package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.board.FigureDoseNotExist;
import tymoteusz.kunicki.entities.creator.Creator;

public class SearchLight extends Figure {

    private static final int MAX_TRESURE = 3;
    private int tresureFound = 0;

    public SearchLight(Board borad) {
        super(borad, FigureType.SEARCHLIGHT);
    }

    @Override
    public void run() {
        isRunning = true;

        for (int i = 0; i < LIFE_TIME; i++) {
            if (!isRunning) break;
            try {
                Thread.sleep(1000);
                searchForTrasure();
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

    private void searchForTrasure() throws FigureDoseNotExist {
        if (board.getTresure(this)) {
            tresureFound++;
            IO.println("Tresure found (" + board.getCurrentLocation(this) + ")");
        }
        if (tresureFound >= MAX_TRESURE) becomeAShooter();

    }

    private void becomeAShooter() {
        Creator.transformSearchLightToShooter(board, this);
    }

}
