package client.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by staho on 23.04.2017.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kamilbury");

        initRootLayout();
        showDrawingLayout();

    }

    private void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));

            this.rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showDrawingLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/DrawingLayout.fxml"));

            AnchorPane anchorPane = loader.load();

            rootLayout.setCenter(anchorPane);
            DrawingController controller = loader.getController();
            controller.setMainApp(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        launch(args);
    }
}
