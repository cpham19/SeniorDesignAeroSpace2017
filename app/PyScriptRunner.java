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
	private String nameOfScript = "MLP.py";
	private String pathToPythonScripts= "cd src/Files & " + nameOfScript;
	String s = null;

	public PyScriptRunner()
	{

	}

	public void makeDirectory() {
		try {
			Path path = Paths.get("src/Files");

			if (Files.exists(path)) {
			    GUIController.outputTextArea.append("Please put your Python scripts in \"Files\" in your project's src folder.\n");
			    GUIController.outputTextArea.append("This is where CSV files and Python scripts should be located.\n");
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
			 GUIController.outputTextArea.append("This is where CSV files and Python scripts should be located.\n");
		}
		catch (Exception e) {
			 GUIController.outputTextArea.append("CMD commands didn't run.\n");
		}
	}

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
			commands.add(pathToPythonScripts);

			ProcessBuilder pb = new ProcessBuilder(commands);

			pb.redirectErrorStream(true);

			Process p = pb.start();

			 GUIController.outputTextArea.append("Ran python script\n");
		}
		catch(Exception e)
		{
			 GUIController.outputTextArea.append("Python script didn't run.\n");
		}
	}
}

