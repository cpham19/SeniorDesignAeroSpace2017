package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private Stage primaryStage;
	private Pane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Main App");

		initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (Pane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Pane getRootLayout() {
		return rootLayout;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
