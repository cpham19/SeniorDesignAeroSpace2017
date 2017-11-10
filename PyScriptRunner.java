package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
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
	private String nameOfScript = "Project.py";
	private String pathToPythonScripts= "cd C:\\Users\\Calvin\\Documents\\Python Scripts & " + nameOfScript;
	String s = null;

	public PyScriptRunner()
	{

	}

	public void makeDirectory() {
		try {
			Path path = Paths.get("Files");

			if (Files.exists(path)) {
			    System.out.println("Please put your Python scripts in \"Files\" in your project folder.");
			    System.out.println("This is where CSV files and Python scripts should be located.");
			    return;
			}

			ArrayList<String> commands = new ArrayList<>();
			commands.add("cmd.exe");
			commands.add("/c");
			commands.add("mkdir Files");
			commands.add("start");

			ProcessBuilder pb = new ProcessBuilder(commands);

			pb.redirectErrorStream(true);

			Process p = pb.start();

			System.out.println("Created a folder called \"Files\" in your project folder.");
			System.out.println("This is where CSV files and Python scripts should be located.");
		}
		catch (Exception e) {
			System.err.println("CMD commands didn't run.");
		}
	}

	public void runPyScript()// pulls out the
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

			System.out.println("Ran python script");
		}
		catch(Exception e)
		{
			System.err.println("Python script didn't run.");
		}
	}
}

