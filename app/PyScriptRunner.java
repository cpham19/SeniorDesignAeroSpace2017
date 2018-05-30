package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;


public class PyScriptRunner
{
	// Name of script to train
	private String nameOfTrainingScript = "Training_Sklearn.py";

	// Path to Files folder in your project folder
	private String pathToFilesFolder = "C:/Users/Calvin/Desktop/project/project/src/Files";

	// Path to Anaconda Prompt
	private String pathToAnacondaPrompt = "cd C:/Users/Calvin/Anaconda3/Scripts/ & activate.bat";

	// Path to training script
	private String pathToTrainingScript = pathToAnacondaPrompt
										+ "& cd " + pathToFilesFolder
										+ "& python " + nameOfTrainingScript;

	// Name of autopilot script
	private String nameOfAutopilotScript = "AutopilotSklearn.py";

	// Path of autopilot script
	private String pathToAutopilotScript = pathToAnacondaPrompt
										 + "& cd " + pathToFilesFolder
										 + "& python " + nameOfAutopilotScript;

	public PyScriptRunner()
	{

	}

	// Creates a folder called "Files" in src folder (this is used for storing CSV files)
	public void makeDirectory() {
		try {
			Path path = Paths.get("src/Files");

			if (Files.exists(path)) {
			    //GUIController.outputTextArea.append("Please put your Python scripts in \"Files\" in your project's src folder.\n");
			    GUIController.outputTextArea.append("CSV files are located in the folder \"Files\".\n");
			    return;
			}

			ArrayList<String> commands = new ArrayList<>();
			commands.add("cmd.exe");
			commands.add("/c");
			commands.add("mkdir src/Files");
			commands.add("start");

			ProcessBuilder pb = new ProcessBuilder(commands);

			pb.redirectErrorStream(true);

			Process p = pb.start();

			 GUIController.outputTextArea.append("Created a folder called \"Files\" in your project folder.\n");
			 GUIController.outputTextArea.append("This is where CSV files are located.\n");
		}
		catch (Exception e) {
			 GUIController.outputTextArea.append("CMD commands didn't run.\n");
		}
	}

	// This runs training script (not functional as of now)
	public void runTrainingScript()
	{
		try
		{
			ArrayList<String> commands = new ArrayList<>();
			commands.add("cmd");
			commands.add("/c");
			commands.add("start");
			commands.add("cmd.exe");
			commands.add("/k");
			commands.add(pathToTrainingScript);

			ProcessBuilder pb = new ProcessBuilder(commands);

			pb.redirectErrorStream(true);

			Process p = pb.start();

			 GUIController.outputTextArea.append(GUIController.getCurrentLocalDateTimeStamp() + "Ran python script.\n");
		}
		catch(Exception e)
		{
			 GUIController.outputTextArea.append(GUIController.getCurrentLocalDateTimeStamp() + "Python script didn't run.\n");
		}
	}

	// This runs autopilot script (not functional as of now)
	public void runAutomaticScript()
	{
		try
		{
			ArrayList<String> commands = new ArrayList<>();
			commands.add("cmd");
			commands.add("/c");
			commands.add("start");
			commands.add("cmd.exe");
			commands.add("/k");
			commands.add(pathToAutopilotScript);

			ProcessBuilder pb = new ProcessBuilder(commands);

			pb.redirectErrorStream(true);

			Process p = pb.start();

			 GUIController.outputTextArea.append(GUIController.getCurrentLocalDateTimeStamp() + "Ran python script.\n");
		}
		catch(Exception e)
		{
			 GUIController.outputTextArea.append(GUIController.getCurrentLocalDateTimeStamp() + "Python script didn't run.\n");
		}
	}
}

