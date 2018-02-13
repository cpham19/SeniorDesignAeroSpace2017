package app;

public class Main
{
	public static SerialIOController VCM_Communicator;
	public static DataController Data_Controller;
	public static PyScriptRunner ScriptRunner;
	private static int GUIWidth = 925;
	private static int GUIHeight = 810;
	public static GUIController GUI;

	public static void main(String args[]) throws Exception
	{
		// Create a new Python Script Runner
		ScriptRunner = new PyScriptRunner();
		ScriptRunner.makeDirectory();

		// create a Communication controller
		VCM_Communicator = new SerialIOController();

		// create a GUI controller (Automatically opens itself
		GUI = new GUIController(GUIWidth,GUIHeight, VCM_Communicator, ScriptRunner);
	}
}