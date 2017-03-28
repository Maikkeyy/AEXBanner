package com.gso;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Maikkeyy on 28-3-2017.
 */

public class AEXBanner extends Application {
    private BannerController bannerController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {

    }

    public void setKoersen(String koersen) {

    }

}
