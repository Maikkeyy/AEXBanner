package com.gso;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Maikkeyy on 28-3-2017.
 */

public class AEXBanner extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 100;
    public static final int NANO_TICKS = 20000000;
    public final double textSpeed = 5;

    private BannerController bannerController;
    private AnimationTimer animationTimer;

    private Text text;
    private double textLength;
    private double textPosition;


    @Override
    public void start(Stage primaryStage) throws Exception{
        bannerController = new BannerController(this);

        Font font = new Font("Arial", HEIGHT - 10);
        text = new Text();
        text.setFont(font);
        text.setFill(Color.YELLOW);

        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(text);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("AEX banner");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start animation: text moves from right to left
        animationTimer = new AnimationTimer() {
            private long prevUpdate;

            @Override
            public void handle(long now) {
                long lag = now - prevUpdate;
                if (lag >= NANO_TICKS) {
                    // calculate new location of text
                    textPosition -= textSpeed;
                    text.relocate(textPosition,0);
                    prevUpdate = now;
                }
            }

            @Override
            public void start() {
                prevUpdate = System.nanoTime();
                textPosition = WIDTH;
                super.start(); // wherein handle() will be called 60 ps
            }
        };
        animationTimer.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setKoersen(String koersen) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                text.setText(koersen);
                textLength = text.getLayoutBounds().getWidth();
            }
        });
    }


    @Override
    public void stop() {
        animationTimer.stop();
        bannerController.stop();
    }

}
