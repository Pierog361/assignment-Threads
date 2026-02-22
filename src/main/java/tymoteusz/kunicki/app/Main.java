package tymoteusz.kunicki.app;

import tymoteusz.kunicki.board.Board;
import tymoteusz.kunicki.exceptions.FigureDoseNotExist;
import tymoteusz.kunicki.entities.creator.Creator;
import tymoteusz.kunicki.gui.GraphicalThreadVisualisation;

public class Main {

    private Board board = new Board();

    static void main() throws RuntimeException, FigureDoseNotExist {

        Main main = new Main();

        // Initiating GUI Thread
        Thread GUIThrred = new Thread(new GraphicalThreadVisualisation(main.board), "GUIThread");
        GUIThrred.setDaemon(true);
        GUIThrred.start();

        // Initiating Creator Thread
        Thread CreatorThread = new Thread(new Creator(main.board), "CreatorThread");
        CreatorThread.setDaemon(true);
        CreatorThread.start();

    }
}
