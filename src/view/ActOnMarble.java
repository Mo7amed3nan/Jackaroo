package view;

import java.util.ArrayList;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.player.Marble;
import model.player.Player;

public class ActOnMarble {
	public static void selectMarble(Stage primaryStage){
		double scale = 1.4;
		for(Marble marble : View.game.getBoard().getActionableMarbles()){
			marble.cir.setOnMouseClicked(e->{
				// view ----
				DropShadow glow = new DropShadow();
		        glow.setColor(Color.valueOf(marble.getColour().toString()).deriveColor(0, 1.0, 1.5, 0.8));
		        glow.setRadius(12); // the radius
		        glow.setSpread(0.5);
		        marble.cir.setEffect(glow);
		        marble.cir.setScaleX(scale);marble.cir.setScaleY(scale);
				Player currPlayer = View.game.getPlayers().get(0);
				
				try {
					currPlayer.selectMarble(marble);
					
					
				} catch (Exception e1) {
					ShowMessage.show(primaryStage, e1.getMessage());
					currPlayer.deselectAll();
					
					// reset view ----
					for(Marble x : View.game.getBoard().getActionableMarbles()){x.cir.setEffect(null); x.cir.setScaleX(1); x.cir.setScaleY(1);}
					for(Player p:View.game.getPlayers()){
		            	for(Marble m : p.getMarbles()){
		            		m.cir.setEffect(null);
		            		m.cir.setScaleX(1);
		            		m.cir.setScaleY(1);
		            	}
		            }
				}
			});
		}
	}

}