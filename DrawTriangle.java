package q2;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * <p>
 * DrawTriangle: draws an equilateral triangle using a rubber banding technique.
 * </p>
 * <ul>
 * <li>Triangle size and orientation are determined by a mouse drag.</li>
 * <li>Use the original mouse press location as the center</li>
 * <li>The dragged position of the mouse as one of the corners</li>
 * </ul>
 *
 * @author Ying-Ju(Amber) Chen
 * @version 1.0
 */
public class DrawTriangle extends Application {

    /** The contents of the application scene. */
    private Group root;

    /** center point. */
    private Point2D center;
    /** circle to move to first mouse click location. */
    private Circle atCenter = new Circle(0, 0, (2 + 1));

    /**
     * Displays an initially empty scene, waiting for 
     * the user to draw lines with
     * the mouse.
     * 
     * @param primaryStage
     *            a Stage
     */
    public void start(Stage primaryStage) {
        root = new Group(atCenter);
        atCenter.setFill(Color.CYAN);

        final int appWidth = 800;
        final int appHeight = 500;
        Scene scene = new Scene(root, appWidth, appHeight, Color.BLACK);

        scene.setOnMousePressed(this::processMousePress);
        scene.setOnMouseDragged(this::processMouseDrag);

        primaryStage.setTitle("Equilateral Triangle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Adds a new line to the scene when the mouse button is pressed.
     * 
     * @param event
     *            invoked this method
     */
    public void processMousePress(MouseEvent event) {
        root.getChildren().clear();
        center = new Point2D(event.getX(), event.getY());
        atCenter = new Circle(center.getX(), center.getY(), (2 + 1));

        atCenter.setFill(Color.CYAN);
        root.getChildren().add(atCenter);
    }

    /**
     * Updates the end point of the current line as 
     * the mouse is dragged, creating
     * the rubber band effect.
     * 
     * @param event
     *            invoked this method
     */
    public void processMouseDrag(MouseEvent event) {
        root.getChildren().clear();

        double radius = Math
                .sqrt(Math.pow(center.getX() - event.getX(), 2) 
                + Math.pow(center.getY() - event.getY(), 2));

        Point2D one = new Point2D(event.getX(), event.getY());
        double angleOriginal = Math.atan2((event.getY() - center.getY()), 
                (event.getX() - center.getX()));
        double angleSecond = angleOriginal + 2 * Math.PI / (2 + 1);
        double angleThird = angleSecond + 2 * Math.PI / (2 + 1);

        Point2D two = new Point2D(center.getX() + radius 
                * Math.cos(angleSecond),
                center.getY() + radius * Math.sin(angleSecond)); 
        Point2D three = new Point2D(center.getX() + radius 
                * Math.cos(angleThird),
                center.getY() + radius * Math.sin(angleThird));
        Line firstLine = new Line(one.getX(), one.getY(), 
                two.getX(), two.getY());
        Line secondLine = new Line(two.getX(), two.getY(), 
                three.getX(), three.getY());
        Line thirdLine = new Line(three.getX(), three.getY(), 
                one.getX(), one.getY());
        firstLine.setStroke(Color.PINK);
        secondLine.setStroke(Color.YELLOW);
        thirdLine.setStroke(Color.CYAN);
        Group triangle = new Group(firstLine, secondLine, thirdLine, atCenter);

        root.getChildren().add(triangle);

    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
