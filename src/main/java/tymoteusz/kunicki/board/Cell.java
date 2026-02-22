package tymoteusz.kunicki.board;

import tymoteusz.kunicki.entities.figures.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private Figure figure;
    private boolean hasTresure, hasFigure;

    public static List<Integer> getFreeCellsIndexList(List<Cell> cells) {
        List<Integer> freeCellIndexies = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            if (!cells.get(i).hasFigure()) freeCellIndexies.add(i);
        }
        return freeCellIndexies;
    }

    public static int getIndex(List<Cell> cells, Figure figure) throws FigureDoseNotExist{

        for (int i = 0; i < cells.size(); i++) {
            if (!cells.get(i).hasFigure()) continue;
            if (Objects.equals(cells.get(i).getfigure().id, figure.id)) return i;
        }
        throw new FigureDoseNotExist("Figure " + figure.getFigureType() + " not found!" );
    }

    // Tresure methods
    public boolean hasTresure() {
        return hasTresure;
    }

    public void addTresure() {
        hasTresure = true;
    }

    public void removeTresure() {
        hasTresure = false;
    }

    // Figure methods
    public boolean hasFigure() {
        return hasFigure;
    }

    public Figure getfigure() {
        if (!hasFigure) return null;
        else return figure;
    }

    public void addFigure(Figure figure) {
        hasFigure = true;
        this.figure = figure;
    }

    public void removeFigure() {
        hasFigure = false;
        this.figure = null;
    }


}
