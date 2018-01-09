package test;

import app.SerialIOController;

public class WriteThread extends Thread {
	private static int GUIWidth = 925;
	private static int GUIHeight = 720;

    SerialIOController VCM_Communicator = new SerialIOController();
    //GUIController GUI = new GUIController(GUIWidth, GUIHeight);

    public WriteThread() {

    }

    @Override
    public void run() {
		// We need to check to see and send the currentMessage of GUI to the VCM
		//VCM_Communicator.SerialWrite(GUI.getCurrentOutput());
		// pull messages from the IO and send them to the GUI
    }
}
