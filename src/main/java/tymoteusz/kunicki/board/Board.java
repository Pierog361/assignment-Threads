package tymoteusz.kunicki.board;

import tymoteusz.kunicki.entities.figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int amountOfActivFigures, amountOfActivTresures;

    private final static int WIDTH = 10, HIGHT = 10;
    public List<Cell> cells = new ArrayList<>(WIDTH * HIGHT);

    {
        for (int i = 0; i < WIDTH * HIGHT; i++) {
            cells.add(new Cell());
        }
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        int row = -1;
        for (Cell cell : cells) {
            if (row++ % 10 == 9) boardString.append("\n");

            if (cell.hasTresure()) boardString.append("t");
            else boardString.append(" ");


            if (!cell.hasFigure()) {
                boardString.append("0 ");
                continue;
            }

            switch (cell.getfigure().getFigureType()) {
                case SHOOTER -> boardString.append("S ");
                case BULLDOSER -> boardString.append("B ");
                case SEARCHLIGHT -> boardString.append("L ");
                case ARROW -> boardString.append(". ");
                default -> boardString.append("0 ");
            }
        }
        return boardString.toString();
    }

    public synchronized void addFigure(int location, Figure figure) {
        cells.get(location).addFigure(figure);
        amountOfActivFigures++;
    }

    public synchronized void removeFigure(Figure Figure) {
        cells.get(getCurrentLocation(Figure)).removeFigure();
        amountOfActivFigures--;
    }

    public synchronized void addTresure(int location) {
        cells.get(location).addTresure();
        amountOfActivTresures++;
    }

    public synchronized void removeTresure(int location) {
        cells.get(location).removeTresure();
        amountOfActivFigures--;
    }

    public synchronized int getAmountOfActivFigures() {
        return amountOfActivFigures;
    }

    public synchronized int getAmountOfActivTresures() {
        return amountOfActivTresures;
    }

    public synchronized List<Integer> getFreeCellsIndexList() {
        return Cell.getFreeCellsIndexList(cells);
    }

    public synchronized int getCurrentLocation(Figure figure) throws FigureDoseNotExist {
        return Cell.getIndex(cells, figure);
    }

    public int getNextLocation(Figure figure, int direction, int offset) throws FigureDoseNotExist {
        int previousLoacation = getCurrentLocation(figure);
        switch (direction) {
            case Figure.NRTH -> {
                return previousLoacation - offset * WIDTH;
            }
            case Figure.NRTHEAST -> {
                return previousLoacation - offset * WIDTH + offset;
            }
            case Figure.EAST -> {
                return previousLoacation + offset;
            }
            case Figure.SOTHEAST -> {
                return previousLoacation + offset + offset * WIDTH;
            }
            case Figure.SOTH -> {
                return previousLoacation + WIDTH * offset;
            }
            case Figure.SOTHWEST -> {
                return previousLoacation + offset * WIDTH - offset;
            }
            case Figure.WEST -> {
                return previousLoacation - offset;
            }
            case Figure.NRTHWEST -> {
                return previousLoacation - offset * WIDTH - offset;
            }
        }

        return previousLoacation;
    }

    public synchronized boolean moveFigure(Figure figure, int direction) throws FigureDoseNotExist {
        try {
            return setLocation(figure, getNextLocation(figure, direction, 1));
        } catch (IndexOutOfBoundsException e) {
            kill(figure);
            return true;
        }
    }

    public synchronized boolean setLocation(Figure figure, int location) throws IndexOutOfBoundsException {
        if (cells.get(location).hasFigure()) return false;
        removeFigure(figure);
        addFigure(location, figure);
        return true;
    }

    public synchronized List<Figure> getLoockedAtFigiures(Figure figure, int direction, int range) throws FigureDoseNotExist {

        List<Figure> newFigures = new ArrayList<>();
        for (int i = 1; i <= range; i++) {
            try {
                Cell newCell = cells.get(getNextLocation(figure, direction, i));
                if (newCell.hasFigure()) if (newCell.getfigure().getFigureType() != Figure.FigureType.ARROW)
                    newFigures.add(newCell.getfigure());
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return newFigures;
    }

    public synchronized boolean getTresure(Figure figure) throws FigureDoseNotExist{
        Cell currentCell = cells.get(getCurrentLocation(figure));
        if (!currentCell.hasTresure()) return false;

        removeTresure(getCurrentLocation(figure));
        return true;
    }

    public synchronized void kill(Figure figure) {
        figure.kill();
    }
}
