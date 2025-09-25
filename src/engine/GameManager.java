package engine;

import engine.board.Cell;
import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import model.Colour;
import model.player.Marble;

public interface GameManager {
	public void sendHome(Marble marble);
    public void fieldMarble() throws CannotFieldException, IllegalDestroyException;
    public void discardCard(Colour colour) throws CannotDiscardException;
    public void discardCard() throws CannotDiscardException;
    public Colour getActivePlayerColour();
    public Colour getNextPlayerColour();
	public void notifyMoveStep(Marble marble, Cell from, Cell to);
}
