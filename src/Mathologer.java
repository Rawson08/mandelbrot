/**
 * Name: Roshan Subedi
 * CS351: Modulo Times Tables Visualization
 *
 *
 */



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mathologer extends Application {


    //Initializing variables
    private boolean running = false;
    private int timesTable = 0;
    private double incrementSize;
    private double factor;
    private int colorIndex = 0;
    private final Color[] myColor = {Color.BLUE, Color.DARKBLUE, Color.CYAN, Color.DARKCYAN, Color.GREEN,
            Color.DARKGREEN, Color.MAGENTA, Color.DARKMAGENTA,Color.ORANGE, Color.DARKORANGE, Color.PINK,
            Color.RED, Color.DARKRED, Color.YELLOW, Color.PURPLE, Color.CORAL, Color.VIOLET, Color.DARKVIOLET};

    private long frameRate = 10;
    private long lastTime = 0;

    @Override
    public void start(Stage primaryStage) {

        //Initializing canvas, buttons, Textfields and Labels for buttons
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Button startStopButton = new Button("Start");
        TextField pointsOnCircle = new TextField("360");
        Label pointsOnCircleLabel = new Label("Points on Circle");
        TextField multiplier = new TextField("1");
        Label multiplierLabel = new Label("Increment size: ");
        TextField jumpToTimesTable = new TextField();
        Label jumpToTimesTableLabel = new Label("Enter Times Table to jump:");
        Label fpsSliderLabel = new Label("FPS Slider (1-60)");
        Slider fpsSlider = new Slider(1, 30, 10);


        //Start/Stop button Action Listener to switch running boolean
        startStopButton.setOnAction(e -> {
            if (!running) {
                running = true;
                startStopButton.setText("Stop");
            } else {
                running = false;
                startStopButton.setText("Start");
            }
        });

        //Takes total number of points and updates when entered
        pointsOnCircle.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                factor = Double.parseDouble(newValue);
            } catch (NumberFormatException ex) {
                pointsOnCircle.setText(oldValue);
            }
        });

        //Takes multiplier variable and updates when entered
        multiplier.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                incrementSize = Double.parseDouble(newValue);
            } catch (NumberFormatException ex) {
                multiplier.setText(oldValue);
            }
        });

        //Takes point to jump into and updates when entered
        jumpToTimesTable.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                timesTable = Integer.parseInt(newValue);
            } catch (NumberFormatException ex) {
                jumpToTimesTable.setText(oldValue);
            }
        });

        //Updates the FPS value from the slider into frameRate
        fpsSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                frameRate = (long) newValue.doubleValue());

        //VBox to hold all the buttons and labels to control the animation
        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.TOP_LEFT);
        buttons.getChildren().addAll(startStopButton, pointsOnCircleLabel, pointsOnCircle, multiplierLabel,
                multiplier, fpsSliderLabel, fpsSlider, jumpToTimesTableLabel, jumpToTimesTable);

        //Borderpane to store the canvas on right and buttons on left
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(buttons);
        root.setRight(canvas);

        //To control the FPS
        long frameTime = 1000000000 / frameRate;


        new AnimationTimer() {
            /**
             * Method which handles the major Animation. It checks if the program is running,
             * and then starts the animation when pressed Start, which changes the variable
             * running into true. It returns/breaks if the variable running is false.
             */
            @Override
            public void handle(long now) {
                if (now - lastTime < frameTime){
                    return;
                }

                lastTime = now;

                if (!running) {
                    return;
                }

                int total = Integer.parseInt(pointsOnCircle.getText());



                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, 400, 400);

                factor += incrementSize+0.1;
                gc.setStroke(myColor[colorIndex % myColor.length]);
                colorIndex++;

                //Updates factor if user wants to jump to certain frame
                if (timesTable != 0){
                    factor = timesTable;
                    gc.setStroke(Color.PURPLE);
                }

                gc.setLineWidth(1);
                gc.strokeOval(0, 0, 400, 400);
                gc.translate(200, 200);


                //Draws the animation
                for (int i = 0; i < total; i++) {
                    double angle = (i % total) * (2 * Math.PI / total) + Math.PI;
                    double x = Math.cos(angle) * 200;
                    double y = Math.sin(angle) * 200;

                    gc.setFill(Color.LIGHTGRAY);
                    gc.fillOval(x - 4, y - 4, 8, 8);
                }

                for (int i = 0; i < total; i++) {
                    double aAngle = (i % total) * (2 * Math.PI / total) + Math.PI;
                    double bAngle = ((i * factor) % total) * (2 * Math.PI / total) + Math.PI;
                    double ax = Math.cos(aAngle) * 200;
                    double ay = Math.sin(aAngle) * 200;
                    double bx = Math.cos(bAngle) * 200;
                    double by = Math.sin(bAngle) * 200;

                    gc.strokeLine(ax, ay, bx, by);
                }

                gc.translate(-200, -200);

                try {
                    long sleepTime = (lastTime - System.nanoTime() + frameTime) / (40000*frameRate);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        //Setting the scene and its resolution
        primaryStage.setScene(new Scene(root,  600, 600));
        primaryStage.show();
    }

    //Main method
    public static void main(String[] args) {
        launch(args);
    }
}