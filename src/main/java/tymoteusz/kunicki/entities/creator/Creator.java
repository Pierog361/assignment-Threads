package tymoteusz.kunicki.entities.creator;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.entities.figures.*;

import java.util.Random;

public class Creator implements Runnable {

    private static final int MAX_FIGURES = 10, MAX_TRESURES = 50;
    private final Board board;
    private final Random random = new Random();

    private Boolean isRunning;

    public Creator(Board bord) {
        this.board = bord;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {

            generatAFigure();
            generateAtresure();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    }

    private void generatAFigure() {

        if (board.getAmountOfActivFigures() >= MAX_FIGURES) return;

        int newLocation = board.getFreeCellsIndexList().get(random.nextInt(0, board.getFreeCellsIndexList().size()));
        int newFigureType = random.nextInt(0, 3);

        Figure newFigure = new Bulldozer(board);
        switch (newFigureType) {
            case 0 -> newFigure = new Bulldozer(board);
            case 1 -> newFigure = new Shooter(board);
            case 2 -> newFigure = new SearchLight(board);
            default -> new Bulldozer(board);
        }

        board.addFigure(newLocation, newFigure);
        IO.println(newFigure.getFigureType() + " was born! (" + newLocation + ")");

        startAGeneratedFigure(newFigure);

    }

    private void generateAtresure() {

        if (board.getAmountOfActivTresures() >= MAX_TRESURES) return;

        int newLocation = board.getFreeCellsIndexList().get(random.nextInt(0, board.getFreeCellsIndexList().size()));
        board.addTresure(newLocation);
        IO.println("TRESURE was added! (" + newLocation + ")");
    }

    private void startAGeneratedFigure(Figure figure) {

        Thread figureThread = new Thread(figure);
        figureThread.start();

    }

    public synchronized static void transformSearchLightToShooter(Board board, SearchLight searchLight) {
        int newFigureLocation = board.getCurrentLocation(searchLight);
        Figure newFigure = new Shooter(board);

        searchLight.kill();

        board.addFigure(newFigureLocation, newFigure);
        IO.println("Searchlight transformed (" + newFigureLocation + ")");

        Creator creator = new Creator(board);
        creator.startAGeneratedFigure(newFigure);

    }

    public synchronized static void spawnArrow(Board board, int location, int direction) {
        Figure newFigure = new Arrow(board, direction);

        board.addFigure(location, newFigure);
        IO.println("ARROW is shot (" + location + ")");

        Creator creator = new Creator(board);
        creator.startAGeneratedFigure(newFigure);

    }
}
