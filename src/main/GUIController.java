package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GUIController {
	@FXML
	private Button upButton;

	@FXML
	private Button leftButton;

	@FXML
	private Button downButton;

	@FXML
	private Button rightButton;

	@FXML
	private Button stopButton;

	@FXML
	private Button manualButton;

	@FXML
	private Button autopilotButton;

	@FXML
	private Button collectButton;

	@FXML
	private Button trainingButton;

	@FXML
	private Button decreaseSpeedButton;

	@FXML
	private Button increaseSpeedButton;

	@FXML
	private Button rotateServoLeftButton;

	@FXML
	private Button rotateServoRightButton;

	@FXML
	private Label carSpeedLabel;

	@FXML
	private Label servoAngleLabel;

	@FXML
	private TextArea outputTextArea;

	@FXML
	private GridPane grid;

	static SerialIOController sioc;
	PyScriptRunner runner;

	private String currentState;
	private static int carSpeed = 0;
	private static int servoAngle = 0;

	Map<String, Integer> currentCoord = new HashMap<String, Integer>();
	Map<String, Integer> prevCoord = new HashMap<String, Integer>();

	@FXML
	private void initialize() throws Exception {
		sioc = new SerialIOController();
		runner = new PyScriptRunner();

		// Make "Files" directory if it doesn't exist.
		runner.makeDirectory();

		// Clear CSV file and Local database
		sioc.dc.clearCSV("Sample");
		//Data_Controller.clearDatabase("aria_data");

		createGrid();
		updateInfoSchedule();

		upButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User pressed forward button.\n");

			currentState = "Forward";
			sioc.SerialWrite('f');
		});

		leftButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed left button.\n");

			currentState = "Left";
			sioc.SerialWrite('l');
		});

		rightButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User pressed right button.\n");

			currentState = "Right";
			sioc.SerialWrite('r');
		});

		downButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed down button.\n");

			currentState = "Backward";
			sioc.SerialWrite('b');
		});

		stopButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed stop button.\n");

			currentState = "Stop";
			sioc.SerialWrite('s');
		});

		manualButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed manual button.\n");

			currentState = "Manual Mode";
			sioc.SerialWrite('m');
		});

		trainingButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed training button.\n");

			runner.runPyScript();
		});

		autopilotButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed autopilot button.\n");

			currentState = "Autopilot Mode";
			sioc.SerialWrite('t');
		});

		decreaseSpeedButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed Decrease Speed button.\n");

			sioc.SerialWrite('d');
		});

		increaseSpeedButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed Increase Speed button.\n");

			sioc.SerialWrite('i');
		});

		rotateServoRightButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed Rotate Servo Right button.\n");

			sioc.SerialWrite('1');
		});


		rotateServoLeftButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed Rotate Servo Left button.\n");

			sioc.SerialWrite('2');
		});


		collectButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed Collect Data button.\n");

			if (collectButton.getText().equals("Collect Data")) {
				try {
					sioc.listenData();
					collectButton.setText("Stop");
					collectButton.setStyle("-fx-background-color: red;");
				} catch (TooManyListenersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try
				{
					sioc.ignoreData();
					collectButton.setText("Collect Data");
					collectButton.setStyle("-fx-background-color: white;");
				}
				catch (TooManyListenersException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void updateInfoSchedule()
	{
		// this creates a Timer schedule that will basically run every 60 milisecond starting at 1 second
		Timer timer = new Timer ();
		timer.schedule(new TimerTask(){
			@Override
			public void run()// randomly set pGrid
			{
				// pull messages from the IO and send them to the GUI
				setCarSpeed(sioc.getCarSpeed());
				setServoAngle(sioc.getServoAngle());
			}
		}, 1,100);
	}

	public void createGrid() {
		int columns = 50;
		int rows = 50;

        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(grid.getPrefWidth()/columns);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(grid.getPrefHeight()/rows);
            grid.getRowConstraints().add(row);
        }

		for(int row = 0; row < rows; row++){
			for(int col = 0; col < columns; col++){
				StackPane stpane = new StackPane();
				stpane.setStyle("-fx-border-color: red");
				grid.add(stpane, col, row);
			}
		}

		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: black");

		currentCoord.put("X", (int) Math.ceil(rows) / 2);
		currentCoord.put("Y", (int) Math.ceil(columns) / 2);
		prevCoord.put("X", currentCoord.get("X"));
		prevCoord.put("Y", currentCoord.get("Y"));

		grid.add(pane, (int) Math.ceil(columns) / 2, (int) Math.ceil(rows) / 2);
	}

	public void setCarSpeed(int carSpeed) {
		carSpeedLabel.setText("Car Speed: " + carSpeed);
	}

	public void setServoAngle(int servoAngle) {
		servoAngleLabel.setText("Servo angle: " + servoAngle);
	}

	public String getCurrentLocalDateTimeStamp() {
		String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"));

		int number = Integer.parseInt(hour);

		if (number > 13) {
			number = number - 12;
		}
		return number + LocalDateTime.now().format(DateTimeFormatter.ofPattern(":mm:ss:a"));
	}
}

//This may be used later.
//currentCoord.put("X", currentCoord.get("X") + 1);
//grid.getChildren().remove(pane);
//grid.add(pane, currentCoord.get("Y"), currentCoord.get("X"));
//
//StackPane prevPane = new StackPane();
//prevPane.setStyle("-fx-background-color: blue");
//
//grid.add(prevPane, prevCoord.get("Y"), prevCoord.get("X"));
//prevCoord.put("X", currentCoord.get("X"));
//prevCoord.put("Y", currentCoord.get("Y"));
