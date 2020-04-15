package com.sistek.desktopapp;

public class TestDesktopApp {
	
	static RestClient client;
	
	public static void main(String s[]) {  
 
		RestClient restClient = new RestClient();
		SerialPortConnection serialPortConnection = new SerialPortConnection(restClient);
		FrameManager frameManager = new FrameManager(restClient, serialPortConnection);
		frameManager.startApplication();
		
    }
}
