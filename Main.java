package app;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
	public static CarVector carLoc = new CarVector(0,0,0);
	public static SerialIOController VCM_Communicator;
	public static DataController Data_Controller;
	public static PyScriptRunner ScriptRunner;
	private static int GUIWidth = 925;
	private static int GUIHeight = 720;
	public static GUIController GUI;
	static final int X = 0;
	static final int Y = 1;
	static final int Z = 2;

	public static void main(String args[]) throws Exception
	{
		// Create a new Python Script Runner
		ScriptRunner = new PyScriptRunner();
		ScriptRunner.makeDirectory();

		Data_Controller.clearCSV("Sample");
		//Data_Controller.clearDatabase("aria_data");

		// create a Communication controller
		VCM_Communicator = new SerialIOController();

		// create a GUI controller (Automatically opens itself
		GUI = new GUIController(GUIWidth,GUIHeight, VCM_Communicator, ScriptRunner);
	}
}