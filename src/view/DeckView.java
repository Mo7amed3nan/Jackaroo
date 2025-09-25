package view;

import java.util.ArrayList;

import model.card.Card;
import model.card.Deck;
import engine.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DeckView  {
    private static int CARD_COUNT = 7;  
    private static final double X_OFFSET = 2.5;  
    private static final double Y_OFFSET = 3.0; 

    public static void show() {
        ArrayList<Card> deck = Deck.cardsPool;
        int cardsToShow = Math.min(CARD_COUNT, deck.size());

        View.deck.getChildren().clear();

        for (int i = cardsToShow - 1; i >= 0; i--) {
            Card card = deck.get(i);
            ImageView cv = new ImageView(new Image("/cards/back.png"));

            cv.setTranslateX(i * X_OFFSET);
            cv.setTranslateY(-i * Y_OFFSET);
            
            // cv.getStyleClass().add("deck-card");
            View.deck.getChildren().add(cv);
            cv.setLayoutX(1650);
            cv.setLayoutY(900);
            cv.setFitWidth(100);
            cv.setPreserveRatio(true);
        }
    }

}