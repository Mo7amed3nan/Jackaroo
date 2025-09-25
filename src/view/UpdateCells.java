package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import engine.board.Board;
import engine.board.Cell;
import engine.board.SafeZone;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import model.player.Marble;
import model.player.Player;

public class UpdateCells {
    public static ArrayList<Cell> trackCells;
    public static ArrayList<SafeZone> safeZones;
    public static ArrayList<ArrayList<Marble>> homeZones;

    // Map to store previous positions of marbles by marble ID (or object hash)
    public static Map<Marble, Pair> previousMarblePositions = new HashMap<>();

    public static void update() {
        View.marblesPane.getChildren().clear();

        trackCells = View.game.getBoard().getTrack();
        safeZones = View.game.getBoard().getSafeZones();
        homeZones = new ArrayList<>();
        ArrayList<Player> players = View.game.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            homeZones.add(players.get(i).getMarbles());
        }

        for (int i = 0; i < homeZones.size(); i++) {
            for (int j = 0; j < homeZones.get(i).size(); j++) {
                Marble m = homeZones.get(i).get(j);
                if (m == null) continue;

                Pair position = View.homeindexToPoint.get(i).get(j);
                updateMarblePosition(m, position);
                m.cir.setEffect(null);
                m.cir.setScaleX(1);
                m.cir.setScaleY(1);
            }
        }

        for (int i = 0; i < trackCells.size(); i++) {
            Marble m = trackCells.get(i).getMarble();
            if (m == null) continue;

            Pair position = View.indexToPoint.get(i);
            updateMarblePosition(m, position);
        }

        for (int i = 0; i < safeZones.size(); i++) {
            for (int j = 0; j < safeZones.get(i).getCells().size(); j++) {
                Marble m = safeZones.get(i).getCells().get(j).getMarble();
                if (m == null) continue;

                Pair position = View.safeZoneIndexToPoint.get(i).get(j);
                updateMarblePosition(m, position);
                m.cir.setEffect(null);
                m.cir.setScaleX(1);
                m.cir.setScaleY(1);
            }
        }
    }

    private static void updateMarblePosition(Marble marble, Pair newPos) {
        Circle circle = marble.cir;
        Pair oldPos = previousMarblePositions.get(marble);

        if (oldPos != null && (oldPos.x != newPos.x || oldPos.y != newPos.y)) {
            double dx = newPos.x - oldPos.x;
            double dy = newPos.y - oldPos.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double speed = 0.17;
            double minDuration = 100;
            double duration = Math.max(distance / speed, minDuration);

            // Create a subtle arc path
            Path path = new Path();
            path.getElements().add(new MoveTo(oldPos.x, oldPos.y));

            // Control point slightly off center for a gentle arc
            double controlX = (oldPos.x + newPos.x) / 2 + dy * 0.2; // Offset perpendicular to the line
            double controlY = (oldPos.y + newPos.y) / 2 - dx * 0.2;

            path.getElements().add(new QuadCurveTo(controlX, controlY, newPos.x, newPos.y));

            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(duration));
            pathTransition.setPath(path);
            pathTransition.setNode(circle);
            pathTransition.setOrientation(PathTransition.OrientationType.NONE);
            pathTransition.setInterpolator(Interpolator.EASE_BOTH);

            pathTransition.setOnFinished(e -> {
                circle.setTranslateX(0);
                circle.setTranslateY(0);
                circle.setCenterX(newPos.x);
                circle.setCenterY(newPos.y);
            });

            pathTransition.play();
        } else {
            circle.setCenterX(newPos.x);
            circle.setCenterY(newPos.y);
        }

        previousMarblePositions.put(marble, new Pair(newPos.x, newPos.y));
        View.marblesPane.getChildren().add(circle);
    }

}
