package tymoteusz.kunicki.board;

public class FigureDoseNotExist extends RuntimeException {
    public FigureDoseNotExist(String message) {
        super(message);
    }
}
