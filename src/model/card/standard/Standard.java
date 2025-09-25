package model.card.standard;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.card.Card;
import model.player.Marble;

public class Standard extends Card {
    private final int rank;
    private final Suit suit;

    public Standard(String name, String description, int rank, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, boardManager, gameManager);
        this.rank = rank;
        this.suit = suit;
        
       
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException{
        this.boardManager.moveBy(marbles.get(0), rank, false);
    }
    @Override
    public String getImageCode(){
    	String code;
    	switch (this.getRank()){
	    	case 1: code = "a";break;
	    	case 11: code = "j";break;
	    	case 12: code = "q";break;
	    	case 13: code = "k";break;
	    	default: code = this.getRank()+"";
    	}
    	String suit= "";
    	switch (this.getSuit()){
	    	case SPADE: suit = "s";break;
	    	case CLUB: suit = "c";break;
	    	case HEART: suit = "h";break;
	    	case DIAMOND: suit = "d";break;
    	
    	}
    	return code+suit;
    }

}
