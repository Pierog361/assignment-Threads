package tymoteusz.kunicki.entities.figures;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.board.CellOcupiedException;

import java.util.Random;
import java.util.UUID;

public abstract class Figure implements Runnable {

    public final static int NRTH = 0, NRTHEAST = 1, EAST = 2, SOTHEAST = 3, SOTH = 4, SOTHWEST = 5, WEST = 6, NRTHWEST = 7;

    public enum FigureType {BULLDOSER, SEARCHLIGHT, SHOOTER, ARROW}

    Random random = new Random();

    protected static final int LIFE_TIME = 100;
    protected boolean isRunning, hasDied, destinedToDie;
    protected int direction = 0;
    protected Board board;
    private FigureType figureType;

    public final String id;

    public Figure(Board board, FigureType figureType) {
        this.board = board;
        this.figureType = figureType;
        this.hasDied = false;
        this.destinedToDie = false;
        id = UUID.randomUUID().toString();
    }

    public void turnLeft() {
        if (direction != NRTH) {
            direction--;
        } else direction = 6;
    }

    public void turnRight() {
        if (direction != NRTHWEST) {
            direction++;
        } else direction = 0;
    }

    public void kill() {
        destinedToDie = true;
    }

    protected void die() {
        if (hasDied) return;
        Thread.yield();
        board.removeFigure(this);
        isRunning = false;
        hasDied = true;

        IO.println(figureType + " has died!");

    }

    public void goForward(int direction) throws CellOcupiedException {
        board.moveFigure(this, direction);
    }

    public FigureType getFigureType() {
        return this.figureType;
    }
}
