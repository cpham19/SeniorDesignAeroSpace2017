package appForKabirsCar;

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
		// Create a PyscriptRunner object
		ScriptRunner = new PyScriptRunner();
		ScriptRunner.makeDirectory();

		// Create a SerialIOcontroller object
		VCM_Communicator = new SerialIOController();

		// Create a GUIController object
		GUI = new GUIController(GUIWidth,GUIHeight, VCM_Communicator, ScriptRunner);
	}
}