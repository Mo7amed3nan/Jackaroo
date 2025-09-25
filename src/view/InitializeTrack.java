package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class InitializeTrack {

    public static final String STYLE = "board-cell-circle";
    public static final Color CELL_STROKE_COLOR = Color.DARKGOLDENROD;
    public static final Color CELL_COLOR = Color.BLACK;

    private static Circle createCell(int x, int y) {
        Circle cell = new Circle(View.cellR);
        cell.setFill(CELL_COLOR);
        cell.setStroke(CELL_STROKE_COLOR);
        cell.setCenterX(x);
        cell.setCenterY(y);
        View.cellAccumulator.add(cell);
        View.indexToPoint.add(new Pair((int) x, (int) y));
        return cell;
    }

    private static void drawPath(int steps, int dx, int dy, int[] pos) {
        for (int i = 0; i < steps; i++) {
            pos[0] += dx;
            pos[1] += dy;
            createCell(pos[0], pos[1]);
        }
    }

    public static void initializeTrackCells(int startX, int startY, int step, int r) {
        createCell(startX, startY);
        int diagStep = (int) ((1 / Math.sqrt(2)) * step);
        int[] pos = {startX, startY};

        drawPath(8, -diagStep, -diagStep, pos);
        drawPath(5, -step, 0, pos);
        drawPath(8, -diagStep, diagStep, pos);
        drawPath(4, -diagStep, -diagStep, pos);
        drawPath(8, diagStep, -diagStep, pos);
        drawPath(5, 0, -step, pos);
        drawPath(8, -diagStep, -diagStep, pos);
        drawPath(4, diagStep, -diagStep, pos);
        drawPath(8, diagStep, diagStep, pos);
        drawPath(5, step, 0, pos);
        drawPath(8, diagStep, -diagStep, pos);
        drawPath(4, diagStep, diagStep, pos);
        drawPath(8, -diagStep, diagStep, pos);
        drawPath(5, 0, step, pos);
        drawPath(8, diagStep, diagStep, pos);
        drawPath(3, -diagStep, diagStep, pos);
    }

    public static void initializeSafeZone(int step, int r, int w) {
        for (int i = 0; i < 4; i++) View.safeZoneIndexToPoint.add(new ArrayList<>());
        int diagStep = (int) ((1 / Math.sqrt(2)) * step);
        int[] positions = {23, 48, 73, 98};
        int[][] directions = {
            {diagStep, -diagStep}, {diagStep, diagStep}, {-diagStep, diagStep}, {-diagStep, -diagStep}
        };

        for (int i = 0; i < 4; i++) {
            Pair p = View.indexToPoint.get(positions[i]);
            int x = p.x, y = p.y;
            for (int j = 0; j < 4; j++) {
                x += directions[i][0];
                y += directions[i][1];
                View.safeZoneIndexToPoint.get((i + 1) % 4).add(new Pair(x, y));
                Circle cell = new Circle(View.cellR);
                cell.getStyleClass().add(STYLE);
                cell.setStroke(View.colOrder.get((i + 1) % 4));
                cell.setStrokeWidth(w);
                cell.setCenterX(x);
                cell.setCenterY(y);
                View.cellAccumulator.add(cell);
            }
        }
    }

    public static void initializeHomecells(int step, int r, int w) {
        for (int i = 0; i < 4; i++) View.homeindexToPoint.add(new ArrayList<>());

        int[] indexes = {2, 27, 52, 77};
        int[][] offsets = {{-80, 0}, {0, -100}, {80, 0}, {0, 50}};

        for (int i = 0; i < 4; i++) {
            Pair p = View.indexToPoint.get(indexes[i]);
            int x = p.x + offsets[i][0];
            int y = p.y + offsets[i][1];

            int[][] pattern = {
                {0, 0}, {0, 2 * step}, {step, step}, {-step, step}
            };
            
            boolean f = false;
            
            for (int[] offset : pattern) {
            	int extraX = 0; int extraY = 0;
            	if(i == 1){ extraX = -step; extraY = -step; }
            	if(i == 2){ extraX = step; extraY = -step; }
            	if(i == 3){ extraX += step; extraY += step; }
                int cx = x + offset[0] + extraX;
                int cy = y + offset[1] + extraY;
                Circle cell = new Circle(View.cellR);
                cell.getStyleClass().add(STYLE);
                cell.setCenterX(cx);
                cell.setCenterY(cy);
                cell.setStroke(View.colOrder.get(i));
                cell.setStrokeWidth(w);
                View.homeindexToPoint.get(i).add(new Pair(cx, cy));
                View.cellAccumulator.add(cell);
                if(!f){
                	f = true;
                	View.homeCellsPos[i] = new Pair(cx, cy);
                }
            }
        }
    }
}