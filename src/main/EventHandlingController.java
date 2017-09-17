package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class EventHandlingController {
	@FXML
	private Button upButton;

	@FXML
	private Button leftButton;

	@FXML
	private Button downButton;

	@FXML
	private Button rightButton;

	@FXML
	private TextArea outputTextArea;

	@FXML
	private Slider modeSlider;

	@FXML
	private void initialize() {
		upButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User pressed forward button.\n");
		});

		leftButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed left button.\n");
		});

		rightButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User pressed right button.\n");
		});

		downButton.setOnAction((event) -> {
			outputTextArea.appendText(getCurrentLocalDateTimeStamp()+ ": User pressed down button.\n");
		});

		modeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != newValue.intValue() && newValue.intValue() == 1) {
				outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User chooses Training Mode.\n");
			}
			else if (oldValue.intValue() != newValue.intValue() &&  newValue.intValue() == 2) {
				outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User chooses Testing Mode.\n");
			}
			else if (oldValue.intValue() != newValue.intValue() &&  newValue.intValue() == 3) {
				outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User chooses Manual Mode.\n");
			}
			else if (oldValue.intValue() != newValue.intValue() &&  newValue.intValue() == 4) {
				outputTextArea.appendText(getCurrentLocalDateTimeStamp() + ": User chooses Autopilot Mode.\n");
			}
		});
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
