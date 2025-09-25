package view;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class FieldShortcut {
    public static void field( Scene scene, Stage primaryStage) {

		scene.setOnKeyPressed(k -> {
			KeyCode code = k.getCode();

			switch (code) {
			case W:
				if(View.game.getPlayers().get(2).getMarbles().size() == 0){
					ShowMessage.show(primaryStage, "There is no marble to field !");
					return;
				}
				try {
					View.game.getBoard().sendToBase(
							View.game.getPlayers().get(2).getMarbles().get(0));
					View.game.getPlayers().get(2).getMarbles().remove(0);
				} catch (Exception e) {
					ShowMessage.show(primaryStage, e.getMessage());
				}
				UpdateCells.update();
				ActOnMarble.selectMarble(primaryStage);
				break;

			case A:
				if(View.game.getPlayers().get(1).getMarbles().size() == 0){
					ShowMessage.show(primaryStage, "There is no marble to field !");
					return;
				}
				try {
					View.game.getBoard().sendToBase(
							View.game.getPlayers().get(1).getMarbles().get(0));
					View.game.getPlayers().get(1).getMarbles().remove(0);
				} catch (Exception e) {
					ShowMessage.show(primaryStage, e.getMessage());
				}
				UpdateCells.update();
				ActOnMarble.selectMarble(primaryStage);
				break;

			case D:
				if(View.game.getPlayers().get(3).getMarbles().size() == 0){
					ShowMessage.show(primaryStage, "There is no marble to field !");
					return;
				}

				try {
					View.game.getBoard().sendToBase(
							View.game.getPlayers().get(3).getMarbles().get(0));
					View.game.getPlayers().get(3).getMarbles().remove(0);
				} catch (Exception e) {
					ShowMessage.show(primaryStage, e.getMessage());
				}
				UpdateCells.update();
				ActOnMarble.selectMarble(primaryStage);
				break;

			case S:
				if(View.game.getPlayers().get(0).getMarbles().size() == 0){
					ShowMessage.show(primaryStage, "There is no marble to field !");
					return;
				}
				try {
					View.game.getBoard().sendToBase(
							View.game.getPlayers().get(0).getMarbles().get(0));
					View.game.getPlayers().get(0).getMarbles().remove(0);
				} catch (Exception e) {
					ShowMessage.show(primaryStage, e.getMessage());
				}
				UpdateCells.update();
				ActOnMarble.selectMarble(primaryStage);
				break;
				
				default: break;
			}

		});
    }
}
